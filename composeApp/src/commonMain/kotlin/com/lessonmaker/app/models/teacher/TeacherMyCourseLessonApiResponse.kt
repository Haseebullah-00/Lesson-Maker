package com.lessonmaker.app.models.teacher

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeacherMyCourseLessonApiResponse(
    @SerialName("status")
    val status: String? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: List<TeacherMyCourseLessonData>?=null
)
@Serializable
data class TeacherMyCourseLessonData(
    @SerialName("lessonId")
    val lessonId: Int?=0,
    @SerialName("lessonTitle")
    val lessonTitle: String?=null,
    @SerialName("createdAt")
    val createdAt: String?=null,

    )