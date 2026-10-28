package com.lessonmaker.app.mainScreen.teacherFlow.sideBar.colleaguesCourses.addColleaguesCourses


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.theme.*
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.book_ic
import org.jetbrains.compose.resources.painterResource

data class RequestableCourse(
    val id: Int,
    val code: String,
    val department: String,
    val title: String,
    val instructor: String,
    val lectureNotes: Int,
    val requested: Boolean = false,
)

@Composable
fun AddColleaguesCoursesScreenFlow(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {

    val courses = remember {
        mutableStateListOf(
            RequestableCourse(1, "CS-101", "Computer Science", "Programming Fundamentals", "Dr. Munir", 14),
            RequestableCourse(2, "CS-102", "Computer Science", "Object Oriented Programming", "Mr. Ahmad", 8, requested = true),
        )
    }

    Column(Modifier.fillMaxSize().background(White)) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(courses, key = { it.id }) { c ->
                RequestCourseCard(c) {

                    val i = courses.indexOfFirst { it.id == c.id }
                    if (i >= 0) courses[i] = c.copy(requested = true)
                }
            }
        }
    }
}

@Composable
private fun RequestCourseCard(c: RequestableCourse, onRequest: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(White, RoundedCornerShape(12.dp))
            .border(BorderStroke(1.dp, fieldBorder), RoundedCornerShape(12.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                c.code, color = fieldCursor, style = TextSize12().semiBold(),
                modifier = Modifier
                    .background(dataCellInfoBoxBg, RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            )
            Text(c.department, color = grey94, style = TextSize12().Regular())
        }

        Text(c.title, color = bgColor, style = TextSize14().bold())
        Text("Instructor: ${c.instructor}", color = loginTabUnselectedTxtClr, style = TextSize12().Regular())

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(Res.drawable.book_ic), null,
                Modifier.size(14.dp),
                colorFilter = ColorFilter.tint(fieldIcon)
            )
            Spacer(Modifier.width(6.dp))
            Text("${c.lectureNotes} Lecture Notes", color = loginTabUnselectedTxtClr, style = TextSize12().Regular())
        }

        Spacer(Modifier.height(2.dp))

        // Action button: Request / Request Sent
        val sent = c.requested
        Row(
            Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(if (sent) greyE3 else fieldCursor, RoundedCornerShape(8.dp))
                .then(if (sent) Modifier else Modifier.clickable { onRequest() }),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (sent) Icons.Rounded.Check else Icons.AutoMirrored.Rounded.Send,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = if (sent) loginTabUnselectedTxtClr else Color.White
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = if (sent) "Request Sent" else "Request Course Notes",
                color = if (sent) loginTabUnselectedTxtClr else Color.White,
                style = TextSize12().semiBold()
            )
        }
    }
}