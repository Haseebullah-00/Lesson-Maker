package com.lessonmaker.app.shared.media.pdf

import android.content.Context
import android.net.Uri
import java.io.ByteArrayOutputStream
import android.provider.OpenableColumns

actual class SharedPdf(
    private val pdfUri: Uri?,
    private val context: Context
) {

    actual fun toByteArray(): ByteArray? {
        return try {
            pdfUri?.let { uri ->
                val inputStream = context.contentResolver.openInputStream(uri)
                inputStream?.use { stream ->
                    val buffer = ByteArray(8192)
                    val byteArrayOutputStream = ByteArrayOutputStream()

                    var bytesRead: Int
                    while (stream.read(buffer).also { bytesRead = it } != -1) {
                        byteArrayOutputStream.write(buffer, 0, bytesRead)
                    }

                    byteArrayOutputStream.toByteArray()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    actual fun getFileName(): String? {
        return try {
            if (pdfUri == null) return null
            val cursor = context.contentResolver.query(pdfUri, null, null, null, null)
            cursor?.use {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (it.moveToFirst()) it.getString(nameIndex) else null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    actual fun getUri(): Any? = pdfUri
}
