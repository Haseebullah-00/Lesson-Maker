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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.Text
import com.lessonmaker.app.navigationControler.Screen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.Image
import com.lessonmaker.app.common.MainButton
import com.lessonmaker.app.common.MainGradientBg
import com.lessonmaker.app.common.SimpleInputFieldWithError
import com.lessonmaker.app.common.SimplePasswordInputField
import com.lessonmaker.app.common.roundedShapeForInput
import com.lessonmaker.app.mainScreen.DashboardScreen
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.PrimaryColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.TextSize32
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.backClr
import com.lessonmaker.app.theme.blurClr
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.buttonBackClr
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.apple
import lessonmaker.composeapp.generated.resources.check_box
import lessonmaker.composeapp.generated.resources.google
import lessonmaker.composeapp.generated.resources.ic_youtube

@Composable
fun LoginScreenFlow(newRouts: (String) -> Unit)
{
    ///
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()

        )
    {
        /////

        ///

        ////
        MainGradientBg()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.height(70.dp))

            Text(
                text = "Welcome Back!",
                color = White,
                style = TextSize32().Medium()
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Sign in to continue",
                color = White,
                style = TextSize18().Regular()
            )
            Spacer(modifier = Modifier.height(30.dp))
            SimpleInputFieldWithError(
                email,

                onValueChange = { email = it },
                hint = "Email Address",

                )
            Spacer(modifier = Modifier.height(10.dp))
            SimplePasswordInputField(text = password,
                hint = "Password",
                enabled = true,
                onValueChange = {password=it}
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                )
                {
                    Image(painter = painterResource(Res.drawable.check_box),
                        contentDescription = "CHeck Box",
                        modifier = Modifier
                            .size(16.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            )
                            {
                            }
                    )
                    Text(
                        text = "Remember me",
                        color = White,
                        style = TextSize16().Regular()
                    )

                }
                Text(
                    text = "Forgot password?",
                    color = White,
                    style = TextSize16().Medium(),
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        )
                        {


                        }
                )




            }
            Spacer(modifier = Modifier.height(30.dp))
            MainButton(buttonBackClr,
                Color.Transparent,
                "Sign In",
                60.dp,
                0.dp,
                onClick = {
                    newRouts.invoke(Screen.DashboardScreen.route)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        )
                        {
                            newRouts.invoke(Screen.RegisterFLowScreen.route)

                        },

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Don’t have an account?",
                        color = Color.White,
                        style = TextSize16().Regular()
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "Sign up.",
                        color = Color.White,
                        style = TextSize16().bold()
                    )
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            )
            {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height((0.55).dp)
                        .background(Color.White)
                )
                Text(
                    text = "Or use your social login",
                    color = Color.White,
                    style = TextSize14().Regular()
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height((0.55).dp)
                        .background(Color.White)
                )

            }
            Spacer(modifier = Modifier.height(25.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                )
                {
                    socialLoginCard(Res.drawable.apple, onClick = {})
                    Spacer(modifier = Modifier.width(20.dp))
                    socialLoginCard(Res.drawable.google, onClick = {})

                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            MainButton(Color.Transparent,
                Color.Transparent,
                "Continue As Guest",
                60.dp,
                0.dp,
                onClick = {
                    newRouts.invoke(Screen.DashboardScreen.route)
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
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
                    Image(painter = painterResource(Res.drawable.ic_youtube),
                        contentDescription = "youtube Icon",
                    )
                    Text(
                        text = "/@ettanehsan",
                        color = Color.White,
                        style = TextSize18().Medium()
                    )


                }

            }







        }
    }
}
@Composable
fun socialLoginCard(
    icon: DrawableResource,
    onClick:()-> Unit
)
{
    Card(
        modifier = Modifier
            .size(60.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
             onClick.invoke()
            }
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                clip = false,
                ambientColor = blurClr.copy(alpha = 0.15f),
                spotColor = blurClr.copy(alpha = 0.15f)
            )
            .border(
                width = 0.dp,
                color = White,
                shape = RoundedCornerShape(10.dp)
            ),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = White,
    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            Image(painter = painterResource(icon),
                contentDescription = "Icon")
        }



    }


}


