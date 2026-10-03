package com.lessonmaker.app.models.categoryModel

import org.jetbrains.compose.resources.DrawableResource

data class SellerWatchModel(
    var image: DrawableResource,
    var profile: DrawableResource,
    val name: String
)
