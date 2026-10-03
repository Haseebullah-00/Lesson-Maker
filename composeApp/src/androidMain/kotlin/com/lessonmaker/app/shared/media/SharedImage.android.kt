package com.lessonmaker.app.shared.media

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.lessonmaker.app.extensionFunctions.handleNull

import java.io.ByteArrayOutputStream

actual class SharedImage(private val bitmap: Bitmap?,private val fileName:String?=null) {
    actual fun toByteArray(): ByteArray? {
        return if (bitmap != null) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            @Suppress("MagicNumber") bitmap.compress(
                Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream
            )
            byteArrayOutputStream.toByteArray()
        } else {
            println("toByteArray null")
            null
        }
    }

    actual fun toImageBitmap(): ImageBitmap? {
        val byteArray = toByteArray()
        return if (byteArray != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()
        } else {
            println("toImageBitmap null")
            null
        }
    }

    actual fun getFileName(): String? {
        return fileName.handleNull()
    }

}

actual fun imageBitmapToByteArray(imageBitmap: ImageBitmap): ByteArray {
    val bitmap: Bitmap = imageBitmap.asAndroidBitmap()
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream) // Compress as JPEG (change if needed)
    return stream.toByteArray()
}