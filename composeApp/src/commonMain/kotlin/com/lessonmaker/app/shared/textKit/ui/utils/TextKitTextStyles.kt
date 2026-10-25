package com.lessonmaker.app.shared.textKit.ui.utils


import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lessonmaker.app.shared.textkit.theme.TextKitTheme

@Composable
internal fun textKitTitleStyle(): TextStyle = TextStyle(
    fontFamily = TextKitTheme.typography.fontFamily,
    fontSize = 16.sp, fontWeight = FontWeight.SemiBold,
    color = TextKitTheme.colors.onSurface,
)

@Composable
internal fun textKitBodyStyle(): TextStyle = TextStyle(
    fontFamily = TextKitTheme.typography.fontFamily,
    fontSize = 14.sp, fontWeight = FontWeight.Normal,
    color = TextKitTheme.colors.onSurface,
)

@Composable
internal fun textKitCaptionStyle(): TextStyle = TextStyle(
    fontFamily = TextKitTheme.typography.fontFamily,
    fontSize = 12.sp, fontWeight = FontWeight.Normal,
    color = TextKitTheme.colors.onSurfaceVariant,
)

@Composable
internal fun textKitButtonStyle(): TextStyle = TextStyle(
    fontFamily = TextKitTheme.typography.fontFamily,
    fontSize = 14.sp, fontWeight = FontWeight.SemiBold,
)