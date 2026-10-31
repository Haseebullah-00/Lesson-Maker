package com.lessonmaker.app.mainScreen.studentFlow

import androidx.compose.runtime.mutableStateListOf


data class StudentCourse(
    val id: String,
    val code: String,
    val title: String,
    val instructor: String,
    val notesCount: Int
)

data class StudentLesson(
    val id: String,
    val courseId: String,
    val number: Int,
    val title: String,
    val date: String
)

data class StudentBullet(val label: String, val text: String)

data class StudentLessonContent(
    val lessonId: String,
    val toc: List<String>,
    val heading: String,
    val intro: String,
    val bullets: List<StudentBullet>,
    val diagramCaption: String,
    val resourceUrl: String
)

data class StudentInfo(
    val name: String,
    val arid: String,
    val email: String,
    val program: String,
    val department: String,
    val semester: String
)


object StudentDummyStore {
    val student = StudentInfo(
        name = "Sajid Mahmood",
        arid = "23-ARID-0428",
        email = "sajid.mahmood@biit.edu.pk",
        program = "Student, BSCS Dept",
        department = "Computer Science",
        semester = "7th"
    )

    val courses = listOf(
        StudentCourse("1", "CS-101", "Programming Fundamentals", "Dr. Adnan Ahmed", 14),
        StudentCourse("2", "ENG-101", "Functional English", "Mr. Khalid", 8),
        StudentCourse("3", "CSC-205", "Discrete Structures", "Dr. Mohsin", 8),
    )

    val lessons = listOf(
        StudentLesson("l1", "1", 1, "Introduction to Programming", "Oct 12, 2025"),
        StudentLesson("l2", "1", 2, "Control Structures", "Oct 18, 2025"),
        StudentLesson("l3", "1", 3, "Functions and Arrays", "Nov 02, 2025"),
    )

    fun lessonsOf(courseId: String) = lessons.filter { it.courseId == courseId }.ifEmpty { lessons }
    fun lesson(id: String) = lessons.firstOrNull { it.id == id }

    fun content(lessonId: String) = StudentLessonContent(
        lessonId = lessonId,
        toc = listOf(
            "Introduction to Programming",
            "Variables and Data Types",
            "Control Structures (Conditions and Loops)"
        ),
        heading = "Programming Fundamentals Basics",
        intro = "Programming Fundamentals provides the basic concepts needed to understand how " +
                "programs are designed and developed. In this lesson, we will cover the " +
                "foundational concepts of programming, including variables, data types, " +
                "operators, input/output, and basic control statements.",
        bullets = listOf(
            StudentBullet("Variables & Data Types:", "Defines how data is stored, represented, and used in a program."),
            StudentBullet("Control Structures:", "Controls program flow using conditions, loops, and decision-making statements.")
        ),
        diagramCaption = "Diagram: Common & Expected Flow",
        resourceUrl = "https://www.youtube.com/watch?v=KvFSMDsRUA4&utm_source"
    )
}

data class StudentEditCourse(
    val id: String,
    val teacher: String,
    val code: String,
    val title: String,
    val since: String,
    val notesCount: Int
)

enum class EditRequestStatus { Pending, Approved, Rejected }

data class StudentEditRequest(
    val id: String,
    val code: String,
    val lessonTitle: String,
    val info: String,
    val status: EditRequestStatus,
    val note: String
)

/** Dummy data – replace with API later. Requests are observable so "My Requests" updates after sending. */
object StudentEditStore {
    val courses = listOf(
        StudentEditCourse("1", "Dr. Adnan Ahmed", "CS-101", "Programming Fundamentals", "Oct 20, 2025", 14),
        StudentEditCourse("3", "Dr. Adnan Ahmed", "CSC-205", "Discrete Structures", "Nov 01, 2025", 8),
    )

    val requests = mutableStateListOf(
        StudentEditRequest("r1", "CS-101", "Introduction to Programming",
            "Sent to Dr. Adnan Ahmed · Today, 10:45 AM", EditRequestStatus.Pending,
            "Waiting for approval. The lesson stays unchanged until then."),
        StudentEditRequest("r2", "CS-101", "Control Structures",
            "Approved by Dr. Adnan Ahmed · Oct 22, 2025", EditRequestStatus.Approved, ""),
        StudentEditRequest("r3", "CSC-205", "Sets and Relations",
            "Rejected by Dr. Mohsin · Nov 03, 2025", EditRequestStatus.Rejected,
            "Reason: Please keep the original definition."),
    )

    fun teacherOf(code: String) = courses.firstOrNull { it.code == code }?.teacher ?: "your teacher"

    fun sendRequest(code: String, lessonTitle: String) {
        requests.add(
            0,
            StudentEditRequest(
                id = "r${requests.size + 1}", code = code, lessonTitle = lessonTitle,
                info = "Sent to ${teacherOf(code)} · Just now",
                status = EditRequestStatus.Pending,
                note = "Waiting for approval. The lesson stays unchanged until then."
            )
        )
    }
}