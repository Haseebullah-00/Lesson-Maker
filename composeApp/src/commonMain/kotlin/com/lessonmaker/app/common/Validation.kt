package com.lessonmaker.app.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.theme.TextSize11
import com.lessonmaker.app.theme.formErrorClr
import com.lessonmaker.app.theme.semiBold

private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

/** Same regex for teacher, student and data cell emails (the value itself is never changed). */
fun String.isValidEmail(): Boolean = EMAIL_REGEX.matches(trim())

/** Your pink error text (apiError etc.). Draws nothing when message is null. */
@Composable
fun FormErrorText(message: String?) {
    if (message == null) return
    Box(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
            Text(
                text = message,
                color = formErrorClr,
                style = TextSize11().semiBold(),
                modifier = Modifier.padding(start = 1.dp, top = 2.dp).weight(1f)
            )
        }
    }
}