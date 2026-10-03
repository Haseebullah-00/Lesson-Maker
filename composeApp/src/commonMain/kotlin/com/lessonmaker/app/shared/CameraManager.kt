package com.lessonmaker.app.shared
import androidx.compose.runtime.Composable
import com.lessonmaker.app.shared.media.SharedImage

@Composable
expect fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager

expect class CameraManager(
    onLaunch: () -> Unit
) {
    fun launch()
}