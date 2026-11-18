package com.lessonmaker.app.models.course

import com.lessonmaker.app.models.student.StudentCoursesData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllowCourseAccessApiResponse(

    @SerialName("status")
    val status: String? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: List<AllowCourseAccessData>?=null
)
@Serializable
data class AllowCourseAccessData(
    @SerialName("studentId")
    val studentId: Int? = 0,
    @SerialName("aridNo")
    val aridNo: String? = null,
    @SerialName("studentName")
    val studentName: String? = null,
)
