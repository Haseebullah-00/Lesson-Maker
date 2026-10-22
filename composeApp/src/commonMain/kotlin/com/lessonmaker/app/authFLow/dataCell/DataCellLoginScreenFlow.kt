package com.lessonmaker.app.authFLow.dataCell

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.authFLow.topHeading
import com.lessonmaker.app.common.MainButtonNew
import com.lessonmaker.app.common.SimpleInputFieldWithErrorNew
import com.lessonmaker.app.common.SimplePasswordInputFieldNew
import com.lessonmaker.app.navigationControler.Screen
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize28
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.boldExtraBold
import com.lessonmaker.app.theme.dataCellInfoBoxBg
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.fieldText
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold
import com.picket.app.authFlow.screen.login.DataCellLoginVm
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.caution_ic
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DataCellLoginScreenFlow(newRouts: (String) -> Unit){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val dataCellLoginVm = koinViewModel<DataCellLoginVm>()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp)
                    .imePadding()
            ) {
                Spacer(modifier = Modifier.height(70.dp))
                topHeading()

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Data Cell Access",
                    color = bgColor,
                    style = TextSize28().boldExtraBold()
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Administrative portal for academic uploads and records.",
                    color = loginTabUnselectedTxtClr,
                    style = TextSize14().Regular()
                )

                Spacer(modifier = Modifier.height(20.dp))
                DataCellInfoBanner(text = "Authorized Data Cell staff only.")

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Administrative Email",
                    color = fieldText,
                    style = TextSize14().semiBold()
                )
                Spacer(modifier = Modifier.height(10.dp))
                SimpleInputFieldWithErrorNew(
                    text = dataCellLoginVm.email,
                    hint = "datacell.admin@biit.edu.pk",
                    errorMessage = null,
                    onValueChange = {
                        dataCellLoginVm.email = it
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Password",
                    color = fieldText,
                    style = TextSize14().semiBold()
                )
                Spacer(modifier = Modifier.height(10.dp))
                SimplePasswordInputFieldNew(
                    text = dataCellLoginVm.password,
                    hint = "Enter password",
                    errorMessage = null,
                    onValueChange = {
                        dataCellLoginVm.password = it
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
                MainButtonNew(heading = "Authenticate Portal") {

                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            BackToLoginFooter(onClick = {
                newRouts.invoke(Screen.LoginScreenFlow.route)
            })
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun BackToLoginFooter(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Not from the administration?",
            color = loginTabUnselectedTxtClr,
            textAlign = TextAlign.Center,
            style = TextSize13().Regular()
        )
        val shape = RoundedCornerShape(8.dp)
        Box(
            modifier = Modifier
                .height(34.dp)
                .background(Color.White, shape)
                .border(1.dp, fieldBorder, shape)
                .clickable(interactionSource = MutableInteractionSource(),
                    indication = null){
                    onClick()
                }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Back to Account Login",
                color = bgColor,
                style = TextSize13().bold()
            )
        }
    }
}
@Composable
private fun DataCellInfoBanner(text: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(dataCellInfoBoxBg, RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

            Image(painter = painterResource(Res.drawable.caution_ic),
                "",
                modifier = Modifier
                    .size(14.dp))

        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            color = fieldCursor ,
            style = TextSize12().semiBold()
        )
    }
}