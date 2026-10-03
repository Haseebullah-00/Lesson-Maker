package com.lessonmaker.app.shared.media.video

import android.content.ContentResolver
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.network.NetworkLogs
import com.lessonmaker.app.utility.FileManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import org.koin.compose.koinInject
import java.io.File
import kotlin.let

@Composable
actual fun rememberGalleryManagerForVideo(onResult: (SharedVideo?) -> Unit): VideoGalleryManager {
    val context = LocalContext.current
    val commonLoading = koinInject<CommonLoading>()
    val contentResolver: ContentResolver = context.contentResolver
    val videoGalleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                NetworkLogs("videoIsLessThen20MB","003")
                FileManager.getPath(context = context, uri)?.let { filePath->
                    NetworkLogs("videoIsLessThen20MB","004")
                    // You can add video compression or processing logic if needed here.
                    CoroutineScope(Dispatchers.IO).launch {
                        // If you need to reduce video size, similar to images, you can handle it here.
                        val videoFile = File(filePath)
                        if (videoFile.isLessThanMB(20)) {
                            NetworkLogs("videoIsLessThen20MB","001")
                            withContext(Dispatchers.Main) {
                                onResult.invoke(
                                    SharedVideo(
                                        videoUri = Uri.fromFile(videoFile),
                                        fileName = filePath
                                    )
                                )
                            }
                        } else {
                            NetworkLogs("videoIsLessThen20MB","002")
                           commonLoading.alertMessage="Video size should be less than 20 MB"
                        }
                    }
                }
            }
        }

    return remember {
        VideoGalleryManager(onLaunch = {
            videoGalleryLauncher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.VideoOnly
                )
            )
        })
    }
}

actual class VideoGalleryManager actual constructor(private val onLaunch: () -> Unit) {
    actual fun launch() {
        onLaunch()
    }
}

actual suspend fun getVideoThumbnailBitmap(url: String): ImageBitmap? = withContext(Dispatchers.IO) {
    try {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(url, HashMap())
        val bitmap = retriever.frameAtTime
        retriever.release()
        bitmap?.asImageBitmap()
    } catch (e: Exception) {
        null
    }
}

fun File.isLessThanMB(mb: Int): Boolean {
    if (!this.exists()) return false
    val sizeInBytes = this.length()
    val limitInBytes = mb * 1024 * 1024
    return sizeInBytes < limitInBytes
}