package com.lessonmaker.app.shared.media.pdf

import androidx.compose.runtime.Composable


import androidx.compose.runtime.remember
import platform.Foundation.NSURL

import platform.UIKit.UIApplication
import platform.UIKit.UIDocumentPickerViewController
import platform.UIKit.UIDocumentPickerDelegateProtocol
import platform.UIKit.UIDocumentPickerMode
import platform.darwin.NSObject

@Composable
actual fun rememberGalleryManagerForPdf(onResult: (SharedPdf?) -> Unit): PdfGalleryManager {
    val delegate = remember {
        object : NSObject(), UIDocumentPickerDelegateProtocol {
            override fun documentPicker(
                controller: UIDocumentPickerViewController,
                didPickDocumentsAtURLs: List<*>
            ) {
                val url = didPickDocumentsAtURLs.firstOrNull() as? NSURL
                onResult(url?.let { SharedPdf(it) })
            }

            override fun documentPickerWasCancelled(controller: UIDocumentPickerViewController) {
                onResult(null)
            }
        }
    }

    return remember {
        PdfGalleryManager {
            val picker = UIDocumentPickerViewController(
                documentTypes = listOf("com.adobe.pdf"), // MIME type for PDFs
                inMode = UIDocumentPickerMode.UIDocumentPickerModeImport
            )
            picker.delegate = delegate
            picker.allowsMultipleSelection = false

            UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                picker, true, null
            )
        }
    }
}

actual class PdfGalleryManager actual constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launch() = onLaunch()
}
