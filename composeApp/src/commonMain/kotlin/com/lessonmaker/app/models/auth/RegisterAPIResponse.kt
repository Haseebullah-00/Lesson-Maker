package com.lessonmaker.app.models.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterAPIResponse(
    @SerialName("message")
    var message: String? = null,
    @SerialName("status")
    var status: String? = null,
    @SerialName("user")
    var user: SingleUserModel? = null
)

@Serializable
data class SingleUserModel(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("email") val email: String,
    @SerialName("is_social") val isSocial: String,
    @SerialName("image") val image: String,
    @SerialName("dial_code") val dialCode: String,
    @SerialName("phone") val phone: String,
    @SerialName("is_email_verifed") val isEmailVerified: String,
    @SerialName("is_phone_verified") val isPhoneVerified: String,
    @SerialName("ref_code") val refCode: String,
    @SerialName("firebase_user_key") val firebaseUserKey: String
)