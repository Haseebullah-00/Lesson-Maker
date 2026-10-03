package com.lessonmaker.app.shared.media.video

import androidx.compose.runtime.Composable

import androidx.compose.runtime.remember
import platform.Foundation.NSURL
import platform.UIKit.*
import platform.darwin.NSObject
/**
 * Expect declaration of a CameraManager for capturing media.
 * Each platform will provide its own actual implementation.
 */
actual class VideoCameraManager actual constructor(private val onLaunch: () -> Unit) {
    actual fun launch() {
        onLaunch()
    }
}



@Composable
actual fun rememberCameraManagerForVideo(onResult: (SharedVideo?) -> Unit): VideoCameraManager {
    val picker = UIImagePickerController()

    val videoDelegate = remember {
        object : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {
            override fun imagePickerController(
                picker: UIImagePickerController,
                didFinishPickingMediaWithInfo: Map<Any?, *>
            ) {
                val mediaType = didFinishPickingMediaWithInfo[UIImagePickerControllerMediaType] as? String
                val mediaURL = didFinishPickingMediaWithInfo[UIImagePickerControllerMediaURL] as? NSURL

                if (mediaType == "public.movie" && mediaURL != null) {
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
        VideoCameraManager {
            picker.setSourceType(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera)
            picker.setMediaTypes(listOf("public.movie")) // Only video recording
            picker.setCameraCaptureMode(UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModeVideo)
            picker.setDelegate(videoDelegate)

            UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                picker, true, null
            )
        }
    }
}
