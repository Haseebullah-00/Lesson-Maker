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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.MainButton
import com.lessonmaker.app.common.MainButtonNew
import com.lessonmaker.app.common.MainGradientBg
import com.lessonmaker.app.common.SimpleInputFieldWithError
import com.lessonmaker.app.common.SimpleInputFieldWithErrorNew
import com.lessonmaker.app.common.SimplePasswordInputField
import com.lessonmaker.app.common.SimplePasswordInputFieldNew
import com.lessonmaker.app.common.dialogue.MediaPickerInterface
import com.lessonmaker.app.common.dialogue.MediaSourcePicker
import com.lessonmaker.app.shared.media.excel.readSpreadsheet
import com.lessonmaker.app.shared.media.excel.rememberGalleryManagerForExcel
import com.lessonmaker.app.shared.media.pdf.rememberGalleryManagerForPdf
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.TextSize28
import com.lessonmaker.app.theme.TextSize32
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.blurClr
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.boldExtraBold
import com.lessonmaker.app.theme.buttonBackClr
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldText
import com.lessonmaker.app.theme.loginTabSelectionBg
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold
import com.picket.app.authFlow.screen.login.LoginVm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.apple
import lessonmaker.composeapp.generated.resources.book_ic
import lessonmaker.composeapp.generated.resources.check_box
import lessonmaker.composeapp.generated.resources.google
import lessonmaker.composeapp.generated.resources.ic_youtube
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel



@Composable
fun LoginScreenFlow(newRouts: (String) -> Unit)
{
    val loginVm= koinViewModel<LoginVm>()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)

    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally


        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp)
                    .imePadding()
                ,

                )
            {
                Spacer(modifier = Modifier.height(70.dp))
                topHeading()

                Spacer(modifier = Modifier.height(20.dp))
                Text(text ="Account Login",
                    color = bgColor,
                    style = TextSize28().boldExtraBold()
                )

                Spacer(modifier = Modifier.height(24.dp))
                RoleSwitcher(
                    selected = loginVm.accountType,
                    onSelect = {
                        loginVm.switchAccountType(it)

                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(text ="Institutional Email",
                    color = fieldText,
                    style = TextSize14().semiBold()
                )
                Spacer(modifier = Modifier.height(10.dp))
                SimpleInputFieldWithErrorNew(
                    text = loginVm.email,
                    hint =  if(loginVm.accountType== AuthAccountType.Teacher)"dr.abc@biit.edu.pk" else "2023-ARID-0000@biit.edu.pk",
                    errorMessage = null,
                    onValueChange = {
                        loginVm.email=it
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text(text ="Password",
                    color = fieldText,
                    style = TextSize14().semiBold()
                )
                Spacer(modifier = Modifier.height(10.dp))
                SimplePasswordInputFieldNew(
                    text = loginVm.password,
                    hint =  "Enter password",
                    errorMessage = null,
                    onValueChange = {
                        loginVm.password=it
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                MainButtonNew(heading = "Login"){

                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            DataCellFooter(onClick = {
                newRouts.invoke(Screen.DataCellLoginScreenFlow.route)

            })

            Spacer(modifier = Modifier.height(20.dp))


        }

    }


}

@Composable
private fun DataCellFooter(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Are you from the administration?",
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
                text = "Login via Data Cell Portal",
                color = bgColor,
                style = TextSize13().bold()
            )
        }
    }
}
@Composable
fun topHeading()
{
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(painter = painterResource(Res.drawable.book_ic),
            "Book",
            modifier = Modifier
                .size(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))

        Text(text ="Lesson Maker",
            color = bgColor,
            style = TextSize18().boldExtraBold()
        )

    }

}


@Composable
private fun RoleSwitcher(selected: AuthAccountType, onSelect: (AuthAccountType) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(loginTabSelectionBg , RoundedCornerShape(12.dp))
            .padding(4.dp)
    ) {
        RoleTab(
            text = "Teacher",
            selected = selected == AuthAccountType.Teacher,
            onClick = { onSelect(AuthAccountType.Teacher) },
            modifier = Modifier.weight(1f)
        )
        RoleTab(
            text = "Student",
            selected = selected == AuthAccountType.Student,
            onClick = { onSelect(AuthAccountType.Student) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun RoleTab(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier = modifier
            .fillMaxSize()
            .then(if (selected) Modifier.shadow(2.dp, shape) else Modifier)
            .background(if (selected) White else Color.Transparent, shape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) bgColor else loginTabUnselectedTxtClr,
            style = if (selected) TextSize14().bold() else TextSize14().semiBold()
        )
    }
}

