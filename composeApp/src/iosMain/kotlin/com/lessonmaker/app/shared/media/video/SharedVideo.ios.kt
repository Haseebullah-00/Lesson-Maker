package com.lessonmaker.app.shared.media.video

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.cinterop.*
import platform.AVFoundation.*
import platform.CoreGraphics.CGImageRef
import platform.CoreMedia.CMTime
import platform.CoreMedia.CMTimeMake
import platform.Foundation.*
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import org.jetbrains.skia.Image
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.get

actual class SharedVideo(private val nsUrl: NSURL) {

    @OptIn(ExperimentalForeignApi::class)
    actual fun toByteArray(): ByteArray? {
        val data = NSData.dataWithContentsOfURL(nsUrl) ?: return null
        val bytes = data.bytes?.reinterpret<ByteVar>() ?: return null
        return ByteArray(data.length.toInt()) { i -> bytes[i] }
    }

    actual fun getFileName(): String? {
        return nsUrl.lastPathComponent
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun getThumbnail(): ImageBitmap? {
        val asset = AVAsset.assetWithURL(nsUrl)
        val imageGenerator = AVAssetImageGenerator(asset).apply {
            appliesPreferredTrackTransform = true
        }

        val time = CMTimeMake(value = 1, timescale = 1)

        memScoped {
            val actualTime = alloc<CMTime>()
            val cgImage: CGImageRef? = imageGenerator.copyCGImageAtTime(time, actualTime.ptr, null)
            if (cgImage != null) {
                val uiImage = UIImage.imageWithCGImage(cgImage)
                return uiImage.toImageBitmap()
            }
        }
        return null
    }

    actual fun getUri(): Any? = nsUrl.absoluteString


    fun UIImage.toImageBitmap(): ImageBitmap? {
        val byteArray = toByteArray()
        return if (byteArray != null) {
                val jpegData: NSData = UIImageJPEGRepresentation(this, 1.0) ?: return null
           val byteArray = jpegData.toByteArray()
            Image.makeFromEncoded(byteArray).toComposeImageBitmap()
        } else {
            null
        }
    }
}
//fun UIImage.toImageBitmap(): ImageBitmap? {
//    val jpegData: NSData = UIImageJPEGRepresentation(this, 1.0) ?: return null
//    val byteArray = jpegData.toByteArray()
//    val skiaImage = Image.makeFromEncoded(byteArray)
//    return skiaImage.toImageBitmap()
//}

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
    val bytes = this.bytes?.reinterpret<ByteVar>() ?: return ByteArray(0)
    return ByteArray(this.length.toInt()) { index -> bytes[index] }
}
//fun UIImage.toImageBitmap(): ImageBitmap? {
//    val nsData: NSData = UIImagePNGRepresentation(this) ?: return null
//    val byteArray = nsData.toByteArray()
//    return Image.makeFromEncoded(byteArray).asImageBitmap()
//}