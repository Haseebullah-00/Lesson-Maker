package com.lessonmaker.app.mainScreen.account.editProfile

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lessonmaker.app.common.MainButton
import com.lessonmaker.app.common.SimpleInputFieldWithError
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.navigationControler.Screen
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.buttonBackClr

@Composable
fun EditProfileScreen(dashboardViewModel: DashboardViewModel,
                     dashboardRoutes: (String) -> Unit
)
{
    var fName by remember { mutableStateOf("") }
    var nickName by remember { mutableStateOf("") }
    var parentEmail by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
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
                Spacer(modifier = Modifier.height(20.dp))
                SimpleInputFieldWithError(
                    fName, onValueChange = { fName = it },
                    hint = "Full Name",

                    )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Your username will only be used to log in. You'll create a display name later which is what will be seen by other users!",
                    color = White,
                    style = TextSize12().Regular().copy(
                        lineHeight = 16.sp
                    ),

                    )
                Spacer(modifier = Modifier.height(12.dp))
                SimpleInputFieldWithError(
                    nickName, onValueChange = { nickName = it },
                    hint = "Nickname",

                    )
                Spacer(modifier = Modifier.height(12.dp))
                SimpleInputFieldWithError(
                    parentEmail, onValueChange = { parentEmail = it },
                    hint = "Parent Email address",

                    )
                Spacer(modifier = Modifier.height(13.dp))
                Text(
                    text = "We'll need your parents to verify your Lesson Maker profile so you can start having fun!",
                    color = White,
                    style = TextSize12().Regular().copy(
                        lineHeight = 16.sp
                    )

                )


            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )
            {
                MainButton(buttonBackClr,
                    Color.Transparent,
                    "Save Details",
                    60.dp,
                    0.dp,
                    onClick = {

                    }
                )

            }



            Spacer(modifier = Modifier.height(10.dp))
        }

    }

}