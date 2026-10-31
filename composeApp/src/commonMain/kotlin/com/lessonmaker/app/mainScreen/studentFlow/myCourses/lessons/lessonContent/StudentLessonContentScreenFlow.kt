package com.lessonmaker.app.mainScreen.studentFlow.myCourses.lessons.lessonContent


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.studentFlow.StudentDummyStore
import com.lessonmaker.app.theme.BackgorundColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.dataCellInfoBoxBg
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold

@Composable
fun StudentLessonContentScreenFlow(
    lessonId: String,
    courseCode: String,
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {
    val content = remember(lessonId) { StudentDummyStore.content(lessonId) }
    val uriHandler = LocalUriHandler.current
    val cardShape = RoundedCornerShape(12.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgorundColor)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Table of contents
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White, cardShape)
                .border(1.dp, fieldBorder, cardShape)
                .padding(12.dp)
        ) {
            Text(text = "TABLE OF CONTENTS", color = fieldCursor, style = TextSize12().bold())
            Spacer(Modifier.height(8.dp))
            content.toc.forEachIndexed { index, item ->
                Row(modifier = Modifier.padding(vertical = 2.dp)) {
                    Text(
                        text = (index + 1).toString().padStart(2, '0'),
                        color = loginTabUnselectedTxtClr,
                        style = TextSize12().Regular()
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(text = item, color = bgColor, style = TextSize12().semiBold())
                }
            }
        }

        // Lesson body
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White, cardShape)
                .border(1.dp, fieldBorder, cardShape)
                .padding(14.dp)
        ) {
            Text(text = content.heading, color = bgColor, style = TextSize16().bold())
            Spacer(Modifier.height(10.dp))
            Text(text = content.intro, color = bgColor, style = TextSize13().Regular())
            Spacer(Modifier.height(10.dp))

            content.bullets.forEach { bullet ->
                Row(modifier = Modifier.padding(vertical = 3.dp)) {
                    Text(text = "•", color = fieldCursor, style = TextSize13().bold())
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(bullet.label) }
                            append(" ")
                            append(bullet.text)
                        },
                        color = bgColor,
                        style = TextSize13().Regular()
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(BackgorundColor, RoundedCornerShape(8.dp))
                    .border(1.dp, fieldBorder, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = content.diagramCaption,
                    color = loginTabUnselectedTxtClr,
                    style = TextSize12().Regular()
                )
            }

            Spacer(Modifier.height(12.dp))
            Text(text = "RESOURCE REFERENCE", color = loginTabUnselectedTxtClr, style = TextSize12().bold())
            Spacer(Modifier.height(4.dp))
            Text(
                text = content.resourceUrl,
                color = fieldCursor,
                style = TextSize13().semiBold(),
                modifier = Modifier.clickable {
                    runCatching { uriHandler.openUri(content.resourceUrl) }
                }
            )
        }
        Spacer(Modifier.height(16.dp))
    }
}