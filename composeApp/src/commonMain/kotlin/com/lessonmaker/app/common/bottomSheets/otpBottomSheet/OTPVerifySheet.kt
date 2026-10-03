package com.lessonmaker.app.common.bottomSheets.otpBottomSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.lessonmaker.app.common.MainButton
import com.lessonmaker.app.common.MainButton2
import com.lessonmaker.app.common.OtpTextField
import com.lessonmaker.app.common.bottomSheets.BottomSheetCommonInterface
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.theme.PrimaryColor
import com.lessonmaker.app.theme.TextColor1E
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize22
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.grey69
import com.lessonmaker.app.theme.semiBold
import com.lessonmaker.app.theme.spaceBetweenFields

import org.jetbrains.compose.resources.painterResource


import org.koin.compose.koinInject
import lessonmaker.composeapp.generated.resources.Res

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPVerifySheet(commonInterface: BottomSheetCommonInterface) {

    val sharedPreferenceManager: SharedPreferenceManager = koinInject()
    val keyboardController = LocalSoftwareKeyboardController.current
    var otp by mutableStateOf("")
    val annotatedText = buildAnnotatedString {

        withStyle(
            style = SpanStyle(
                color = grey69,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp
            )
        ) {
            append("To verify your mobile number, please enter the OTP that is sent to your mobile device ending in\n")
        }

        withStyle(
            style = SpanStyle(
                color = grey69,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp
            )
        ) {
            append("xxxxxx345")
        }
        pushStringAnnotation(tag = "EDIT", annotation = "edit_number")
        withStyle(
            style = SpanStyle(color = Color(0xFFD12121), fontWeight = FontWeight.SemiBold)
        ) {
         //   append("Change Mobile Number")
        }
        pop()
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    val annotatedTextCode = buildAnnotatedString {
        append("Code not received? ")
        pushStringAnnotation(tag = "RESEND", annotation = "resend_number")
        withStyle(
            style = SpanStyle(color = Color(0xFF093393), fontWeight = FontWeight.SemiBold)
        ) {
            append("Resend OTP")
        }
        pop()
    }

    ModalBottomSheet(
        onDismissRequest = {
            commonInterface.closeSheet()
        },
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp),
        tonalElevation = 16.dp,
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 0.dp), // removes bottom gap,
        dragHandle = {
            Box(modifier = Modifier.height(0.dp))
        },

        ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(horizontal = 5.dp),

            backgroundColor = White,
            shape = RoundedCornerShape(5.dp),
            elevation = 0.dp
        )
        {
            Column(
                modifier = Modifier
                    //.align(Alignment.BottomCenter)
                  //  .statusBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(White)
                    .padding(horizontal = spaceBetweenFields),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

//                    Image(
//                        painter = painterResource(resource = Res.drawable.ic_new_close), // your image
//                        contentDescription = "Close",
//                        modifier = Modifier
//                            .size(36.dp)
//                            .clickable {
//                                commonInterface.closeSheet()
//                            }
//                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(
                                color = TextColor1E,
                                fontSize = TextSize22().fontSize,
                                fontWeight = FontWeight.Medium
                            )) {
                                append("OTP ")
                            }
                            withStyle(style = SpanStyle(
                                color = PrimaryColor,
                                fontSize = TextSize22().fontSize,
                                fontWeight = FontWeight.SemiBold
                            )) {
                                append("Verification")
                            }
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                ClickableText(
                    text = annotatedText,
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        lineHeight = 25.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    // .padding(horizontal = 20.dp),
                    onClick = { offset ->
                        annotatedText.getStringAnnotations("EDIT", offset, offset)
                            .firstOrNull()?.let {
                                commonInterface.moveBackScreen()
                                //  newRoute.invoke(Screen.POPBackStack.route)
                            }
                    }
                )

                Spacer(modifier = Modifier.height(25.dp))

                // OTP field
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OtpTextField(
                        otpText = otp,
                        otpCount = 4,
                        onOtpTextChange = { value, _ ->
                            otp = value
                        }
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "Change mobile number",
                    color = PrimaryColor,
                    style = TextSize16().semiBold(),
                    modifier = Modifier.clickable {
                        commonInterface.moveBackScreen()
                    }
                )

                // Resend code
//                ClickableText(
//                    text = annotatedTextCode,
//                    style = TextStyle(
//                        fontSize = 14.sp,
//                        color = Color.Black,
//                        textAlign = TextAlign.Center,
//                        lineHeight = 20.sp
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 20.dp),
//                    onClick = { offset ->
//                        annotatedText.getStringAnnotations("RESEND", offset, offset)
//                            .firstOrNull()?.let {
//                                // commonInterface.moveBackScreen()
//                            }
//                    }
//                )
//
                Spacer(modifier = Modifier.height(30.dp))

                // Submit button
                MainButton2 (heading = "Submit") {
                    commonInterface.moveNextScreen()
                    //  newRoute.invoke(Screen.Register.route)
                }
                Spacer(modifier = Modifier.height(15.dp))
            }

        }

    }
}

