package com.lessonmaker.app.mainScreen.teacherFlow.sideBar.archived

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.base.BaseListWithRefresh
import com.lessonmaker.app.common.widgets.EmptyScreen
import com.lessonmaker.app.mainScreen.DashboardViewModel


import com.lessonmaker.app.mainScreen.teacherFlow.myCourses.TeacherCourse
import com.lessonmaker.app.mainScreen.teacherFlow.myCourses.TeacherMyCoursesVM
import com.lessonmaker.app.models.teacher.archivedCourses.TeacherArchivedCourseData
import com.lessonmaker.app.models.teacher.myCourses.TeacherCourseData
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.BackgorundColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.dataCellInfoBoxBg
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.ic_empty_courses
import lessonmaker.composeapp.generated.resources.lesson_ic
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TeacherArchivedScreenFlow(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
)
{

    val teacherArchivedCoursesVM = koinViewModel<TeacherArchivedCoursesVM>()

    val teacherId = dashboardViewModel.sharedPreferenceManager.getTeacher()?.id?:0
    val lazyListState = rememberLazyListState()
    val dummyCourses = listOf(
        TeacherCourse("CS-101", "Computer Science", "Programming Fundamentals", 3),
        TeacherCourse("CS-102", "Computer Science", "Object Oriented Programming", 3),
        TeacherCourse("CS-201", "Computer Science", "Data Structures & Algorithms", 3),
    )

    LaunchedEffect(Unit){
        teacherArchivedCoursesVM.getAllCourses(teacherId = teacherId)
    }

    val courses by  teacherArchivedCoursesVM.courses.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgorundColor)

    )
    {
        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            BaseListWithRefresh(
                viewModel =  teacherArchivedCoursesVM,
                emptyView = {
                    if ( teacherArchivedCoursesVM.showEmptyScreen) {
                        EmptyScreen(
                            emptyImage = Res.drawable.ic_empty_courses,
                            message = "No courses archived yet",
                            subHeading = "Your courses will appear here once archived. In\n the meantime, explore your current’ courses",
                            showHomeButton = false,
                            imgModifier = Modifier.size(128.dp)
                        ) {
                            dashboardRoutes.invoke(DashboardSubScreen.LaunchHomeScreenFlow.route)
                        }
                    }
                },
                refreshCall = {
                    teacherArchivedCoursesVM.getAllCourses(teacherId)
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(
                        horizontal = 16.dp, vertical = 16.dp
                    )
                ) {

                    items(courses?:emptyList()) { course ->
                        CourseCard(course = course, onClick = {
                            dashboardRoutes.invoke(
                                DashboardSubScreen.TeacherArchivedLessonScreenFlow.createRoute(
                                    "${course?.courseId}",
                                    course?.courseCode ?: "",
                                    course?.courseName ?: ""
                                )
                            )
                        })
                        Spacer(modifier = Modifier.height(15.dp))

                    }
                }
            }

        }

    }
//    val dummyCourses = listOf(
//        TeacherCourse("CS-301", "Computer Science", "Operating Systems", 3),
//        TeacherCourse("CS-402", "Computer Science", "Parallel and Distributed\n" +
//                " Computing", 3),
//       // TeacherCourse("CS-201", "Computer Science", "Data Structures & Algorithms", 3),
//    )
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(BackgorundColor),
//    ){
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth(),
//
//            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            items(dummyCourses) { course ->
//                CourseCard(course = course, onClick = {
//                    dashboardRoutes.invoke(DashboardSubScreen.TeacherLessonScreenFlow.createRoute("",course.code,course.title))
//                })
//            }
//        }
//
//    }


}
@Composable
private fun CourseCard(course: TeacherArchivedCourseData?, onClick: () -> Unit) {
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // course code chip
            Box(
                modifier = Modifier
                    .background(dataCellInfoBoxBg, RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = course?.courseCode?:"",
                    color = fieldCursor,
                    style = TextSize13().bold()
                )
            }
//            Text(
//                text = course.category,
//                color = loginTabUnselectedTxtClr,
//                style = TextSize13().Regular()
//            )
        }

        Spacer(Modifier.height(12.dp))
        Text(
            text = course?.courseName?:"",
            color = bgColor,
            style = TextSize16().bold()
        )
        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically){
            Image(painter = painterResource(Res.drawable.lesson_ic),
                "",
                modifier = Modifier
                    .size(16.dp))
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "${course?.lessonCount} Lecture Notes",
                color = loginTabUnselectedTxtClr,
                style = TextSize13().Regular()
            )

        }

    }
}

//@Composable
//private fun CourseCard(course: TeacherCourse, onClick: () -> Unit) {
//    val shape = RoundedCornerShape(12.dp)
//    Column(
//        modifier = Modifier
//
//
//            .fillMaxWidth()
//            .clip(shape)
//            .background(White, shape)
//            .border(1.dp, fieldBorder, shape)
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null,
//                onClick = onClick
//            )
//            .padding(16.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            // course code chip
//            Box(
//                modifier = Modifier
//                    .background(dataCellInfoBoxBg, RoundedCornerShape(6.dp))
//                    .padding(horizontal = 8.dp, vertical = 3.dp)
//            ) {
//                Text(
//                    text = course.code,
//                    color = fieldCursor,
//                    style = TextSize13().bold()
//                )
//            }
//            Text(
//                text = course.category,
//                color = loginTabUnselectedTxtClr,
//                style = TextSize13().Regular()
//            )
//        }
//
//        Spacer(Modifier.height(12.dp))
//        Text(
//            text = course.title,
//            color = bgColor,
//            style = TextSize16().bold()
//        )
//        Spacer(Modifier.height(8.dp))
//        Row(modifier = Modifier,
//            verticalAlignment = Alignment.CenterVertically){
//            Image(painter = painterResource(Res.drawable.lesson_ic),
//                "",
//                modifier = Modifier
//                    .size(16.dp))
//            Spacer(modifier = Modifier.width(5.dp))
//            Text(
//                text = "${course.notesCount} Lecture Notes",
//                color = loginTabUnselectedTxtClr,
//                style = TextSize13().Regular()
//            )
//
//        }
//
//    }
//}