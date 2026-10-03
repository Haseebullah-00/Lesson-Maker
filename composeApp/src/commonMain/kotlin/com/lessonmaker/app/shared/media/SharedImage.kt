package com.lessonmaker.app.shared.media

import androidx.compose.ui.graphics.ImageBitmap

expect class SharedImage {
    fun toByteArray(): ByteArray?
    fun toImageBitmap(): ImageBitmap?
    fun getFileName():String?
}
expect fun imageBitmapToByteArray(imageBitmap: ImageBitmap): ByteArray
