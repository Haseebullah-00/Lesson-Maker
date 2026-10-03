package com.lessonmaker.app.shared.media

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri

object BitmapUtils {
    fun getBitmapFromUri(uri: Uri, contentResolver: ContentResolver): Bitmap? {
        return runCatching {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val rotation = getImageRotation(uri, contentResolver)
                rotateBitmapIfNeeded(bitmap, rotation)
            }
        }.onFailure { e ->
            e.printStackTrace()
            println("getBitmapFromUri Exception: ${e.localizedMessage}")
        }.getOrNull()
    }

    private fun getImageRotation(uri: Uri, contentResolver: ContentResolver): Int {
        return runCatching {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                val exif = ExifInterface(inputStream)
                when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> 270
                    else -> 0
                }
            } ?: 0
        }.getOrDefault(0)
    }

    private fun rotateBitmapIfNeeded(bitmap: Bitmap, rotation: Int): Bitmap {
        return if (rotation != 0) {
            val matrix = Matrix().apply { postRotate(rotation.toFloat()) }
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true).also {
                bitmap.recycle()
            }
        } else {
            bitmap
        }
    }
}