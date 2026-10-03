package com.lessonmaker.app.shared.media
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.lessonmaker.app.extensionFunctions.handleNull
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.get
import kotlinx.cinterop.reinterpret
import org.jetbrains.skia.Image
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation

actual class SharedImage(private val image: UIImage?,val fileName:String?=null) {
    @OptIn(ExperimentalForeignApi::class)
    actual fun toByteArray(): ByteArray? {
        return if (image != null) {
            val imageData = UIImageJPEGRepresentation(image, COMPRESSION_QUALITY)
                ?: throw IllegalArgumentException("image data is null")
            val bytes = imageData.bytes ?: throw IllegalArgumentException("image bytes is null")
            val length = imageData.length

            val data: CPointer<ByteVar> = bytes.reinterpret()
            ByteArray(length.toInt()) { index -> data[index] }
        } else {
            null
        }

    }

    actual fun toImageBitmap(): ImageBitmap? {
        val byteArray = toByteArray()
        return if (byteArray != null) {
            Image.makeFromEncoded(byteArray).toComposeImageBitmap()
        } else {
            null
        }
    }

    private companion object {
        const val COMPRESSION_QUALITY = 0.99
    }

    actual fun getFileName(): String? {
       return fileName.handleNull()
    }

}

actual fun imageBitmapToByteArray(imageBitmap: ImageBitmap): ByteArray {
    return ByteArray(0)
}

