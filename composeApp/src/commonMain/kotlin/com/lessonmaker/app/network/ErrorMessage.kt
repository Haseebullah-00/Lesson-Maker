package com.lessonmaker.app.network


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorMessage(
    @SerialName("message")
    val message: String,

    @SerialName("status")
    val success: Boolean
)