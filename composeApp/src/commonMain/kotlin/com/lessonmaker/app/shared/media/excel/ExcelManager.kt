package com.lessonmaker.app.shared.media.excel

import androidx.compose.runtime.Composable

@Composable
expect fun rememberGalleryManagerForExcel(onResult: (SharedExcel?) -> Unit): ExcelGalleryManager

expect class ExcelGalleryManager(
    onLaunch: () -> Unit
) {
    fun launch()
}