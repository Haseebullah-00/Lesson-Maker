package com.lessonmaker.app.common.dataCellSheetSpecs

/** Column names in ONE place. Add new ones here. */
object Col {
    const val COURSE_CODE = "Course Code"
    const val COURSE_NAME = "Course Name"

    const val TEACHER_CODE = "TeacherCode"
    const val TEACHER_NAME = "TeacherName"

    const val ARID_NO = "ARID No"
    const val STUDENT_NAME = "StudentName"
    const val CGPA = "CGPA"

    const val EMAIL = "Email"
    const val PASSWORD = "Password"

    /** Student sheet: Teacher ID[i] teaches Course ID[i]; one course per teacher. Multi-value cells: several ids separated by ; or , e.g. "CS101;CS201". Values = Course Code / TeacherCode. */
    const val COURSE_IDS = "Course ID"
    const val TEACHER_IDS = "Teacher ID"
}

enum class SheetKind(
    val cardTitle: String,
    val noun: String,
    val sampleFile: String,
    val columns: List<String>
) {
    Course(
        "Course Records Upload", "course", "course_list.xlsx",
        listOf(Col.COURSE_CODE, Col.COURSE_NAME)
    ),
    Student(
        "Student Records Upload", "student", "student_list.xlsx",
        listOf(Col.ARID_NO, Col.STUDENT_NAME, Col.EMAIL, Col.PASSWORD, Col.CGPA, Col.TEACHER_IDS, Col.COURSE_IDS)
    ),
    Teacher(
        "Teacher Records", "teacher", "teachers_list.xlsx",
        listOf(Col.TEACHER_CODE, Col.TEACHER_NAME, Col.EMAIL, Col.PASSWORD, Col.COURSE_IDS)
    );

    /** Shown on the upload card when the sheet has multi-value columns. */
    val multiNote: String?
        get() = if (Col.COURSE_IDS in columns || Col.TEACHER_IDS in columns)
            "Course ID / Teacher ID may hold several values separated by ;" else null
}

class ParsedSheet(val fileName: String, val header: List<String>, val rows: List<List<String>>)

class SheetRow(val values: Map<String, String>) {
    operator fun get(column: String): String = values[column].orEmpty()
    /** Splits a multi-value cell ("CS101; CS201") into clean ids. */
    fun ids(column: String): List<String> = splitIds(this[column])
}

fun splitIds(s: String): List<String> =
    s.split(';', ',', '|').map { it.trim() }.filter { it.isNotEmpty() }   // no distinct(): student teacher/course lists are paired by position

// ---------- typed lists (what you send to the API) ----------
data class CourseRecord(val courseCode: String, val courseName: String)

data class TeacherRecord(
    val teacherCode: String,
    val teacherName: String,
    val email: String,
    val password: String,
    val courseIds: List<String>          // a teacher can teach many courses
)

data class StudentRecord(
    val aridNo: String,
    val studentName: String,
    val email: String,
    val password: String,
    val cgpa: String,
    val teacherIds: List<String>,
    val courseIds: List<String>          // a student can take many courses
)

data class UploadBundle(
    val sessionTitle: String,
    val courses: List<CourseRecord>,
    val teachers: List<TeacherRecord>,
    val students: List<StudentRecord>
)

fun SheetRow.toCourse() = CourseRecord(this[Col.COURSE_CODE], this[Col.COURSE_NAME])

fun SheetRow.toTeacher() = TeacherRecord(
    this[Col.TEACHER_CODE], this[Col.TEACHER_NAME], this[Col.EMAIL], this[Col.PASSWORD],
    ids(Col.COURSE_IDS).distinct()
)

fun SheetRow.toStudent() = StudentRecord(
    this[Col.ARID_NO], this[Col.STUDENT_NAME], this[Col.EMAIL], this[Col.PASSWORD], this[Col.CGPA],
    ids(Col.TEACHER_IDS), ids(Col.COURSE_IDS)
)

object SheetReader {
    fun parse(fileName: String, raw: List<List<String>>): ParsedSheet {
        val headerIndex = raw.indexOfFirst { r -> r.any { it.isNotBlank() } }
        if (headerIndex < 0) return ParsedSheet(fileName, emptyList(), emptyList())
        val rows = raw.drop(headerIndex + 1).filter { r -> r.any { it.isNotBlank() } }
        return ParsedSheet(fileName, raw[headerIndex], rows)
    }

    fun toRows(kind: SheetKind, sheet: ParsedSheet): List<SheetRow> {
        val have = sheet.header.map { SheetValidator.norm(it) }
        val index = kind.columns.associateWith { have.indexOf(SheetValidator.norm(it)) }
        return sheet.rows.map { r ->
            SheetRow(kind.columns.associateWith { c -> r.getOrNull(index.getValue(c)).orEmpty() })
        }
    }

