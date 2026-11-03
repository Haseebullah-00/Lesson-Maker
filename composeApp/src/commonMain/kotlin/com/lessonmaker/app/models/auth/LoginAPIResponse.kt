package com.lessonmaker.app.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import lessonmaker.composeapp.generated.resources.Res

@Serializable
data class LoginAPIResponse(
    @SerialName("status")
    val status: String? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("data")
    val data:LoginData? = null
)
@Serializable
data class LoginData(
    @SerialName("id")
    val id : Int?=0,
    @SerialName("name")
    val name: String?=null,
    @SerialName("email")
    val email: String?=null,
    @SerialName("aridNo")
    val aridNo: String?=null

)



@Serializable
data class TeacherModel(
    @SerialName("id")
    val id: Int? = 0,

    @SerialName("name")
    val name: String? = null,

    @SerialName("email")
    val email: String? = null
)

@Serializable
data class StudentModel(
    @SerialName("id")
    val id: Int? = 0,

    @SerialName("name")
    val name: String? = null,

    @SerialName("email")
    val email: String? = null,

    @SerialName("aridNo")
    val aridNo: String? = null
)