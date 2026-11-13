package com.lessonmaker.app.mainScreen.studentFlow.myCourses


import com.lessonmaker.app.mainScreen.DashboardViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.base.BaseListWithRefresh
import com.lessonmaker.app.common.AppSearchField
import com.lessonmaker.app.common.widgets.EmptyScreen

import com.lessonmaker.app.mainScreen.studentFlow.StudentCourse
import com.lessonmaker.app.mainScreen.studentFlow.StudentDummyStore

import com.lessonmaker.app.models.student.StudentCoursesData

import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.BackgorundColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.dataCellInfoBoxBg
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.ic_empty_courses
import lessonmaker.composeapp.generated.resources.ic_next
import lessonmaker.composeapp.generated.resources.lesson_ic
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StudentMyCoursesScreenFLow(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
){
    val studentMyCoursesVM = koinViewModel<StudentMyCoursesVM>()
    val studentId=dashboardViewModel.sharedPreferenceManager.getStudent()?.id?:0
    val lazyListState = rememberLazyListState()
    val courses by studentMyCoursesVM.courses.collectAsState()
    LaunchedEffect(Unit){
        studentMyCoursesVM.getAllCourses(studentId = studentId)

    }
    var query by remember { mutableStateOf("") }
//    val courses = remember(query) {
//        StudentDummyStore.courses.filter {
//            it.title.contains(query, true) || it.code.contains(query, true)
//        }
//    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgorundColor)

    )
    {
        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            BaseListWithRefresh(
                viewModel = studentMyCoursesVM,
                emptyView = {
                    if (studentMyCoursesVM.showEmptyScreen) {
                        EmptyScreen(
                            emptyImage = Res.drawable.ic_empty_courses,
                            message = "No courses enrolled yet",
                            subHeading = "Your enrolled courses will appear here once your instructor assigns them.",
                            showHomeButton = false,
                            imgModifier = Modifier.size(128.dp)
                        ) {
                            dashboardRoutes.invoke(DashboardSubScreen.LaunchHomeScreenFlow.route)
                        }
                    }
                },
                refreshCall = {
                    studentMyCoursesVM.getAllCourses(studentId = studentId)
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp),

                    contentPadding = PaddingValues(
                        horizontal = 16.dp, vertical = 16.dp
                    )
                ) {

                    items(courses) { course ->
                        StudentCourseCard(course) {
                            dashboardRoutes.invoke(
                                DashboardSubScreen.StudentLessonsScreenFlow.createRoute(
                                    "${course?.courseId?:0}", course.courseCode?:"", course?.courseName?:"",
                                    "${course?.teacherId?:0
                                    }"
                                )
                            )
                        }
                    }
                }
            }




        }

    }

//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(BackgorundColor),
//        contentPadding = PaddingValues(16.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        item {
//            AppSearchField(text = query, onValueChange = { query = it }, hint = "Search your courses...")
//        }
//        if (courses.isEmpty()) {
//            item {
//                Text(
//                    text = "No courses found",
//                    color = loginTabUnselectedTxtClr,
//                    style = TextSize14().Regular(),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.fillMaxWidth().padding(top = 32.dp)
//                )
//            }
//        }
//        items(courses, key = { it.id }) { course ->
//            StudentCourseCard(course) {
//                dashboardRoutes.invoke(
//                    DashboardSubScreen.StudentLessonsScreenFlow.createRoute(
//                        course.id, course.code, course.title
//                    )
//                )
//            }
//        }
//    }
}

@Composable
private fun StudentCourseCard(course: StudentCoursesData?, onClick: () -> Unit) {
    val shape = RoundedCornerShape(12.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(White, shape)
            .border(1.dp, fieldBorder, shape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .background(dataCellInfoBoxBg, RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Text(text = course?.courseCode?:"", color = fieldCursor, style = TextSize13().bold())
        }
        Spacer(Modifier.height(12.dp))
        Text(text = course?.courseName?:"", color = bgColor, style = TextSize16().bold())
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Instructor: ${course?.teacherName?:""}",
            color = loginTabUnselectedTxtClr,
            style = TextSize13().Regular()
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(Res.drawable.lesson_ic),
                    contentDescription = "",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = "${course?.lessonCount?:0} Lecture Notes",
                    color = loginTabUnselectedTxtClr,
                    style = TextSize13().Regular()
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "View Notes", color = fieldCursor, style = TextSize13().semiBold())
                Spacer(Modifier.width(4.dp))
                Image(
                    painter = painterResource(Res.drawable.ic_next),
                    contentDescription = "",
                    modifier = Modifier.size(10.dp),
                    colorFilter = ColorFilter.tint(fieldCursor)
                )
            }
        }
    }
}