package com.lessonmaker.app.models.student.lesson

import com.lessonmaker.app.models.student.StudentCoursesData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudentMyCourseLessonApiResponse(
    @SerialName("status")
    val status: String? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: List<StudentMyCourseLessonData>?=null
)
@Serializable
data class StudentMyCourseLessonData(
    @SerialName("lessonId")
    val lessonId: Int?=0,
    @SerialName("lessonTitle")
    val lessonTitle: String?=null,
    @SerialName("createdAt")
    val createdAt: String?=null,

    )