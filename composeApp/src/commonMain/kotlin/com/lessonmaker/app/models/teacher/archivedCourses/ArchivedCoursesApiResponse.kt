package com.lessonmaker.app.models.teacher.archivedCourses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeacherArchivedCoursesApiResponse(
    @SerialName("status")
    val status: String? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: List<TeacherArchivedCourseData>?=null
)
@Serializable
data class TeacherArchivedCourseData(
    @SerialName("courseId")
    val courseId: Int?=0,
    @SerialName("courseCode")
    val courseCode: String?=null,
    @SerialName("courseName")
    val courseName: String?=null,
    @SerialName("lessonCount")
    val lessonCount: Int?=0,

    )