package com.lessonmaker.app.shared.media.video

import androidx.compose.runtime.Composable

/**
 * Expect declaration of a CameraManager for capturing media.
 * Each platform will provide its own actual implementation.
 */
expect class VideoCameraManager(
    onLaunch: () -> Unit
) {
    fun launch()
}

/**
 * Composable function to get a CameraManager that captures videos.
 * Call `launch()` on it to start recording.
 */
@Composable
expect fun rememberCameraManagerForVideo(onResult: (SharedVideo?) -> Unit): VideoCameraManager
