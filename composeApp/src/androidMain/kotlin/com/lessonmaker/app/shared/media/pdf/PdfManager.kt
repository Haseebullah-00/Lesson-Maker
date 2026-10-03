package com.lessonmaker.app.shared.media.pdf

import androidx.compose.runtime.Composable

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberGalleryManagerForPdf(onResult: (SharedPdf?) -> Unit): PdfGalleryManager {
    val context = LocalContext.current
    var onLaunch by remember { mutableStateOf<() -> Unit>({}) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        onResult(uri?.let { SharedPdf(it, context) })
    }

    onLaunch = { launcher.launch("application/pdf") }

    return remember {
        PdfGalleryManager(
            onLaunch = { onLaunch() }
        )
    }
}

actual class PdfGalleryManager actual constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launch() = onLaunch()
}
