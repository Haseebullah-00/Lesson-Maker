package com.lessonmaker.app.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.theme.ButtonBackgroundColor
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.PrimaryColor
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize17
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.secondaryColor
import com.lessonmaker.app.theme.semiBold


import org.jetbrains.compose.resources.painterResource


val buttonHeight = 50.dp
val buttonHeight45 = 45.dp

@Composable
fun DialogButton(
    modifier: Modifier = Modifier.fillMaxWidth().height(buttonHeight),
    heading: String = "Next",
    isNegative: Boolean = false,
    clicked: (Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = if (isNegative) secondaryColor else PrimaryColor,
                shape = RoundedCornerShape(size = 10.dp)
            )
            .clickable { clicked.invoke(true) }, contentAlignment = Alignment.Center
    ) {
        Text(
            text = heading,
            style = TextSize16().Medium(),
            color = if (isNegative) PrimaryColor else White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MainButton(
    cardBackClr: Color,
    borderClr: Color,
    text:String,
    height: Dp,
    borderWidth: Dp,
    onClick:()-> Unit
)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            )
            {
                onClick.invoke()

            }

            .border(
                width = borderWidth,
                color = borderClr,
                shape = RoundedCornerShape(10.dp)
            ),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = cardBackClr,
        elevation = 0.dp

    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            Text(
                text = text,
                color = White,
                style = TextSize18().bold()
            )

        }

    }


}

@Composable
fun MainButton2(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
    heading: String = "Next",
    clicked: (Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = PrimaryColor,
                shape = RoundedCornerShape(8.dp)
            )
            .background(ButtonBackgroundColor)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) {
                clicked(true)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = heading,
            color = PrimaryColor,
            style = TextSize17().Medium()
        )
    }
}



@Composable
fun MainWhiteButtonNegative(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
    heading: String = "Next",
    clicked: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { clicked() },
        shape = RoundedCornerShape(8.dp),
        backgroundColor = White, // ✅ set background inside Card
        elevation = 1.dp,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = heading,
                color = PrimaryColor,
                style = TextSize16().semiBold()
            )
        }
    }
}




