// androidMain/sharedLogic/SharedVideo.kt
package com.lessonmaker.app.shared.media.video

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.lessonmaker.app.extensionFunctions.handleNull
import com.lessonmaker.app.utility.AppContextProvider

import java.io.ByteArrayOutputStream
import kotlin.also
import kotlin.io.use
import kotlin.let

actual class SharedVideo(
    private val videoUri: Uri?,
    private val fileName: String? = null
) {

    private val appContext: Context
        get() = AppContextProvider.appContext  // Provide static context if needed

    actual fun toByteArray(): ByteArray? {
        return try {
            videoUri?.let { uri ->
                val inputStream = appContext.contentResolver.openInputStream(uri)
                inputStream?.use { stream ->
                    // Buffer to read in chunks
                    val buffer = ByteArray(8192)
                    val byteArrayOutputStream = ByteArrayOutputStream()

                    var bytesRead: Int
                    while (stream.read(buffer).also { bytesRead = it } != -1) {
                        byteArrayOutputStream.write(buffer, 0, bytesRead)
                    }

                    // Return byte array after reading
                    byteArrayOutputStream.toByteArray().also {
                        println("Read bytes: ${it.size}")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    actual fun getFileName(): String? {
        return fileName.handleNull()
    }

    actual fun getThumbnail(): ImageBitmap? {
        return try {
            if (videoUri == null) return null
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(appContext, videoUri)
            val bitmap = retriever.frameAtTime
            retriever.release()
            bitmap?.asImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    actual fun getUri(): Any? {
       return videoUri
    }

}
