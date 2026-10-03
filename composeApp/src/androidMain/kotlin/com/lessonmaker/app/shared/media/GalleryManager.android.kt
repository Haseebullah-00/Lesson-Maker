package com.lessonmaker.app.shared.media

import android.content.ContentResolver
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.lessonmaker.app.extensionFunctions.handleNull
import com.lessonmaker.app.utility.FileManager
import com.lessonmaker.app.utility.ReduceImageSize

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@Composable
actual fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager {
    val context = LocalContext.current
    val contentResolver: ContentResolver = context.contentResolver
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                FileManager.getPath(context = context,uri)?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        val imageFile= ReduceImageSize.compressImageNew(old = File(it), inContext = context)
                        withContext(Dispatchers.Main){
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
    return remember {
        GalleryManager(onLaunch = {
            galleryLauncher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        })
    }
}

actual class GalleryManager actual constructor(private val onLaunch: () -> Unit) {
    actual fun launch() {
        onLaunch()
    }
}