package com.lessonmaker.app.common.dataCellSheetSpecs

import kotlinx.serialization.SerialName      // lets us choose the JSON key name of a field
import kotlinx.serialization.Serializable    // lets Ktor turn a class into JSON (REQUIRED, was missing before)

// =====================================================================================
// 1. COLUMN NAMES - the ONLY place where Excel header names are written.
//    If a header changes in Excel, change it here and nowhere else.
// =====================================================================================
object Col {
    const val COURSE_CODE = "Course_Code"      // Course sheet + Teacher sheet + Student sheet
    const val COURSE_NAME = "Course_Name"      // Course sheet

    const val TEACHER_ID = "Teacher_ID"        // Teacher sheet + Student sheet
    const val TEACHER_NAME = "Teacher_Name"    // Teacher sheet

    const val STUDENT_ID = "Student_ID"        // Student sheet (the ARID number)
    const val STUDENT_NAME = "Student_Name"    // Student sheet
    const val CGPA = "CGPA"                    // Student sheet - OPTIONAL (empty for first-semester students)

    const val EMAIL = "Email"                  // Teacher sheet + Student sheet
    const val PASSWORD = "Password"            // Teacher sheet + Student sheet
}

// =====================================================================================
// 2. SHEET FORMATS - which columns each Excel file must / may contain.
// =====================================================================================
enum class SheetKind(
    val cardTitle: String,                     // title shown on the upload card
    val noun: String,                          // used in "No <noun> file selected"
    val sampleFile: String,                    // used in "Tap to upload <sampleFile>"
    val columns: List<String>,                 // REQUIRED headers: file is rejected if one is missing
    val optional: List<String> = emptyList()   // OPTIONAL headers: read if present, "" if missing, never an error
) {
    Course(
        "Course Records Upload", "course", "Course.xlsx",
        listOf(Col.COURSE_CODE, Col.COURSE_NAME)
    ),
    Student(
        "Student Records Upload", "student", "Student.xlsx",
        listOf(Col.STUDENT_ID, Col.STUDENT_NAME, Col.EMAIL, Col.PASSWORD, Col.COURSE_CODE, Col.TEACHER_ID),
        optional = listOf(Col.CGPA)            // CGPA moved here: it may be blank, so it is not required
    ),
    Teacher(
        "Teacher Records", "teacher", "Teacher.xlsx",
        listOf(Col.TEACHER_ID, Col.TEACHER_NAME, Col.EMAIL, Col.PASSWORD, Col.COURSE_CODE)
    );

    // every column the reader should try to read = required + optional
    val allColumns: List<String> get() = columns + optional
}

// =====================================================================================
// 3. RAW DATA HOLDERS - the Excel file as plain text, nothing converted yet.
// =====================================================================================
// header = the first non-blank row, rows = the data rows under it. Every cell is a String.
class ParsedSheet(val fileName: String, val header: List<String>, val rows: List<List<String>>)

// one data row as "header name -> cell value"
class SheetRow(val values: Map<String, String>) {
    // row[Col.EMAIL] gives the email; returns "" when the column does not exist
    operator fun get(column: String): String = values[column].orEmpty()
}

// =====================================================================================
// 4. RECORDS - one object per sheet row. These are exactly what is sent to the server as JSON.
//    @SerialName must match the C# property names (SessionTitle, Courses, CourseCode ...).
// =====================================================================================
@Serializable
data class CourseRecord(
    @SerialName("CourseCode") val courseCode: String,
    @SerialName("CourseName") val courseName: String
)

@Serializable
data class TeacherRecord(
    @SerialName("TeacherId") val teacherId: String,
    @SerialName("TeacherName") val teacherName: String,
    @SerialName("Email") val email: String,
    @SerialName("Password") val password: String,
    @SerialName("CourseCode") val courseCode: String
)

@Serializable
data class StudentRecord(
    @SerialName("StudentId") val studentId: String,
    @SerialName("StudentName") val studentName: String,
    @SerialName("Email") val email: String,
    @SerialName("Password") val password: String,
    @SerialName("CourseCode") val courseCode: String,
    @SerialName("TeacherId") val teacherId: String,
    @SerialName("CGPA") val cgpa: String? = null   // null when the cell is blank (first-semester student)
)

