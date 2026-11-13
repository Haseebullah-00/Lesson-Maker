package com.lessonmaker.app.models.student


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudentMyCoursesApiResponse(

    @SerialName("status")
    val status: String? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: List<StudentCoursesData>?=null
)
@Serializable
data class StudentCoursesData(
    @SerialName("courseId")
    val courseId: Int? =0,
    @SerialName("courseCode")
    val courseCode: String? =null,
    @SerialName("courseName")
    val courseName: String? =null,
    @SerialName("teacherId")
    val teacherId: Int? =0,
    @SerialName("teacherName")
    val teacherName: String? =null,
    @SerialName("lessonCount")
    val lessonCount: Int? =0,



)