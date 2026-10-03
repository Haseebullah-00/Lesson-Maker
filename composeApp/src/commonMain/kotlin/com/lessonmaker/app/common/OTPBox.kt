package com.lessonmaker.app.common


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.TextSize30
import com.lessonmaker.app.theme.border
import com.lessonmaker.app.theme.inputBackgroundColor


@Composable
fun OtpTextField(
    otpText: String,
    otpCount: Int = 4,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp) // Optional for left/right spacing
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
            onValueChange = {
                if (it.text.length <= otpCount) {
                    onOtpTextChange(it.text, it.text.length == otpCount)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done
            ),
            decorationBox = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(otpCount) { index ->
                        CharView(
                            index = index,
                            text = otpText,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                }
            }
        )
    }
}



@Composable
private fun CharView(
    index: Int,
    text: String,
    modifier: Modifier
) {
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = modifier
            .background(color = inputBackgroundColor, shape = RoundedCornerShape(8.dp))
            .border(border = BorderStroke(width = 1.dp,color = border), shape = RoundedCornerShape(8.dp))
            .padding(2.dp),
        text = char,
        style = TextSize30(),
        color = Black,
        textAlign = TextAlign.Center
    )
}