@Serializable
data class UploadBundle(
    @SerialName("SessionTitle") val sessionTitle: String,     // e.g. "Fall 2025"
    @SerialName("Courses") val courses: List<CourseRecord>,
    @SerialName("Teachers") val teachers: List<TeacherRecord>,
    @SerialName("Students") val students: List<StudentRecord>
)

// ---- row -> record mappers. Named arguments are used so the order can never get mixed up. ----
fun SheetRow.toCourse() = CourseRecord(
    courseCode = this[Col.COURSE_CODE],
    courseName = this[Col.COURSE_NAME]
)

fun SheetRow.toTeacher() = TeacherRecord(
    teacherId = this[Col.TEACHER_ID],
    teacherName = this[Col.TEACHER_NAME],
    email = this[Col.EMAIL],
    password = this[Col.PASSWORD],
    courseCode = this[Col.COURSE_CODE]
)

fun SheetRow.toStudent() = StudentRecord(
    studentId = this[Col.STUDENT_ID],
    studentName = this[Col.STUDENT_NAME],
    email = this[Col.EMAIL],
    password = this[Col.PASSWORD],
    courseCode = this[Col.COURSE_CODE],
    teacherId = this[Col.TEACHER_ID],
    cgpa = this[Col.CGPA].ifBlank { null }     // blank cell -> null (not "") so the server stores NULL
)

// =====================================================================================
// 5. READER - turns raw Excel cells into rows, records and finally one UploadBundle.
// =====================================================================================
object SheetReader {

    // raw cells (from readSpreadsheet) -> header + data rows, skipping blank rows
    fun parse(fileName: String, raw: List<List<String>>): ParsedSheet {
        // the header is the first row that has at least one non-blank cell
        val headerIndex = raw.indexOfFirst { r -> r.any { it.isNotBlank() } }
        // no such row = completely empty file -> empty sheet (checkFormat reports it later)
        if (headerIndex < 0) return ParsedSheet(fileName, emptyList(), emptyList())
        // data rows = everything under the header, minus rows that are entirely blank
        val rows = raw.drop(headerIndex + 1).filter { r -> r.any { it.isNotBlank() } }
        return ParsedSheet(fileName, raw[headerIndex], rows)
    }

    // each data row -> SheetRow. Columns are found by header NAME, so column order in Excel does not matter.
    fun toRows(kind: SheetKind, sheet: ParsedSheet): List<SheetRow> {
        // normalized headers of the file, e.g. "Course_Code" -> "coursecode"
        val have = sheet.header.map { SheetValidator.norm(it) }
        // for every column we want (required + optional): its position in the file, or -1 if absent
        val index = kind.allColumns.associateWith { have.indexOf(SheetValidator.norm(it)) }
        return sheet.rows.map { r ->
            SheetRow(kind.allColumns.associateWith { c ->
                val i = index.getValue(c)                       // position of this column
                if (i < 0) "" else r.getOrNull(i).orEmpty().trim()   // missing column/short row -> "", else trimmed text
            })
        }
    }

    // builds the final lists. Call only after the checks passed.
    fun buildBundle(title: String, sheets: Map<SheetKind, ParsedSheet>) = UploadBundle(
        sessionTitle = title,
        courses = toRows(SheetKind.Course, sheets.getValue(SheetKind.Course)).map { it.toCourse() },
        teachers = toRows(SheetKind.Teacher, sheets.getValue(SheetKind.Teacher)).map { it.toTeacher() },
        students = toRows(SheetKind.Student, sheets.getValue(SheetKind.Student)).map { it.toStudent() }
    )
}

// =====================================================================================
// 6. VALIDATOR - CHECK 1 (per file) and CHECK 2 (across files).
// =====================================================================================
object SheetValidator {
    // simple email shape: something@something.tld
    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    // lowercase letters/digits only, so "Course_Code" == "course code". Used ONLY to compare headers.
    fun norm(s: String) = s.lowercase().filter { it.isLetterOrDigit() }

