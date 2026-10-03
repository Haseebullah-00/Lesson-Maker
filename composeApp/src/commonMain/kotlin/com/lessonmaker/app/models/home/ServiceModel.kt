package com.lessonmaker.app.models.home

import org.jetbrains.compose.resources.DrawableResource

data class ServiceModel(
    var image: DrawableResource,
    var desc: String,
    val name: String,
    val price: String,
)
