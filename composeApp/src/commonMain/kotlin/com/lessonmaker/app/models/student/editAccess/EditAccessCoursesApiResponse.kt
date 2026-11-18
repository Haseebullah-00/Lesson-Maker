package com.lessonmaker.app.models.student.editAccess

import com.lessonmaker.app.models.course.AllowedCourseAccessData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditAccessCoursesApiResponse(
    @SerialName("status")
    val status: String? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: List<EditCourseAccessData>?=null
)
@Serializable
data class EditCourseAccessData(
    @SerialName("teacherId")
    val teacherId: Int? = 0,
    @SerialName("teacherName")
    val teacherName: String? = null,
    @SerialName("courses")
    val courses: List<CourseAccessData>?=null
)
@Serializable
data class CourseAccessData(
    @SerialName("courseId")
    val courseId: Int? =0,
    @SerialName("courseCode")
    val courseCode: String? =null,
    @SerialName("courseName")
    val courseName: String? =null,
    @SerialName("lessonCount")
    val lessonCount: Int? =0,
    @SerialName("accessDate")
    val accessDate: String?=null,

)