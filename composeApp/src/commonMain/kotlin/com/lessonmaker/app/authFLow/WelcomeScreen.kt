package com.lessonmaker.app.authFLow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.MainButton
import com.lessonmaker.app.common.MainGradientBg
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.navigationControler.Screen
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.PrimaryColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.TextSize30
import com.lessonmaker.app.theme.TextSize32
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.backClr
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.buttonBackClr
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.ic_youtube
import lessonmaker.composeapp.generated.resources.welcome_screen_logo

@Composable
fun WelcomeScreen(newRouts: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        MainGradientBg()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
        )
        {



            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.dp)
                    .weight(1f)
                    .padding(horizontal = 12.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Spacer(modifier = Modifier.height(95.dp))

                Image(
                    painter = painterResource(Res.drawable.welcome_screen_logo),
                    contentDescription = "Welcome Logo",
                    modifier = Modifier
                        .height(91.dp)
                )

                Spacer(modifier = Modifier.height(70.dp))

                Text(
                    text = "Welcome!",
                    color = White,
                    style = TextSize32().Medium()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Don’t have an account?",
                    color = Color.White,
                    style = TextSize18().Regular()
                )

                Spacer(modifier = Modifier.height(20.dp))
                MainButton(
                    buttonBackClr,
                    Color.Transparent,
                    "Join",
                    60.dp,
                    0.dp,
                    onClick = {
                        newRouts.invoke(Screen.AgeAskingScreen.route)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                MainButton(
                    Color.Transparent,
                    White,
                    "Sign In",
                    60.dp,
                    1.dp,
                    onClick = {
                        newRouts.invoke(Screen.LoginScreenFlow.route)
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween

                )
                {

                    Text(
                        text = "Continue as Guest",
                        color = White,
                        style = TextSize18().bold(),
                        modifier = Modifier
                            .clickable(interactionSource = MutableInteractionSource(),
                                indication = null
                            )
                            {
                                newRouts.invoke(Screen.DashboardScreen.route)

                            }

                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        )
                        {
                            Image(
                                painter = painterResource(Res.drawable.ic_youtube),
                                contentDescription = "youtube Icon",
                            )
                            Text(
                                text = "/@ettanehsan",
                                color = Color.White,
                                style = TextSize18().Medium()
                            )

                        }

                    }

                    Text(
                        text = "Privacy Policy",
                        color = White,
                        style = TextSize16().bold(),
                        modifier = Modifier
                            .clickable(interactionSource = MutableInteractionSource(),
                                indication = null)
                            {


                            }
                    )

                }
                Spacer(modifier = Modifier.height(30.dp))


            }
        }






    }
}



