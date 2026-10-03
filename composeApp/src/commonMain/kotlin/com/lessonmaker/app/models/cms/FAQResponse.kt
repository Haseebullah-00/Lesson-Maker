package com.lessonmaker.app.models.cms


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FAQResponse(
    @SerialName("message")
    var message: String? = null,
    @SerialName("oData")
    var oData: List<OData>? = null,
    @SerialName("status")
    var status: String? = null
) {
    @Serializable
    data class OData(
        @SerialName("active")
        var active: String? = null,
        @SerialName("created_at")
        var createdAt: String? = null,
        @SerialName("created_by")
        var createdBy: String? = null,
        @SerialName("description")
        var description: String? = null,
        @SerialName("id")
        var id: String? = null,
        @SerialName("title")
        var title: String? = null,
        @SerialName("updated_at")
        var updatedAt: String? = null,
        @SerialName("updated_by")
        var updatedBy: String? = null
    )
}