package com.lessonmaker.app.models.student.lesson.lessonContent

import com.lessonmaker.app.models.student.lesson.StudentMyCourseLessonData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudentLessonContentApiResponse(
    @SerialName("status")
    val status: String? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: LessonContentData?=null
)
@Serializable
data class LessonContentData(

    @SerialName("lessonId")
    val lessonId: Int?=0,
    @SerialName("lessonTitle")
    val lessonTitle: String?=null,
    @SerialName("lessonContent")
    val lessonContent: String?=null,

    @SerialName("tableOfContents")
    val tableOfContents: List<TableOfContents>?=null



    )
@Serializable
data class TableOfContents(
    @SerialName("headingLevel")
    val headingLevel: Int?=0,
    @SerialName("headingTitle")
    val headingTitle: String?=null,
    @SerialName("headingId")
    val headingId: String?=null,
    @SerialName("sortOrder")
    val sortOrder: Int?=0,

)
