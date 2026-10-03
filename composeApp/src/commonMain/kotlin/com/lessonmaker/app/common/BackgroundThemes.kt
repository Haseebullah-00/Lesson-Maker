package com.lessonmaker.app.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.theme.White


import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.ic_splash_bg
import lessonmaker.composeapp.generated.resources.ic_youtube


@Composable
fun MainAppBackgroundWhite() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {

    }
}



@Composable
fun MainAppBackground(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF0A93C8), // Center bright blue
                        Color(0xFF005788)  // Outer dark blue
                    ),
                    center = Offset.Unspecified, // default center
                    radius = 700f // tweak radius depending on screen
                )
            )
    )
}
@Composable
fun MainGradientBg(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_splash_bg),
            contentDescription = "splash_logo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}








