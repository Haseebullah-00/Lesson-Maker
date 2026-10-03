package com.lessonmaker.app.shared.statusBarStyle

import androidx.compose.runtime.Composable

enum class StatusBarIconColor {
    Light, // White icons
    Dark   // Black icons
}

@Composable
expect fun setStatusBarIconColor(color: StatusBarIconColor)