    // CHECK 1 (per sheet): required headers exist + at least one data row. Empty list = OK.
    fun checkFormat(kind: SheetKind, sheet: ParsedSheet): List<String> {
        // nothing could be read (empty file or unsupported format such as old .xls)
        if (sheet.header.isEmpty()) {
            return listOf("File is empty or not supported. Use .xlsx or .csv (old .xls is not supported).")
        }
        val errors = mutableListOf<String>()
        val have = sheet.header.map { norm(it) }
        // only REQUIRED columns are checked here; optional ones (CGPA) may be missing
        val missing = kind.columns.filter { norm(it) !in have }
        if (missing.isNotEmpty()) {
            errors += "Missing column(s): ${missing.joinToString()}"
            errors += "Expected: ${kind.columns.joinToString()}"
            errors += "Found: ${sheet.header.filter { it.isNotBlank() }.joinToString()}"
        }
        if (sheet.rows.isEmpty()) errors += "No data rows below the header."
        return errors
    }

    // CHECK 2 (across sheets): ids, emails, passwords are consistent.
    // Run only after CHECK 1 passed for ALL sheets (otherwise columns may be missing).
    fun checkReferences(sheets: Map<SheetKind, ParsedSheet>): Map<SheetKind, List<String>> {
        val courses = SheetReader.toRows(SheetKind.Course, sheets.getValue(SheetKind.Course))
        val teachers = SheetReader.toRows(SheetKind.Teacher, sheets.getValue(SheetKind.Teacher))
        val students = SheetReader.toRows(SheetKind.Student, sheets.getValue(SheetKind.Student))

        val courseCodes = courses.map { it[Col.COURSE_CODE] }.toSet()                 // every course that exists
        val teacherIds = teachers.map { it[Col.TEACHER_ID] }.toSet()                  // every teacher that exists
        val teaches = teachers.map { it[Col.TEACHER_ID] to it[Col.COURSE_CODE] }.toSet() // (teacher, course) pairs

        // ---------- Teacher sheet ----------
        val teacherErrors = mutableListOf<String>()
        teachers.forEach { t ->
            val id = t[Col.TEACHER_ID]
            val email = t[Col.EMAIL]
            if (email.isBlank()) teacherErrors += "Teacher $id: email is empty"
            else if (!emailRegex.matches(email)) teacherErrors += "Teacher $id: invalid email \"$email\""
            if (t[Col.PASSWORD].isBlank()) teacherErrors += "Teacher $id: password is empty"
            if (t[Col.COURSE_CODE] !in courseCodes)
                teacherErrors += "Teacher $id: unknown Course_Code \"${t[Col.COURSE_CODE]}\""
        }

        // ---------- Student sheet ----------
        val studentErrors = mutableListOf<String>()
        students.forEach { s ->
            val id = s[Col.STUDENT_ID]
            val email = s[Col.EMAIL]
            val course = s[Col.COURSE_CODE]
            val teacher = s[Col.TEACHER_ID]
            val cgpa = s[Col.CGPA]

            if (email.isBlank()) studentErrors += "Student $id: email is empty"
            else if (!emailRegex.matches(email)) studentErrors += "Student $id: invalid email \"$email\""
            if (s[Col.PASSWORD].isBlank()) studentErrors += "Student $id: password is empty"

            // CGPA: a BLANK value is allowed (first semester). The old "CGPA is empty" error is removed.
            // Only a NON-blank value is checked, because text like "abc" would make the server reject the request.
            // (Delete this block if you do not want any CGPA check at all.)
            if (cgpa.isNotBlank()) {
                val v = cgpa.toDoubleOrNull()
                if (v == null || v !in 0.0..4.0) studentErrors += "Student $id: CGPA \"$cgpa\" must be a number from 0 to 4"
            }

            if (course !in courseCodes) studentErrors += "Student $id: unknown Course_Code \"$course\""
            if (teacher !in teacherIds) studentErrors += "Student $id: unknown Teacher_ID \"$teacher\""
            else if (course in courseCodes && (teacher to course) !in teaches)
                studentErrors += "Student $id: teacher $teacher does not teach $course"
        }

        // show at most 8 messages per sheet, then "...and N more"
        fun cap(l: List<String>) = if (l.size > 8) l.take(8) + "…and ${l.size - 8} more" else l
        val out = mutableMapOf<SheetKind, List<String>>()
        if (teacherErrors.isNotEmpty()) out[SheetKind.Teacher] = cap(teacherErrors)
        if (studentErrors.isNotEmpty()) out[SheetKind.Student] = cap(studentErrors)
        return out   // empty map = everything is fine
    }
}

