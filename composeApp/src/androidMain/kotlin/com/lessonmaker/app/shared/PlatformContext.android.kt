package com.lessonmaker.app.shared

import android.app.Activity
import android.content.Context

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

actual class PlatformContext(private val androidContext: Context) {
    fun getActivity(): Activity? = androidContext as? Activity
}

@Composable
actual fun getPlatformContext(): PlatformContext {
    return PlatformContext(LocalContext.current)
}

actual fun recreateActivity(context: PlatformContext) {
    context.getActivity()?.recreate()
}

