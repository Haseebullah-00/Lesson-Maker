package com.lessonmaker.app.shared

import android.content.ContentResolver
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.lessonmaker.app.extensionFunctions.handleNull
import com.lessonmaker.app.shared.map.findActivity
import com.lessonmaker.app.shared.media.BitmapUtils
import com.lessonmaker.app.shared.media.SharedImage
import com.lessonmaker.app.utility.FileManager
import com.lessonmaker.app.utility.ReduceImageSize


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@Composable
actual fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager {
    val context = LocalContext.current
    val contentResolver: ContentResolver = context.contentResolver
    return remember {
        CameraManager(
            onLaunch = {
                context.findActivity()?.let {
                    it.cameraViewModel?.takeImageNow(activity = it) { uri ->
                        uri.let {
                            FileManager.getPath(context = context, uri)?.let {
                                CoroutineScope(Dispatchers.IO).launch {
                                    val imageFile = ReduceImageSize.compressImageNew(
                                        old = File(it),
                                        inContext = context
                                    )
                                    withContext(Dispatchers.Main) {
                                        imageFile.let {
                                            onResult.invoke(
                                                SharedImage(
                                                    BitmapUtils.getBitmapFromUri(
                                                        Uri.fromFile(
                                                            it
                                                        ), contentResolver
                                                    ), fileName = imageFile.name.handleNull()
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

actual class CameraManager actual constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launch() {
        onLaunch()
    }
}