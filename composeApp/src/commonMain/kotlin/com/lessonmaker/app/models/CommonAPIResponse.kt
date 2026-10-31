package com.lessonmaker.app.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommonAPIResponse(
    @SerialName("status")
    var status: String? = null,
    @SerialName("message")
    var message: String? = null,


) {

}