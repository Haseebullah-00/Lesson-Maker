package com.lessonmaker.app.shared.media.video

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import platform.Foundation.NSURL
import platform.UIKit.*
import platform.darwin.NSObject

@Composable
actual fun rememberGalleryManagerForVideo(onResult: (SharedVideo?) -> Unit): VideoGalleryManager {
    val picker = UIImagePickerController()

    val videoDelegate = remember {
        object : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {
            override fun imagePickerController(
                picker: UIImagePickerController,
                didFinishPickingMediaWithInfo: Map<Any?, *>
            ) {
                val mediaType = didFinishPickingMediaWithInfo[UIImagePickerControllerMediaType] as? String
                val mediaURL = didFinishPickingMediaWithInfo[UIImagePickerControllerMediaURL] as? NSURL

                if (mediaType == "public.movie"&& mediaURL != null) {
                    onResult(SharedVideo(mediaURL))
                } else {
                    onResult(null)
                }
                picker.dismissViewControllerAnimated(true, null)
            }

            override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                onResult(null)
                picker.dismissViewControllerAnimated(true, null)
            }
        }
    }

    return remember {
        VideoGalleryManager {
            picker.setSourceType(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary)
            picker.setMediaTypes(listOf("public.movie")) // Only videos
            picker.setAllowsEditing(false)
            picker.delegate = videoDelegate

            UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                picker, true, null
            )
        }
    }
}
actual class VideoGalleryManager actual constructor(private val onLaunch: () -> Unit) {
    actual fun launch() {
        onLaunch()
    }
}

actual suspend fun getVideoThumbnailBitmap(url: String): ImageBitmap? {
  return null
}