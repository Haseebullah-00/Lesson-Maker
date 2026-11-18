package com.lessonmaker.app.mainScreen.studentFlow.editAccess.lessons


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
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.base.BaseListWithRefresh
import com.lessonmaker.app.common.AppSearchField
import com.lessonmaker.app.common.extensionFunctions.toDisplayDate
import com.lessonmaker.app.common.widgets.EmptyScreen
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.studentFlow.StudentDummyStore
import com.lessonmaker.app.mainScreen.studentFlow.StudentEditStore

import com.lessonmaker.app.mainScreen.studentFlow.StudentLesson

import com.lessonmaker.app.models.student.lesson.StudentMyCourseLessonData
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.BackgorundColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.dataCellInfoBoxBg
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.infoBannerBg
import com.lessonmaker.app.theme.infoBannerBorder
import com.lessonmaker.app.theme.infoBannerText
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.edit_ic_new
import lessonmaker.composeapp.generated.resources.ic_empty_lessons
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StudentEditCourseLessonsScreenFlow(
    courseId: String,
    courseCode: String,
    courseName: String,
    teacherId: String,
    teacherName: String,
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {
    val studentEditCourseLessonVM = koinViewModel<StudentEditCourseLessonVM>()
    val studentId=dashboardViewModel.sharedPreferenceManager.getStudent()?.id?:0
    val lessons  by studentEditCourseLessonVM.lessons.collectAsState()
    val lazyListState = rememberLazyListState()
    LaunchedEffect(Unit){
        studentEditCourseLessonVM.getAllLessons(teacherId = teacherId.toInt(), studentId = studentId, courseId = courseId.toInt())
    }

    var query by remember { mutableStateOf("") }
//    val lessons = remember(courseId, query) {
//        StudentDummyStore.lessonsOf(courseId).filter { it.title.contains(query, true) }
//    }
//    val teacher = StudentEditStore.teacherOf(courseCode)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgorundColor)
            .imePadding()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            AppSearchField(
                text = query,
                onValueChange =
                    {
                        query = it
                        studentEditCourseLessonVM.getAllLessons(
                            teacherId = teacherId.toInt(),
                            courseId = courseId.toInt(),
                            studentId = studentId,
                            search = it
                        )

                    },
                hint = "Search lessons..."
            )

        }
        Spacer(modifier = Modifier.height(10.dp))
        StudentInfoBanner("Your edits are sent to ${teacherName} for approval.")

        Spacer(modifier = Modifier.height(12.dp))

        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            BaseListWithRefresh(
                viewModel = studentEditCourseLessonVM,
                emptyView = {
                    if (studentEditCourseLessonVM.showEmptyScreen) {
                        EmptyScreen(
                            emptyImage = Res.drawable.ic_empty_lessons,
                            message = "No lessons yet",
                            subHeading = "Your instructor's shared lecture notes will appear here when they become available.",
                            showHomeButton = false,
                            imgModifier = Modifier.size(128.dp)
                        ) {
                            dashboardRoutes.invoke(DashboardSubScreen.LaunchHomeScreenFlow.route)
                        }
                    }
                },
                refreshCall = {
                    studentEditCourseLessonVM.getAllLessons(
                        teacherId = teacherId.toInt(),
                        courseId = courseId.toInt(),
                        studentId = studentId
                    )
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)

                ) {
                    items(lessons) { lesson ->
                        EditLessonCard(lesson) {
                            dashboardRoutes.invoke(
                                DashboardSubScreen.StudentEditLessonScreenFlow.createRoute("${lesson?.lessonId?:0}", courseCode)
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
//        item { AppSearchField(text = query, onValueChange = { query = it }, hint = "Search lessons...") }
//        item { StudentInfoBanner("Your edits are sent to $teacher for approval.") }
//
//        if (lessons.isEmpty()) {
//            item {
//                Text(
//                    text = "No lessons found",
//                    color = loginTabUnselectedTxtClr,
//                    style = TextSize14().Regular(),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.fillMaxWidth().padding(top = 32.dp)
//                )
//            }
//        }
//        items(lessons, key = { it.id }) { lesson ->
//            EditLessonCard(lesson) {
//                dashboardRoutes.invoke(
//                    DashboardSubScreen.StudentEditLessonScreenFlow.createRoute(lesson.id, courseCode)
//                )
//            }
//        }
//    }
}

@Composable
private fun EditLessonCard(lesson: StudentMyCourseLessonData?, onEdit: () -> Unit) {
    val shape = RoundedCornerShape(12.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape)
            .border(1.dp, fieldBorder, shape)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = lesson?.lessonTitle?:"", color = bgColor, style = TextSize14().semiBold())
            Spacer(Modifier.height(4.dp))
            Text(text = lesson?.createdAt.toDisplayDate(), color = loginTabUnselectedTxtClr, style = TextSize12().Regular())
        }
        Row(
            modifier = Modifier
                .background(dataCellInfoBoxBg, RoundedCornerShape(6.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onEdit
                )
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.edit_ic_new),
                contentDescription = "Edit",
                modifier = Modifier.size(12.dp),
                colorFilter = ColorFilter.tint(fieldCursor)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = "Edit", color = fieldCursor, style = TextSize12().semiBold())
        }
    }
}



@Composable
fun StudentInfoBanner(text: String, modifier: Modifier = Modifier, square: Boolean = false) {
    val shape = RoundedCornerShape(if (square) 0.dp else 8.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(infoBannerBg, shape)
            .border(1.dp, infoBannerBorder, shape)
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = "ⓘ", color = infoBannerText, style = TextSize12().bold())
        Spacer(Modifier.width(6.dp))
        Text(text = text, color = infoBannerText, style = TextSize12().Regular())
    }
}