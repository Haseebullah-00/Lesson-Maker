package com.lessonmaker.app.shared.statusBarStyle

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@SuppressLint("ContextCastToActivity")
@Composable
actual fun setStatusBarIconColor(color: StatusBarIconColor) {
    val activity = LocalContext.current as? Activity ?: return
    val decorView = activity.window.decorView

    val flags = decorView.systemUiVisibility
    decorView.systemUiVisibility = if (color == StatusBarIconColor.Dark) {
        flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }
}

