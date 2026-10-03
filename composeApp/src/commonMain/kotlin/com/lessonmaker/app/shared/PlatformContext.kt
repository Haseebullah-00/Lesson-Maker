package com.lessonmaker.app.shared

import androidx.compose.runtime.Composable

expect class PlatformContext

@Composable
expect fun getPlatformContext(): PlatformContext

expect fun recreateActivity(context: PlatformContext)
