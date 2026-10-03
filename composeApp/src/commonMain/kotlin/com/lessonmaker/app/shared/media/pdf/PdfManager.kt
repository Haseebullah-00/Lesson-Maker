package com.lessonmaker.app.shared.media.pdf

import androidx.compose.runtime.Composable

@Composable
expect fun rememberGalleryManagerForPdf(onResult: (SharedPdf?) -> Unit): PdfGalleryManager

expect class PdfGalleryManager(
    onLaunch: () -> Unit
) {
    fun launch()
}
