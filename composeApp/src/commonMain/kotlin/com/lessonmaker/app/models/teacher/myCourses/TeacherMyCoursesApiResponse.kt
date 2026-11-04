package com.lessonmaker.app.models.teacher.myCourses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeacherMyCoursesApiResponse(
    @SerialName("status")
    val status: String? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: List<TeacherCourseData>?=null
    )
@Serializable
data class TeacherCourseData(
    @SerialName("courseId")
    val courseId: Int?=0,
    @SerialName("courseCode")
    val courseCode: String?=null,
    @SerialName("courseName")
    val courseName: String?=null,
    @SerialName("lessonCount")
    val lessonCount: Int?=0,

)