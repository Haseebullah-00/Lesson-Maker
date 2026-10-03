package com.lessonmaker.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import lessonmaker.composeapp.generated.resources.Res


import org.jetbrains.compose.resources.Font
import lessonmaker.composeapp.generated.resources.inter_bold
import lessonmaker.composeapp.generated.resources.inter_light
import lessonmaker.composeapp.generated.resources.inter_medium
import lessonmaker.composeapp.generated.resources.inter_regular
import lessonmaker.composeapp.generated.resources.inter_semiBold


@Composable
fun FustatFontFamily() = FontFamily(
    Font(Res.font.inter_light, weight = FontWeight.Light),
    Font(Res.font.inter_regular, weight = FontWeight.Normal),
    Font(Res.font.inter_medium, weight = FontWeight.Medium),
    Font(Res.font.inter_semiBold, weight = FontWeight.SemiBold),
    Font(Res.font.inter_bold, weight = FontWeight.Bold),
)

@Composable
fun PoppinsFontFamily() = FontFamily(
    Font(Res.font.inter_semiBold, weight = FontWeight.SemiBold),

)


@Composable
fun NavigationButtonText(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 12.sp, // Font size
        fontWeight = FontWeight.Medium, // Font weight
        lineHeight = 18.46.sp, // Line height
        letterSpacing = (-0.1).sp, // Letter spacing
        textAlign = TextAlign.Center, // Text alignment
        textDecoration = TextDecoration.None // No underline
    )
}

@Composable
fun poppinsTextSize18SemiBold(): TextStyle {
    val fontFamily = PoppinsFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
fun TextSize6(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 6.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize8(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 8.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize10(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 10.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize11(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 11.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize12(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 12.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize13(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 13.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize14(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 14.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize15(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 15.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize16(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize17(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 17.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize18(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 18.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize19(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 19.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize40(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 40.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize20(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 20.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize22(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 22.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize24(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 24.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize28(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 28.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize33(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 33.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize30(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 30.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}
@Composable
fun TextSize32(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 32.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextSize42(): TextStyle {
    val fontFamily = FustatFontFamily() // Get custom font family

    return TextStyle(
        fontFamily = fontFamily,
        fontSize = 42.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}

@Composable
fun TextStyle.bold(): TextStyle {
    return this.copy(fontWeight = FontWeight.Bold)
}

@Composable
fun TextStyle.semiBold(): TextStyle {
    return this.copy(fontWeight = FontWeight.SemiBold)
}

@Composable
fun TextStyle.boldExtraBold(): TextStyle {
    return this.copy(fontWeight = FontWeight.ExtraBold)
}

@Composable
fun TextStyle.boldLight(): TextStyle {
    return this.copy(fontWeight = FontWeight.Light)
}

@Composable
fun TextStyle.boldExtraLight(): TextStyle {
    return this.copy(fontWeight = FontWeight.ExtraLight)
}

@Composable
fun TextStyle.Medium(): TextStyle {
    return this.copy(fontWeight = FontWeight.Medium)
}
@Composable
fun TextStyle.Regular(): TextStyle {
    return this.copy(fontWeight = FontWeight.Normal)
}
@Composable
fun FlagText(): TextStyle {
    return TextStyle(
        fontSize = 20.sp, // Font size
        fontWeight = FontWeight.Normal, // Font weight
    )
}