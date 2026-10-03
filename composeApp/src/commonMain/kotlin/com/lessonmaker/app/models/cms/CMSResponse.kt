package com.lessonmaker.app.models.cms


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CMSResponse(
    @SerialName("errors")
    var errors: Errors? = null,
    @SerialName("message")
    var message: String? = null,
    @SerialName("oData")
    var oData: OData? = null,
    @SerialName("status")
    var status: String? = null
) {
    @Serializable
    class Errors

    @Serializable
    data class OData(
        @SerialName("desc_en")
        var descEn: String? = null,
        @SerialName("id")
        var id: Int? = null,
        @SerialName("title_en")
        var titleEn: String? = null
    )
}