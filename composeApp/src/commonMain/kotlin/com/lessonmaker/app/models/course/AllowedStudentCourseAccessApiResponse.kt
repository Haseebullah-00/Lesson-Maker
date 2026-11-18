package com.lessonmaker.app.models.course

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable




@Serializable
data class AllowedStudentCourseAccessApiResponse(

    @SerialName("status")
    val status: String? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: List<AllowedCourseAccessData>?=null
)
@Serializable
data class AllowedCourseAccessData(
    @SerialName("studentId")
    val studentId: Int? = 0,
    @SerialName("aridNo")
    val aridNo: String? = null,
    @SerialName("studentName")
    val studentName: String? = null,
    @SerialName("accessId")
    val accessId: Int? = 0,
    @SerialName("courseName")
    val courseName: String? = null,

)