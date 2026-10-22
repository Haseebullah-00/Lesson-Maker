package com.lessonmaker.app.common.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextHintColor
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.inputBackgroundColor


@Composable
fun SearchBoxWidgets(
    modifier: Modifier = Modifier,
    placeholder: String = "Search here",
    onSearchClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier.clickable { onSearchClicked() }
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier.width(10.dp)
        )
        Box(
            modifier = Modifier
                .size(35.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(inputBackgroundColor)
                .border(1.dp, Color.White, RoundedCornerShape(12.dp))
                .clickable { onSearchClicked() },
            contentAlignment = Alignment.Center
        ) {
//            Icon(
//                painter = painterResource(Res.drawable.ic_search_home),
//                contentDescription = "Search Icon",
//                tint = PrimaryColor,
//                modifier = Modifier.size(20.dp)
//            )
        }

        Text(
            text = placeholder,
            color = TextHintColor,
            style = TextSize14().Regular(),
            modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
        )

        Spacer(
            modifier = Modifier.width(10.dp)
        )
    }
}