    fun buildBundle(title: String, sheets: Map<SheetKind, ParsedSheet>) = UploadBundle(
        sessionTitle = title,
        courses = toRows(SheetKind.Course, sheets.getValue(SheetKind.Course)).map { it.toCourse() },
        teachers = toRows(SheetKind.Teacher, sheets.getValue(SheetKind.Teacher)).map { it.toTeacher() },
        students = toRows(SheetKind.Student, sheets.getValue(SheetKind.Student)).map { it.toStudent() }
    )
}

object SheetValidator {
    fun norm(s: String) = s.lowercase().filter { it.isLetterOrDigit() }

    fun checkFormat(kind: SheetKind, sheet: ParsedSheet): List<String> {
        if (sheet.header.isEmpty()) {
            return listOf("File is empty or not supported. Use .xlsx or .csv (old .xls is not supported).")
        }
        val errors = mutableListOf<String>()
        val have = sheet.header.map { norm(it) }
        val missing = kind.columns.filter { norm(it) !in have }
        if (missing.isNotEmpty()) {
            errors += "Missing column(s): ${missing.joinToString()}"
            errors += "Expected: ${kind.columns.joinToString()}"
            errors += "Found: ${sheet.header.filter { it.isNotBlank() }.joinToString()}"
        }
        if (sheet.rows.isEmpty()) errors += "No data rows below the header."
        return errors
    }

    /** Cross-sheet check. Call only after every checkFormat passed. Empty map = all ids exist. */
    fun checkReferences(sheets: Map<SheetKind, ParsedSheet>): Map<SheetKind, List<String>> {
        val courses = SheetReader.toRows(SheetKind.Course, sheets.getValue(SheetKind.Course))
            .map { it[Col.COURSE_CODE].trim() }.toSet()
        val teachers = SheetReader.toRows(SheetKind.Teacher, sheets.getValue(SheetKind.Teacher))
        val teacherCodes = teachers.map { it[Col.TEACHER_CODE].trim() }.toSet()
        val students = SheetReader.toRows(SheetKind.Student, sheets.getValue(SheetKind.Student))

        fun unknown(rows: List<SheetRow>, key: String, col: String, valid: Set<String>, label: String) =
            rows.flatMap { r -> r.ids(col).filter { it !in valid }.map { "$label ${r[key]}: unknown $col '$it'" } }


        // student rule: Teacher ID[i] teaches Course ID[i], and each teacher appears once (one course per teacher)
        val teaches = teachers.associate { it[Col.TEACHER_CODE].trim() to it.ids(Col.COURSE_IDS).toSet() }
        val pairing = students.flatMap { st ->
            val id = st[Col.ARID_NO]
            val t = st.ids(Col.TEACHER_IDS); val c = st.ids(Col.COURSE_IDS)
            if (t.size != c.size) listOf("Student $id: Teacher ID and Course ID counts must match")
            else buildList {
                if (t.size != t.toSet().size) add("Student $id: only one course per teacher")
                t.zip(c).forEach { (tc, cc) ->
                    if (teaches[tc]?.contains(cc) == false) add("Student $id: teacher $tc does not teach $cc")
                }
            }
        }

        // email format: teacher dr.abc@biit.edu.pk, student = "<ARID No>@biit.edu.pk" (e.g. 2023-ARID-0000@biit.edu.pk)
        val teacherMail = Regex("^dr\\.[a-z]+@biit\\.edu\\.pk$")
        val studentMail = Regex("^\\d{4}-ARID-\\d{4}@biit\\.edu\\.pk$", RegexOption.IGNORE_CASE)
        val teacherEmailErr = teachers.filter { !teacherMail.matches(it[Col.EMAIL].trim()) }
            .map { "Teacher ${it[Col.TEACHER_CODE]}: email must look like dr.abc@biit.edu.pk" }
        val studentEmailErr = students.filter {
            val e = it[Col.EMAIL].trim()
            !studentMail.matches(e) || !e.equals(it[Col.ARID_NO].trim() + "@biit.edu.pk", ignoreCase = true)
        }.map { "Student ${it[Col.ARID_NO]}: email must be <ARID No>@biit.edu.pk" }

        val out = mutableMapOf<SheetKind, List<String>>()
        (unknown(teachers, Col.TEACHER_CODE, Col.COURSE_IDS, courses, "Teacher") + teacherEmailErr)
            .let { if (it.isNotEmpty()) out[SheetKind.Teacher] = it.take(8) }
        (unknown(students, Col.ARID_NO, Col.COURSE_IDS, courses, "Student") +
                unknown(students, Col.ARID_NO, Col.TEACHER_IDS, teacherCodes, "Student") + pairing + studentEmailErr)
            .let { if (it.isNotEmpty()) out[SheetKind.Student] = it.take(8) }
        return out
    }
}