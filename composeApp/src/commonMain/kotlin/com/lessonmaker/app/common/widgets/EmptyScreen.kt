package com.lessonmaker.app.common.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.MainButton
import com.lessonmaker.app.common.MainButton2
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize24
import com.lessonmaker.app.theme.semiBold
import com.lessonmaker.app.theme.spaceBetweenFields
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.welcome_screen_logo


import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource



@Composable
fun EmptyScreen(
    imgModifier: Modifier = Modifier,
    emptyImage: DrawableResource = Res.drawable.welcome_screen_logo,
    message: String,
    subHeading: String = "",
    showHomeButton: Boolean = false,
    /*bgColorParam: Color = bgColor,*/
    bgColorParam: Color = Color.Transparent,
    onClick: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            //.clip(RoundedCornerShape(8.dp))
            .background(color = bgColorParam).padding(15.dp)
           // .border(1.dp, greyCE,RoundedCornerShape(8.dp))
    )
    {
        Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(resource = emptyImage),
                    contentDescription = "HomeBanner",
                    modifier = imgModifier

                )
                if (message.isNotEmpty()) {
                    //Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = message,
                        style = TextSize24().semiBold(),
                        color =  Color(0xFF111827),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = spaceBetweenFields),
                        textAlign = TextAlign.Center
                    )
                }
                if (subHeading.isNotEmpty()) {
                   Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = subHeading,
                        style = TextSize14().Regular(),
                        color = Color(0xFF374151),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = spaceBetweenFields),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        if (showHomeButton) {
            MainButton2(heading = "Home"){

            }
        }
    }
}


