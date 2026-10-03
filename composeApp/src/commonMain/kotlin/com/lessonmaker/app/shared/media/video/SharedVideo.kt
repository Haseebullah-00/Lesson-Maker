package com.lessonmaker.app.shared.media.video

import androidx.compose.ui.graphics.ImageBitmap

expect class SharedVideo {
    fun toByteArray(): ByteArray?
    fun getFileName(): String?
    fun getThumbnail(): ImageBitmap?
    fun getUri(): Any?
}
