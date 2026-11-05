package com.lessonmaker.app.mainScreen.teacherFlow.myCourses.lessons

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
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.base.BaseListWithRefresh
import com.lessonmaker.app.common.MainButtonNew
import com.lessonmaker.app.common.widgets.EmptyScreen
import com.lessonmaker.app.mainScreen.DashboardViewModel


import com.lessonmaker.app.models.teacher.TeacherMyCourseLessonData
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.BackgorundColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.dataCellInfoBoxBg
import com.lessonmaker.app.theme.deleteBtnBg
import com.lessonmaker.app.theme.deleteBtnText
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.fieldHint
import com.lessonmaker.app.theme.fieldText
import com.lessonmaker.app.theme.greyE3
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.delete_ic_new
import lessonmaker.composeapp.generated.resources.edit_ic_new
import lessonmaker.composeapp.generated.resources.ic_empty_courses
import lessonmaker.composeapp.generated.resources.ic_empty_lessons
import lessonmaker.composeapp.generated.resources.search_ic_new
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.collections.emptyList

@Composable
fun TeacherLessonScreenFlow(
    courseId: String,
    courseCode: String,
    courseName: String,
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
){
    val teacherLessonsVM = koinViewModel<TeacherLessonsVM>()
    val teacherId = dashboardViewModel.sharedPreferenceManager.getTeacher()?.id?:0
    val lazyListState = rememberLazyListState()
    val lessons by teacherLessonsVM.lessons.collectAsState()

    LaunchedEffect(Unit){
        teacherLessonsVM.getAllLessons(teacherId = teacherId, courseId = courseId.toInt())
    }

    var query by remember { mutableStateOf("") }
////    val lessons = remember(courseId) {
////        mutableStateListOf<LessonItem>().apply { addAll(dummyLessons) }
////    }
//    val filtered = lessons.filter { it.title.contains(query, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgorundColor)
            .imePadding()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        LessonSearchBar(query = query, onQueryChange = { query = it })
        Spacer(modifier = Modifier.height(12.dp))

        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            BaseListWithRefresh(
                viewModel = teacherLessonsVM,
                emptyView = {
                    if (teacherLessonsVM.showEmptyScreen) {
                        EmptyScreen(
                            emptyImage = Res.drawable.ic_empty_lessons,
                            message = "No lessons yet",
                            subHeading = "Add your first lecture note to start sharing\n lessons with your students.",
                            showHomeButton = false,
                            imgModifier = Modifier.size(128.dp)
                        ) {
                            dashboardRoutes.invoke(DashboardSubScreen.LaunchHomeScreenFlow.route)
                        }
                    }
                },
                refreshCall = {
                    teacherLessonsVM.getAllLessons(teacherId = teacherId, courseId = courseId.toInt())
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)

                ) {

                    items(lessons?:emptyList()) { lesson ->
                        LessonCard(
                            lesson = lesson,
                            onDelete = { },
                            onEdit = {
                                dashboardRoutes.invoke(DashboardSubScreen.TeacherAddLessonScreenFLow.createRoute("",courseCode,courseName))
                            }
                        )
                    }



                }
            }




        }


        Column(modifier = Modifier.fillMaxWidth().background(White)) {
            Box(Modifier.fillMaxWidth().height(1.dp).background(greyE3))
            Box(modifier = Modifier.padding(16.dp)) {
                MainButtonNew(heading = "+  Add Lecture Note") {
                    dashboardRoutes.invoke(DashboardSubScreen.TeacherAddLessonScreenFLow.createRoute("",courseCode,courseName))

                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }

}
@Composable
private fun LessonSearchBar(query: String, onQueryChange: (String) -> Unit) {
    val shape = RoundedCornerShape(10.dp)
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        singleLine = true,
        textStyle = TextSize14().Regular().copy(color = fieldText),
        modifier = Modifier.fillMaxWidth(),
        decorationBox = { inner ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(48.dp)
                    .background(White, shape)
                    .border(1.dp, fieldBorder, shape)
                    .padding(horizontal = 14.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(painter = painterResource(Res.drawable.search_ic_new),
                        "",
                        modifier = Modifier
                            .size(16.dp))
                    Spacer(modifier = Modifier.width(5.dp))
                    if (query.isEmpty()) {
                        Text(
                            text = "Search lessons...",
                            color = fieldHint,
                            style = TextSize14().Regular()
                        )
                    }
                    inner()

                }


            }
        }
    )
}

@Composable
private fun LessonCard(
    lesson: TeacherMyCourseLessonData?,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape)
            .border(1.dp, fieldBorder, shape)
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = lesson?.lessonTitle?:"",
                color = bgColor,
                style = TextSize14().semiBold()
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = lesson?.createdAt?:"",
                color = loginTabUnselectedTxtClr,
                style = TextSize13().Regular()
            )
        }
        Spacer(Modifier.width(8.dp))
        LessonActionButton("Delete", deleteBtnBg, deleteBtnText, Res.drawable.delete_ic_new,onDelete)
        Spacer(Modifier.width(8.dp))
        LessonActionButton("Edit", dataCellInfoBoxBg, fieldCursor, Res.drawable.edit_ic_new, onEdit)
    }
}

@Composable
private fun LessonActionButton(
    text: String,
    bg: Color,
    textColor: Color,
    icon: DrawableResource,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                colorFilter = ColorFilter.tint(textColor)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = text,
                color = textColor,
                style = TextSize13().semiBold()
            )
        }
    }
}
data class LessonItem(
    val id: Int,
    val title: String,
    val date: String,
)


private val dummyLessons = listOf(
    LessonItem(1, "Introduction to Programming", "Oct 12, 2025"),
    LessonItem(2, "Control Structures", "Oct 18, 2025"),
    LessonItem(3, "Functions and Arrays", "Nov 02, 2025"),
)