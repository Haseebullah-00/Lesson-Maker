package com.lessonmaker.app.shared


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.lessonmaker.app.shared.media.SharedImage
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerCameraCaptureMode
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerEditedImage
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject

@Composable
actual fun rememberCameraManager(
    allowsEditing: Boolean,
    onResult: (SharedImage?) -> Unit
): CameraManager {

    val imagePicker = UIImagePickerController()

    val delegate = remember {
        object : NSObject(), UIImagePickerControllerDelegateProtocol,
            UINavigationControllerDelegateProtocol {

            override fun imagePickerController(
                picker: UIImagePickerController,
                didFinishPickingMediaWithInfo: Map<Any?, *>
            ) {
                val image =
                    didFinishPickingMediaWithInfo[UIImagePickerControllerEditedImage] as? UIImage
                        ?: didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage

                onResult(SharedImage(image))
                picker.dismissViewControllerAnimated(true, null)
            }
        }
    }

    return remember {
        CameraManager {
            imagePicker.sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            imagePicker.allowsEditing = allowsEditing
            imagePicker.delegate = delegate

            UIApplication.sharedApplication.keyWindow?.rootViewController
                ?.presentViewController(imagePicker, true, null)
        }
    }
}

actual class CameraManager actual constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launch() {
        onLaunch()
    }
}