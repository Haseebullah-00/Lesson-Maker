package com.lessonmaker.app.common.extensionFunctions

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.roundedShapeForInput
import com.lessonmaker.app.theme.FustatFontFamily
import com.lessonmaker.app.theme.SignupTxtColor
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize15
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.inputBorderColorActive
import com.lessonmaker.app.theme.inputBorderColorNotActive
import com.lessonmaker.app.theme.mainGradient


import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import kotlin.math.roundToInt


@Composable
fun TopButtonWithBackground(
    modifier: Modifier = Modifier.size(40.dp),
    drawableResource: DrawableResource,
    callback: () -> Unit
) {
    Box(
        modifier = modifier.clip(shape = CircleShape)
            .clickable { callback.invoke() }, contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(resource = drawableResource),
            contentDescription = "Event Back image",
        )
    }
}




@Composable
fun TopButtonWithBackground3(
    modifier: Modifier = Modifier.size(40.dp),
    drawableResource: DrawableResource,
    callback: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape = roundedShapeForInput)
            .clickable { callback.invoke() },
        contentAlignment = Alignment.Center // Ensure icon is centered inside the button
    ) {
        Image(
            painter = painterResource(resource = drawableResource),
            contentDescription = "Event Back image"
        )
    }
}





@Composable
fun TermsText(callback: (String) -> Unit) {
    // Clickable "Terms of Use" and "Privacy Policy"
    val annotatedText = buildAnnotatedString {
        append("By continuing you agree to our ")

        // Add "Terms of Use" as a clickable link
        pushStringAnnotation(
            tag = "TERMS_OF_USE",
            annotation = ""
        )
        var fontFamily= FustatFontFamily()
        withStyle(
            style = SpanStyle(
                color = SignupTxtColor,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,

                )
        ) {
            append("Terms of Service.")
        }
        pop()
        appendLine()
        append("Comfort Life services are subject to our ")

        // Add "Privacy Policy" as a clickable link
        pushStringAnnotation(
            tag = "PRIVACY_POLICY",
            annotation = ""
        )
        withStyle(
            style = SpanStyle(
                fontFamily = fontFamily,
                color = SignupTxtColor,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Privacy Policy ")
        }
        pop()


    }
    Box(contentAlignment = Alignment.Center) {
        ClickableText(
            text = annotatedText,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            style = TextSize15().copy(color = SignupTxtColor.copy(alpha = 0.3f)),
            onClick = { offset ->
                annotatedText.getStringAnnotations(
                    tag = "TERMS_OF_USE",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    //callback(CMS_Terms_Conditions)
                }

                annotatedText.getStringAnnotations(
                    tag = "PRIVACY_POLICY",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                  //  callback(CMS_Privacy_Policy)
                }
            }
        )
    }
}



@Composable
fun OTPPhoneNumberText(phone: String = "+971 23 456 789") {
    // Clickable "Terms of Use" and "Privacy Policy"
    val annotatedText = buildAnnotatedString {
        append("Enter the pin you have received via SMS on ")

        // Add "Terms of Use" as a clickable link
        pushStringAnnotation(
            tag = "PhoneNumber",
            annotation = ""
        )
        withStyle(
            style = SpanStyle(
                color = White,
                fontFamily = FustatFontFamily(),
                fontSize = TextSize18().fontSize,
                fontWeight = FontWeight.Bold
            ).copy()
        ) {
            append(phone)
        }
        pop()
    }
    Text(
        text = annotatedText,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 30.dp)
            .wrapContentWidth(Alignment.CenterHorizontally),
        style = TextSize18().copy(color = White),
    )
}

