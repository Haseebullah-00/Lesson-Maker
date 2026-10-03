package com.lessonmaker.app.shared.statusBarStyle

import androidx.compose.runtime.Composable
import platform.UIKit.*

@Composable
actual fun setStatusBarIconColor(color: StatusBarIconColor) {
    val style = when (color) {
        StatusBarIconColor.Light -> UIStatusBarStyleLightContent
        StatusBarIconColor.Dark -> UIStatusBarStyleDarkContent
    }
    UIApplication.sharedApplication.setStatusBarStyle(style, animated = true)
}