package com.lessonmaker.app.mainScreen.studentFlow.myCourses.lessons


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.AppSearchField
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.studentFlow.StudentDummyStore
import com.lessonmaker.app.mainScreen.studentFlow.StudentLesson

import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.BackgorundColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.ic_next
import org.jetbrains.compose.resources.painterResource

@Composable
fun StudentLessonsScreenFlow(
    courseId: String,
    courseCode: String,
    courseName: String,
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val lessons = remember(courseId, query) {
        StudentDummyStore.lessonsOf(courseId).filter { it.title.contains(query, true) }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgorundColor),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            AppSearchField(text = query, onValueChange = { query = it }, hint = "Search lessons...")
        }
        if (lessons.isEmpty()) {
            item {
                Text(
                    text = "No lessons found",
                    color = loginTabUnselectedTxtClr,
                    style = TextSize14().Regular(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 32.dp)
                )
            }
        }
        items(lessons, key = { it.id }) { lesson ->
            StudentLessonCard(lesson) {
                dashboardRoutes.invoke(
                    DashboardSubScreen.StudentLessonContentScreenFlow.createRoute(lesson.id, courseCode)
                )
            }
        }
    }
}

@Composable
private fun StudentLessonCard(lesson: StudentLesson, onClick: () -> Unit) {
    val shape = RoundedCornerShape(12.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape)
            .border(1.dp, fieldBorder, shape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = lesson.title, color = bgColor, style = TextSize14().semiBold())
            Spacer(Modifier.height(4.dp))
            Text(text = lesson.date, color = loginTabUnselectedTxtClr, style = TextSize13().Regular())
        }
        Image(
            painter = painterResource(Res.drawable.ic_next),
            contentDescription = "",
            modifier = Modifier.size(12.dp),
            colorFilter = ColorFilter.tint(loginTabUnselectedTxtClr)
        )
    }
}