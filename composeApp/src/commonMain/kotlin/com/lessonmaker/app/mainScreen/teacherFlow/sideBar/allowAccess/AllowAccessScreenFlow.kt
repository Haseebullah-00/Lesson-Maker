package com.lessonmaker.app.mainScreen.teacherFlow.sideBar.allowAccess


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.AppDropdownField
import com.lessonmaker.app.common.AppSearchField
import com.lessonmaker.app.common.MainButtonNew
import com.lessonmaker.app.common.bottomSheets.BottomSheetCommonInterface
import com.lessonmaker.app.common.bottomSheets.courseSelectionBottomSheet.CourseSelectionBottomSheet
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.teacherFlow.myCourses.TeacherMyCoursesVM
import com.lessonmaker.app.theme.*
import org.koin.compose.viewmodel.koinViewModel

data class AccessStudent(val id: Int, val name: String, val arid: String)

@Composable
fun AllowAccessScreenFlow(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {
    val teacherMyCoursesVM= koinViewModel<TeacherMyCoursesVM>()
    val allowAccessVM = koinViewModel<AllowAccessVM>()
    val teacherId = dashboardViewModel.sharedPreferenceManager.getTeacher()?.id?:0

    val students by allowAccessVM.students.collectAsState()

    LaunchedEffect(Unit){
        teacherMyCoursesVM.getAllCourses(teacherId = teacherId)
        allowAccessVM.getAllStudents(teacherId = teacherId)
    }

    val courses by teacherMyCoursesVM.courses.collectAsState()
    var showSheet by remember { mutableStateOf(false) }

    var courseError by remember { mutableStateOf<String?>(null) }

    var query by remember { mutableStateOf("") }
    val density = LocalDensity.current

    Column(Modifier.fillMaxSize().background(White)) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Box(Modifier) {
                AppDropdownField(
                    text = allowAccessVM.selectedCourse?.courseName?:"",
                    hint = "Select the course you want to give access",
                    errorMessage = courseError,
                    onClick = {
                       showSheet = true
                    },
                    onClear = {
                        allowAccessVM.selectedCourse =null
                        courseError = null
                    }
                )
            }

            AppSearchField(
                text = query,
                hint = "Search by name or ARID",
                onValueChange = { query = it }
            )

            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(students) { s ->
                    val selected = allowAccessVM.selectedStudent?.studentId == s?.studentId
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(if (selected) fieldCursor else White, RoundedCornerShape(8.dp))
                            .border(
                                BorderStroke(1.dp, if (selected) fieldCursor else fieldBorder),
                                RoundedCornerShape(8.dp)
                            )
                            .clickable(interactionSource = MutableInteractionSource(),
                                indication = null) {allowAccessVM.selectedStudent = s }
                            .padding(12.dp)
                    ) {
                        Text(s.studentName?:"", color = if (selected) Color.White else bgColor, style = TextSize13().bold())
                        Text(
                            s.aridNo?:"",
                            color = if (selected) Color.White.copy(alpha = 0.85f) else loginTabUnselectedTxtClr,
                            style = TextSize12().Regular()
                        )
                    }
                }
            }
        }

        Column(Modifier.fillMaxWidth().background(White)) {
            Box(Modifier.fillMaxWidth().height(1.dp).background(greyE3))
            Box(Modifier.padding(16.dp)) {
                MainButtonNew(heading = "Allow Access") {
                    allowAccessVM.allowAccess(
                        teacherId = teacherId,
                        courseId = allowAccessVM.selectedCourse?.courseId?:0,
                        studentId = allowAccessVM.selectedStudent?.studentId?:0,
                        callBack = {

                            allowAccessVM.getAllStudents(teacherId = teacherId)
                        }
                    )

                }
            }
        }
        Spacer(Modifier.height(10.dp))
    }
    if (showSheet) {
        CourseSelectionBottomSheet(
            selectedCourse = allowAccessVM.selectedCourse,
            courseList = courses,
            commonInterface = object : BottomSheetCommonInterface {
                override fun closeSheet() { showSheet = false }
            },
            onSubmit = {
                allowAccessVM.selectedCourse = it
                allowAccessVM.getAllStudents(teacherId = teacherId, courseId = allowAccessVM.selectedCourse?.courseId)

                showSheet = false
            }
        )
    }
}