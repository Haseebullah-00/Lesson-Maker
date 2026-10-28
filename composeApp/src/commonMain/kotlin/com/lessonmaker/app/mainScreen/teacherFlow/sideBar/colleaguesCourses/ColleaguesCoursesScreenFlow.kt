package com.lessonmaker.app.mainScreen.teacherFlow.sideBar.colleaguesCourses

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.MainButtonNew
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.dataCellInfoBoxBg
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.fieldIcon
import com.lessonmaker.app.theme.grey94
import com.lessonmaker.app.theme.greyE3
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.book_ic
import org.jetbrains.compose.resources.painterResource

@Composable
fun ColleaguesCoursesScreenFlow(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
){
   val list = listOf(
        ColleagueCourse("CS-101", "Computer Science", "Programming Fundamentals", "Mr. Zahid", 14),
        ColleagueCourse("CS-102", "Computer Science", "Object Oriented Programming", "Mr. Umer", 8),
    )
    Column(
        Modifier
            .fillMaxSize()
            .background(White)

    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(list) { c -> ColleagueCourseCard(c) }
        }
        Column(modifier = Modifier.fillMaxWidth().background(White)) {
            Box(Modifier.fillMaxWidth().height(1.dp).background(greyE3))
            Box(modifier = Modifier.padding(16.dp)) {
                MainButtonNew(heading = "+  Add Colleagues’ Coursest") {
                    dashboardRoutes.invoke(DashboardSubScreen.AddColleaguesCoursesScreenFlow.route)


                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))


    }

}
data class ColleagueCourse(
    val code: String,
    val department: String,
    val title: String,
    val instructor: String,
    val lectureNotes: Int,
)

@Composable
private fun ColleagueCourseCard(c: ColleagueCourse) {
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
    }
}