@Composable
fun FitMidHeading() {
    // Clickable "Terms of Use" and "Privacy Policy"
    val annotatedText = buildAnnotatedString {
        append("Do you workout by your own?  Keep collecting  ")
        // Add "Terms of Use" as a clickable link
        pushStringAnnotation(
            tag = "hadipoints",
            annotation = ""
        )
        withStyle(
            style = SpanStyle(
                color = White,
                fontFamily = FustatFontFamily(),
                fontSize = TextSize18().fontSize,
                fontWeight = FontWeight.Bold
            ).copy()
        ) {
            append("hadipoints")
        }
        pop()
        append(" to get the  best premium  fitness app for free.")


    }
    Text(
        text = annotatedText,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 30.dp)
            .wrapContentWidth(Alignment.CenterHorizontally),
        style = TextSize18().copy(color = White),
    )
}


@Composable
fun GradientCircularProgressBar(
    progress: Float, // Target progress between 0.0 and 1.0
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 4.dp
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(progress) {
        animatedProgress.animateTo(
            targetValue = progress,
            animationSpec = tween(durationMillis = 1500, easing = LinearOutSlowInEasing)
        )
    }


    Canvas(modifier = modifier) {
        val diameter = size.minDimension
        val stroke = strokeWidth.toPx()
        val startAngle = 90f
        val sweepAngle = animatedProgress.value * 360f

        drawArc(
            color = White.copy(alpha = 0.31f), // Inactive path color
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = stroke, cap = StrokeCap.Round),
            size = Size(diameter - stroke, diameter - stroke),
            topLeft = Offset(
                (size.width - diameter) / 2 + stroke / 2,
                (size.height - diameter) / 2 + stroke / 2
            )
        )
        drawArc(
            brush = mainGradient,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = stroke, cap = StrokeCap.Round),
            size = Size(diameter - stroke, diameter - stroke),
            topLeft = Offset(
                (size.width - diameter) / 2 + stroke / 2,
                (size.height - diameter) / 2 + stroke / 2
            )
        )
    }
}

@Composable
fun GradientHorizontalProgressBar(
    progress: Float, // Progress value between 0.0 and 1.0
    modifier: Modifier = Modifier,
    height: Dp = 8.dp,
    roundedBack: Boolean = false
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(progress) {
        animatedProgress.animateTo(
            targetValue = progress,
            animationSpec = tween(durationMillis = 1500, easing = LinearOutSlowInEasing)
        )
    }

    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFA8D7CD), Color(0xFF1065C2)),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, 0f) // Horizontal gradient
    )

    Box(
        modifier = modifier
            .height(height)
            .background(
                Color(0xFF292929),
                shape = if (roundedBack) RoundedCornerShape(height * 20) else 1f.getHorizontalProgressRoundEnd()
            ) // Background with rounded corners
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = animatedProgress.value)
                .background(
                    brush = gradientBrush,
                    shape = if (roundedBack) RoundedCornerShape(height * 20) else progress.getHorizontalProgressRoundEnd()
                ) // Gradient progress bar
        )
        Text(
            modifier = Modifier.align(alignment = Alignment.CenterEnd).padding(end = 10.dp),
            text = "${(animatedProgress.value * 100).roundToInt()} %",
            style = TextSize13(),
            color = White
        )
    }
}

fun Float.getHorizontalProgressRoundEnd(): RoundedCornerShape {
    if (this >= 1) {
        return RoundedCornerShape(percent = 0)
    }
    return RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50)
}



@Composable
fun CustomCheckBox(
    isChecked: Boolean,
    shape: Shape = RoundedCornerShape(5.dp),
    onCheckedChange: (Boolean) -> Unit
) {

    Box(
        modifier = Modifier
            .size(24.dp)
            .clickable { onCheckedChange(!isChecked) }
            .clip(shape = shape)
            .border(
                1.dp,
                if (isChecked) inputBorderColorActive else inputBorderColorNotActive,
                shape = shape
            ) // Add border
            .background(White,
                shape =shape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isChecked) {
//            Image(
//                modifier = Modifier.size(16.dp),
//                colorFilter = ColorFilter.tint(color = inputBorderColorActive),
//                painter = painterResource(resource = Res.drawable.ic_tick_selected),
//                contentDescription = "Checked"
//            )
        }
    }
}

