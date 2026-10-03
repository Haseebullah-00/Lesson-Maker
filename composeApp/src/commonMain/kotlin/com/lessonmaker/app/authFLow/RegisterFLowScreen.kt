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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lessonmaker.app.common.MainButton
import com.lessonmaker.app.common.MainGradientBg
import com.lessonmaker.app.common.SimpleDropDownField
import com.lessonmaker.app.common.SimpleInputField
import com.lessonmaker.app.common.SimpleInputFieldWithError
import com.lessonmaker.app.common.SimplePasswordInputField
import com.lessonmaker.app.common.countryPicker
import com.lessonmaker.app.common.dropDownField
import com.lessonmaker.app.navigationControler.Screen
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.PrimaryColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.TextSize32
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.backClr
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.buttonBackClr
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.arrow_down
import lessonmaker.composeapp.generated.resources.check_box
import lessonmaker.composeapp.generated.resources.check_mark

@Composable
fun RegisterFLowScreen(newRouts: (String) -> Unit)
{
    var fName by remember { mutableStateOf("") }
    var nickName by remember { mutableStateOf("") }
    var parentEmail by remember { mutableStateOf("") }
    var school by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var conPassword by remember { mutableStateOf("") }
    var subscribe by remember { mutableStateOf(false) }
    var privacy by remember { mutableStateOf(false) }
    var country by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            //.background(PrimaryColor),


        )
    {
        MainGradientBg()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        )
        {
            Spacer(modifier = Modifier.height(45.dp))

            Text(
                text = "Register Now",
                color = White,
                style = TextSize32().Medium()
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Sign in to continue",
                color = White,
                style = TextSize18().Regular()
            )
            Spacer(modifier = Modifier.height(20.dp))
            SimpleInputFieldWithError(
                fName, onValueChange = { fName = it },
                hint = "Full Name",

                )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Your username will only be used to log in. You'll create a display name later which is what will be seen by other users!",
                color = White,
                style = TextSize13().Regular().copy(
                   lineHeight = 16.sp
                ),

            )
            Spacer(modifier = Modifier.height(12.dp))
            SimpleInputFieldWithError(
                nickName, onValueChange = { nickName = it },
                hint = "Nickname",

                )
            Spacer(modifier = Modifier.height(10.dp))
            SimpleInputFieldWithError(
                parentEmail, onValueChange = { parentEmail = it },
                hint = "Parent Email address",

                )
            Spacer(modifier = Modifier.height(13.dp))
            Text(
                text = "We'll need your parents to verify your Lesson Maker profile so you can start having fun!",
                color = White,
                style = TextSize13().Regular().copy(
                    lineHeight = 16.sp
                )

            )
            Spacer(modifier = Modifier.height(13.dp))
            countryPicker(country,"United Arab Emirates", onValueChange = {country=it}, onCoutryChange = {}, onClick = {})
            Spacer(modifier = Modifier.height(10.dp))
            dropDownField(school,"School", onValueChange = {school=it}, onClick = {})
            Spacer(modifier = Modifier.height(10.dp))
            SimplePasswordInputField(text = password, hint = "Password", enabled = true, onValueChange = {password=it})
            Spacer(modifier = Modifier.height(10.dp))
            SimplePasswordInputField(text = conPassword, hint = "Confirm Password", enabled = true, onValueChange = {conPassword=it})
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            )
            {
                CheckBox(subscribe, onToggle = {subscribe=it})
                Text(
                    text = "Suscbribe to Ettan Ehsan emailing list",
                    color = Color.White,
                    style = TextSize14().Regular()
                )



            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            )
            {
                CheckBox(privacy, onToggle = {privacy=it})
                Text(
                    text = buildAnnotatedString {
                        append("I accept the ")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("terms of service")
                        }

                        append(" and ")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("privacy policy")
                        }

                    },
                    color = Color.White,
                    style = TextSize14().Regular()
                )



            }
            Spacer(modifier = Modifier.height(20.dp))
            MainButton(buttonBackClr,
                Color.Transparent,
                "Register",
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
            )
            {
                Row(
                    modifier = Modifier.fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                )
                    {
                        newRouts.invoke(Screen.LoginScreenFlow.route)

                    },

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Already have an account?",
                        color = Color.White,
                        style = TextSize16().Regular()
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "Sign In.",
                        color = Color.White,
                        style = TextSize16().bold()
                    )
                }
            }
            Spacer(modifier = Modifier.height(65.dp))








        }

    }

}
@Composable
fun CheckBox(
    isCheck: Boolean,
    onToggle: (Boolean) -> Unit
) {
    var isChecked by remember { mutableStateOf(isCheck) }

    Card(
        modifier = Modifier
            .size(24.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                isChecked = !isChecked
                onToggle(isChecked)
            }
            .border(
                width = 1.5.dp,
                color = buttonBackClr,
                shape = RoundedCornerShape(0.dp)
            ),
        shape = RoundedCornerShape(0.dp),
        backgroundColor = White,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isChecked) {
                Image(
                    painter = painterResource(resource = Res.drawable.check_mark),
                    contentDescription = "check box"
                )
            }
        }
    }
}
