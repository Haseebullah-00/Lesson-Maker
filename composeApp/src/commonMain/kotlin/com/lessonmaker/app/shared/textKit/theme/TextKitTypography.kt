package com.lessonmaker.app.shared.textkit.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.inter_bold
import lessonmaker.composeapp.generated.resources.inter_light
import lessonmaker.composeapp.generated.resources.inter_medium
import lessonmaker.composeapp.generated.resources.inter_regular
import lessonmaker.composeapp.generated.resources.inter_semiBold


/**
 * The typography used by TextKit UI and rendered content.
 *
 * Obtain the active instance with `TextKitTheme.typography` inside a [TextKitTheme] scope.
 *
 * @property fontFamily The font family applied to editor text and TextKit controls.
 */
@Immutable
data class TextKitTypography(
    val fontFamily: FontFamily
) {

    companion object {
        /**
         * The default typography. [fontFamily] defaults to the bundled Noto Sans (regular + italic);
         * pass a custom [FontFamily] to override it. Composable because loading font resources is.
         */
        @Composable
        fun default(
            fontFamily: FontFamily = notoSansFamily()
        ): TextKitTypography = TextKitTypography(fontFamily = fontFamily)

        @Composable
        private fun notoSansFamily(): FontFamily = FontFamily(
            Font(Res.font.inter_light, weight = FontWeight.Light),
            Font(Res.font.inter_regular, weight = FontWeight.Normal),
            Font(Res.font.inter_medium, weight = FontWeight.Medium),
            Font(Res.font.inter_semiBold, weight = FontWeight.SemiBold),
            Font(Res.font.inter_bold, weight = FontWeight.Bold),
        )
    }
}