package com.lessonmaker.app.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape


@Composable
fun MainTransparentBox(
    modifier: Modifier = Modifier,
    shape: Shape = roundedShape23,
    backgroundColor: Color = Color.White,
    onClick: (() -> Unit)?=null,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor, shape = shape).clickable(enabled = onClick !=null, onClick = {
                onClick?.invoke()
            }),
        content = content
    )
}