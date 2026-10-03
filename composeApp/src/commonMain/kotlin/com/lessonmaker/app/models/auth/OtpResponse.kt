package com.lessonmaker.app.models.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OtpResponse(
    @SerialName("status") val status: String? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("error")  val error: Map<String, List<String>>? = null,
    @SerialName("access_token") val accessToken: String? = null,
    @SerialName("token_type") val tokenType: String? = null,
    @SerialName("expires_at") val expiresAt: String? = null,
    @SerialName("firebase_user_key") val firebaseUserKey: String? = null,
    @SerialName("user")
    var user: SingleUserModel? = null
) {
    fun getAllErrorMessages(): String {
        return error!!.map { (field, errors) ->
            errors.map { "$field: $it" } // Format: "field_name: error_message"
        }?.joinToString("\n") ?: ""
    }
}