package com.lessonmaker.app.shared.media.video

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
expect fun rememberGalleryManagerForVideo(onResult: (SharedVideo?) -> Unit): VideoGalleryManager

expect class VideoGalleryManager(
    onLaunch: () -> Unit
) {
    fun launch()
}



expect suspend fun getVideoThumbnailBitmap(url: String): ImageBitmap?