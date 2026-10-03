package com.lessonmaker.app.shared.media.video

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.extensionFunctions.handleNull
import com.lessonmaker.app.shared.map.findActivity
import com.lessonmaker.app.utility.FileManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import org.koin.compose.koinInject
import java.io.File
import kotlin.let

@Composable
actual fun rememberCameraManagerForVideo(onResult: (SharedVideo?) -> Unit): VideoCameraManager {
    val context = LocalContext.current
    val commonLoading = koinInject<CommonLoading>()
    return remember {
        VideoCameraManager(onLaunch = {
            context.findActivity()?.let { activity ->
                activity.cameraViewModel?.recordVideoNow(activity = activity) { uri ->
                    uri.let {
                        FileManager.getPath(context = context, uri)?.let { path ->
                            CoroutineScope(Dispatchers.IO).launch {
                                val videoFile = File(path)
                                if (videoFile.isLessThanMB(20)) {
                                    withContext(Dispatchers.Main) {
                                        onResult.invoke(
                                            SharedVideo(
                                                videoUri = uri,
                                                fileName = videoFile.name.handleNull()
                                            )
                                        )
                                    }
                                } else {
                                    commonLoading.alertMessage="Video size should be less than 20 MB"
                                }
                            }
                        }
                    }
                }
            }
        })
    }
}


/**
 * Expect declaration of a CameraManager for capturing media.
 * Each platform will provide its own actual implementation.
 */

actual class VideoCameraManager actual constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launch() {
        onLaunch()
    }
}