// =====================================================================================
// HOW TO ADD A NEW COLUMN (example: Phone on the Student sheet)
//   1. Col:          const val PHONE = "Phone"
//   2. SheetKind:    add Col.PHONE to Student's `columns` (required) or `optional` list.
//                    -> reading (toRows) and header checking (checkFormat) pick it up automatically.
//   3. StudentRecord: add   @SerialName("Phone") val phone: String
//   4. toStudent():   add   phone = this[Col.PHONE]
//   5. checkReferences (only if you want a rule): e.g. if (s[Col.PHONE].isBlank()) studentErrors += "..."
//   6. Server: add Phone to the C# import model, the database table, and importData.
//   7. Add the column to the Excel files.
// =====================================================================================
//package com.lessonmaker.app.common.dataCellSheetSpecs
//
//import kotlinx.serialization.SerialName
//import kotlinx.serialization.Serializable
//
//// ============ 1. COLUMN NAMES (the only place header names are written) ============
//object Col {
//    const val COURSE_CODE = "Course_Code"
//    const val COURSE_NAME = "Course_Name"
//
//    const val TEACHER_ID = "Teacher_ID"
//    const val TEACHER_NAME = "Teacher_Name"
//
//    const val STUDENT_ID = "Student_ID"
//    const val STUDENT_NAME = "Student_Name"
//    const val CGPA = "CGPA"                       // NEW
//
//    const val EMAIL = "Email"
//    const val PASSWORD = "Password"
//}
//
//// ============ 2. SHEET FORMATS (required columns of each sheet) ============
//enum class SheetKind(
//    val cardTitle: String,
//    val noun: String,
//    val sampleFile: String,
//    val columns: List<String>
//) {
//    Course(
//        "Course Records Upload", "course", "Course.xlsx",
//        listOf(Col.COURSE_CODE, Col.COURSE_NAME)
//    ),
//    Student(
//        "Student Records Upload", "student", "Student.xlsx",
//        listOf(Col.STUDENT_ID, Col.STUDENT_NAME, Col.EMAIL,Col.PASSWORD, Col.COURSE_CODE, Col.TEACHER_ID, Col.CGPA) // NEW: Col.CGPA
//    ),
//    Teacher(
//        "Teacher Records", "teacher", "Teacher.xlsx",
//        listOf(Col.TEACHER_ID, Col.TEACHER_NAME, Col.EMAIL,Col.PASSWORD, Col.COURSE_CODE)
//    );
//
//    val multiNote: String? get() = null   // kept so older screen code still compiles
//}
//
//// ============ 3. RAW DATA HOLDERS ============
//// header = first row, rows = data rows; cell values are never changed
//class ParsedSheet(val fileName: String, val header: List<String>, val rows: List<List<String>>)
//
//// one data row as "column name -> cell value"
//class SheetRow(val values: Map<String, String>) {
//    operator fun get(column: String): String = values[column].orEmpty()
//}
//
//// ============ 4. RECORDS: one object per sheet row (the lists for your API) ============
//@Serializable
//data class CourseRecord(
//    @SerialName("CourseCode") val courseCode: String,
//    @SerialName("CourseName") val courseName: String
//)
//
//@Serializable
//data class TeacherRecord(
//    @SerialName("TeacherId") val teacherId: String,
//    @SerialName("TeacherName") val teacherName: String,
//    @SerialName("Email") val email: String,
//    @SerialName("Password") val password: String,
//    @SerialName("CourseCode") val courseCode: String
//)
//
//@Serializable
//data class StudentRecord(
//    @SerialName("StudentId") val studentId: String,
//    @SerialName("StudentName") val studentName: String,
//    @SerialName("Email") val email: String,
//    @SerialName("Password") val password: String,
//    @SerialName("CourseCode") val courseCode: String,
//    @SerialName("TeacherId") val teacherId: String,
//    @SerialName("CGPA") val cgpa: String
//)
//
//@Serializable
//data class UploadBundle(
//    @SerialName("SessionTitle") val sessionTitle: String,
//    @SerialName("Courses") val courses: List<CourseRecord>,
//    @SerialName("Teachers") val teachers: List<TeacherRecord>,
//    @SerialName("Students") val students: List<StudentRecord>
//)
//
//// row -> record, one argument per field
//fun SheetRow.toCourse() = CourseRecord(this[Col.COURSE_CODE], this[Col.COURSE_NAME])
//
//fun SheetRow.toTeacher() = TeacherRecord(
//    this[Col.TEACHER_ID], this[Col.TEACHER_NAME], this[Col.EMAIL], this[Col.PASSWORD],this[Col.COURSE_CODE]
//)
//
//fun SheetRow.toStudent() = StudentRecord(
//    this[Col.STUDENT_ID], this[Col.STUDENT_NAME], this[Col.EMAIL],this[Col.PASSWORD],
//    this[Col.COURSE_CODE], this[Col.TEACHER_ID], this[Col.CGPA]       // NEW: this[Col.CGPA]
//)
//
//// ============ 5. READER ============
//object SheetReader {
//
//    // raw cells (from readSpreadsheet) -> header + data rows, skipping blank rows
//    fun parse(fileName: String, raw: List<List<String>>): ParsedSheet {
//        val headerIndex = raw.indexOfFirst { r -> r.any { it.isNotBlank() } }
//        if (headerIndex < 0) return ParsedSheet(fileName, emptyList(), emptyList())
//        val rows = raw.drop(headerIndex + 1).filter { r -> r.any { it.isNotBlank() } }
//        return ParsedSheet(fileName, raw[headerIndex], rows)
//    }
//
//    // each data row -> SheetRow, columns found by header name (column order does not matter)
//    fun toRows(kind: SheetKind, sheet: ParsedSheet): List<SheetRow> {
//        val have = sheet.header.map { SheetValidator.norm(it) }
//        val index = kind.columns.associateWith { have.indexOf(SheetValidator.norm(it)) }
//        return sheet.rows.map { r ->
//            SheetRow(kind.columns.associateWith { c -> r.getOrNull(index.getValue(c)).orEmpty() })
//        }
//    }
//
//    // final lists; call only after all checks passed
//    fun buildBundle(title: String, sheets: Map<SheetKind, ParsedSheet>) = UploadBundle(
//        sessionTitle = title,
//        courses = toRows(SheetKind.Course, sheets.getValue(SheetKind.Course)).map { it.toCourse() },
//        teachers = toRows(SheetKind.Teacher, sheets.getValue(SheetKind.Teacher)).map { it.toTeacher() },
//        students = toRows(SheetKind.Student, sheets.getValue(SheetKind.Student)).map { it.toStudent() }
//    )
//}
//
//// ============ 6. VALIDATOR ============
//object SheetValidator {
//    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
//
//    // lowercase letters/digits only, so "Course_Code" == "course code"; used only to compare headers
//    fun norm(s: String) = s.lowercase().filter { it.isLetterOrDigit() }
//
//    // CHECK 1 (per sheet): required headers exist + at least one data row. Empty list = OK
//    fun checkFormat(kind: SheetKind, sheet: ParsedSheet): List<String> {
//        if (sheet.header.isEmpty()) {
//            return listOf("File is empty or not supported. Use .xlsx or .csv (old .xls is not supported).")
//        }
//        val errors = mutableListOf<String>()
//        val have = sheet.header.map { norm(it) }
//        val missing = kind.columns.filter { norm(it) !in have }
//        if (missing.isNotEmpty()) {
//            errors += "Missing column(s): ${missing.joinToString()}"
//            errors += "Expected: ${kind.columns.joinToString()}"
//            errors += "Found: ${sheet.header.filter { it.isNotBlank() }.joinToString()}"
//        }
//        if (sheet.rows.isEmpty()) errors += "No data rows below the header."
//        return errors
//    }
//
//    // CHECK 2 (across sheets): ids + emails are consistent. Run after CHECK 1 passed for all sheets
//    fun checkReferences(sheets: Map<SheetKind, ParsedSheet>): Map<SheetKind, List<String>> {
//        val courses = SheetReader.toRows(SheetKind.Course, sheets.getValue(SheetKind.Course))
//        val teachers = SheetReader.toRows(SheetKind.Teacher, sheets.getValue(SheetKind.Teacher))
//        val students = SheetReader.toRows(SheetKind.Student, sheets.getValue(SheetKind.Student))
//
//        val courseCodes = courses.map { it[Col.COURSE_CODE].trim() }.toSet()
//        val teacherIds = teachers.map { it[Col.TEACHER_ID].trim() }.toSet()
//        val teaches = teachers.map { it[Col.TEACHER_ID].trim() to it[Col.COURSE_CODE].trim() }.toSet()
//
//        val teacherErrors = mutableListOf<String>()
//        teachers.forEach { t ->
//            val id = t[Col.TEACHER_ID]
//            val email = t[Col.EMAIL].trim()
//            if (email.isBlank()) teacherErrors += "Teacher $id: email is empty"
//            else if (!emailRegex.matches(email)) teacherErrors += "Teacher $id: invalid email \"$email\""
//            if (t[Col.COURSE_CODE].trim() !in courseCodes)
//                teacherErrors += "Teacher $id: unknown Course_Code \"${t[Col.COURSE_CODE]}\""
//        }
//
//        val studentErrors = mutableListOf<String>()
//        students.forEach { s ->
//            val id = s[Col.STUDENT_ID]
//            val email = s[Col.EMAIL].trim()
//            val course = s[Col.COURSE_CODE].trim()
//            val teacher = s[Col.TEACHER_ID].trim()
//            if (email.isBlank()) studentErrors += "Student $id: email is empty"
//            else if (!emailRegex.matches(email)) studentErrors += "Student $id: invalid email \"$email\""
//            if (s[Col.CGPA].isBlank()) studentErrors += "Student $id: CGPA is empty"          // NEW
//            if (course !in courseCodes) studentErrors += "Student $id: unknown Course_Code \"$course\""
//            if (teacher !in teacherIds) studentErrors += "Student $id: unknown Teacher_ID \"$teacher\""
//            else if (course in courseCodes && (teacher to course) !in teaches)
//                studentErrors += "Student $id: teacher $teacher does not teach $course"
//        }
//
//        fun cap(l: List<String>) = if (l.size > 8) l.take(8) + "…and ${l.size - 8} more" else l
//        val out = mutableMapOf<SheetKind, List<String>>()
//        if (teacherErrors.isNotEmpty()) out[SheetKind.Teacher] = cap(teacherErrors)
//        if (studentErrors.isNotEmpty()) out[SheetKind.Student] = cap(studentErrors)
//        return out
//    }
//}
//package com.lessonmaker.app.common.dataCellSheetSpecs
//
///** Column names in ONE place. Add new ones here. */
//object Col {
//    const val COURSE_CODE = "Course Code"
//    const val COURSE_NAME = "Course Name"
//
//    const val TEACHER_CODE = "TeacherCode"
//    const val TEACHER_NAME = "TeacherName"
//
//    const val ARID_NO = "ARID No"
//    const val STUDENT_NAME = "StudentName"
//    const val CGPA = "CGPA"
//
//    const val EMAIL = "Email"
//    const val PASSWORD = "Password"
//
//    /** Student sheet: Teacher ID[i] teaches Course ID[i]; one course per teacher. Multi-value cells: several ids separated by ; or , e.g. "CS101;CS201". Values = Course Code / TeacherCode. */
//    const val COURSE_IDS = "Course ID"
//    const val TEACHER_IDS = "Teacher ID"
//}
//
//enum class SheetKind(
//    val cardTitle: String,
//    val noun: String,
//    val sampleFile: String,
//    val columns: List<String>
//) {
//    Course(
//        "Course Records Upload", "course", "course_list.xlsx",
//        listOf(Col.COURSE_CODE, Col.COURSE_NAME)
//    ),
//    Student(
//        "Student Records Upload", "student", "student_list.xlsx",
//        listOf(Col.ARID_NO, Col.STUDENT_NAME, Col.EMAIL, Col.PASSWORD, Col.CGPA, Col.TEACHER_IDS, Col.COURSE_IDS)
//    ),
//    Teacher(
//        "Teacher Records", "teacher", "teachers_list.xlsx",
//        listOf(Col.TEACHER_CODE, Col.TEACHER_NAME, Col.EMAIL, Col.PASSWORD, Col.COURSE_IDS)
//    );
//
//    /** Shown on the upload card when the sheet has multi-value columns. */
//    val multiNote: String?
//        get() = if (Col.COURSE_IDS in columns || Col.TEACHER_IDS in columns)
//            "Course ID / Teacher ID may hold several values separated by ;" else null
//}
//
//class ParsedSheet(val fileName: String, val header: List<String>, val rows: List<List<String>>)
//
//class SheetRow(val values: Map<String, String>) {
//    operator fun get(column: String): String = values[column].orEmpty()
//    /** Splits a multi-value cell ("CS101; CS201") into clean ids. */
//    fun ids(column: String): List<String> = splitIds(this[column])
//}
//
//fun splitIds(s: String): List<String> =
//    s.split(';', ',', '|').map { it.trim() }.filter { it.isNotEmpty() }   // no distinct(): student teacher/course lists are paired by position
//
//// ---------- typed lists (what you send to the API) ----------
//data class CourseRecord(val courseCode: String, val courseName: String)
//
//data class TeacherRecord(
//    val teacherCode: String,
//    val teacherName: String,
//    val email: String,
//    val password: String,
//    val courseIds: List<String>          // a teacher can teach many courses
//)
//
//data class StudentRecord(
//    val aridNo: String,
//    val studentName: String,
//    val email: String,
//    val password: String,
//    val cgpa: String,
//    val teacherIds: List<String>,
//    val courseIds: List<String>          // a student can take many courses
//)
//
//data class UploadBundle(
//    val sessionTitle: String,
//    val courses: List<CourseRecord>,
//    val teachers: List<TeacherRecord>,
//    val students: List<StudentRecord>
//)
//
//fun SheetRow.toCourse() = CourseRecord(this[Col.COURSE_CODE], this[Col.COURSE_NAME])
//
//fun SheetRow.toTeacher() = TeacherRecord(
//    this[Col.TEACHER_CODE], this[Col.TEACHER_NAME], this[Col.EMAIL], this[Col.PASSWORD],
//    ids(Col.COURSE_IDS).distinct()
//)
//
//fun SheetRow.toStudent() = StudentRecord(
//    this[Col.ARID_NO], this[Col.STUDENT_NAME], this[Col.EMAIL], this[Col.PASSWORD], this[Col.CGPA],
//    ids(Col.TEACHER_IDS), ids(Col.COURSE_IDS)
//)
//
//object SheetReader {
//    fun parse(fileName: String, raw: List<List<String>>): ParsedSheet {
//        val headerIndex = raw.indexOfFirst { r -> r.any { it.isNotBlank() } }
//        if (headerIndex < 0) return ParsedSheet(fileName, emptyList(), emptyList())
//        val rows = raw.drop(headerIndex + 1).filter { r -> r.any { it.isNotBlank() } }
//        return ParsedSheet(fileName, raw[headerIndex], rows)
//    }
//
//    fun toRows(kind: SheetKind, sheet: ParsedSheet): List<SheetRow> {
//        val have = sheet.header.map { SheetValidator.norm(it) }
//        val index = kind.columns.associateWith { have.indexOf(SheetValidator.norm(it)) }
//        return sheet.rows.map { r ->
//            SheetRow(kind.columns.associateWith { c -> r.getOrNull(index.getValue(c)).orEmpty() })
//        }
//    }
//
//    fun buildBundle(title: String, sheets: Map<SheetKind, ParsedSheet>) = UploadBundle(
//        sessionTitle = title,
//        courses = toRows(SheetKind.Course, sheets.getValue(SheetKind.Course)).map { it.toCourse() },
//        teachers = toRows(SheetKind.Teacher, sheets.getValue(SheetKind.Teacher)).map { it.toTeacher() },
//        students = toRows(SheetKind.Student, sheets.getValue(SheetKind.Student)).map { it.toStudent() }
//    )
//}
//
//object SheetValidator {
//    fun norm(s: String) = s.lowercase().filter { it.isLetterOrDigit() }
//
//    fun checkFormat(kind: SheetKind, sheet: ParsedSheet): List<String> {
//        if (sheet.header.isEmpty()) {
//            return listOf("File is empty or not supported. Use .xlsx or .csv (old .xls is not supported).")
//        }
//        val errors = mutableListOf<String>()
//        val have = sheet.header.map { norm(it) }
//        val missing = kind.columns.filter { norm(it) !in have }
//        if (missing.isNotEmpty()) {
//            errors += "Missing column(s): ${missing.joinToString()}"
//            errors += "Expected: ${kind.columns.joinToString()}"
//            errors += "Found: ${sheet.header.filter { it.isNotBlank() }.joinToString()}"
//        }
//        if (sheet.rows.isEmpty()) errors += "No data rows below the header."
//        return errors
//    }
//
//    /** Cross-sheet check. Call only after every checkFormat passed. Empty map = all ids exist. */
//    fun checkReferences(sheets: Map<SheetKind, ParsedSheet>): Map<SheetKind, List<String>> {
//        val courses = SheetReader.toRows(SheetKind.Course, sheets.getValue(SheetKind.Course))
//            .map { it[Col.COURSE_CODE].trim() }.toSet()
//        val teachers = SheetReader.toRows(SheetKind.Teacher, sheets.getValue(SheetKind.Teacher))
//        val teacherCodes = teachers.map { it[Col.TEACHER_CODE].trim() }.toSet()
//        val students = SheetReader.toRows(SheetKind.Student, sheets.getValue(SheetKind.Student))
//
//        fun unknown(rows: List<SheetRow>, key: String, col: String, valid: Set<String>, label: String) =
//            rows.flatMap { r -> r.ids(col).filter { it !in valid }.map { "$label ${r[key]}: unknown $col '$it'" } }
//
//
//        // student rule: Teacher ID[i] teaches Course ID[i], and each teacher appears once (one course per teacher)
//        val teaches = teachers.associate { it[Col.TEACHER_CODE].trim() to it.ids(Col.COURSE_IDS).toSet() }
//        val pairing = students.flatMap { st ->
//            val id = st[Col.ARID_NO]
//            val t = st.ids(Col.TEACHER_IDS); val c = st.ids(Col.COURSE_IDS)
//            if (t.size != c.size) listOf("Student $id: Teacher ID and Course ID counts must match")
//            else buildList {
//                if (t.size != t.toSet().size) add("Student $id: only one course per teacher")
//                t.zip(c).forEach { (tc, cc) ->
//                    if (teaches[tc]?.contains(cc) == false) add("Student $id: teacher $tc does not teach $cc")
//                }
//            }
//        }
//
//        // email format: teacher dr.abc@biit.edu.pk, student = "<ARID No>@biit.edu.pk" (e.g. 2023-ARID-0000@biit.edu.pk)
//        val teacherMail = Regex("^dr\\.[a-z]+@biit\\.edu\\.pk$")
//        val studentMail = Regex("^\\d{4}-ARID-\\d{4}@biit\\.edu\\.pk$", RegexOption.IGNORE_CASE)
//        val teacherEmailErr = teachers.filter { !teacherMail.matches(it[Col.EMAIL].trim()) }
//            .map { "Teacher ${it[Col.TEACHER_CODE]}: email must look like dr.abc@biit.edu.pk" }
//        val studentEmailErr = students.filter {
//            val e = it[Col.EMAIL].trim()
//            !studentMail.matches(e) || !e.equals(it[Col.ARID_NO].trim() + "@biit.edu.pk", ignoreCase = true)
//        }.map { "Student ${it[Col.ARID_NO]}: email must be <ARID No>@biit.edu.pk" }
//
//        val out = mutableMapOf<SheetKind, List<String>>()
//        (unknown(teachers, Col.TEACHER_CODE, Col.COURSE_IDS, courses, "Teacher") + teacherEmailErr)
//            .let { if (it.isNotEmpty()) out[SheetKind.Teacher] = it.take(8) }
//        (unknown(students, Col.ARID_NO, Col.COURSE_IDS, courses, "Student") +
//                unknown(students, Col.ARID_NO, Col.TEACHER_IDS, teacherCodes, "Student") + pairing + studentEmailErr)
//            .let { if (it.isNotEmpty()) out[SheetKind.Student] = it.take(8) }
//        return out
//    }
//}