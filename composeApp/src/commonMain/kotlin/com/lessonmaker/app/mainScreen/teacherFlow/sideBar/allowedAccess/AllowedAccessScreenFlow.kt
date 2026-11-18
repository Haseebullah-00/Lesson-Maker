package com.lessonmaker.app.mainScreen.teacherFlow.sideBar.allowedAccess

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.base.BaseListWithRefresh
import com.lessonmaker.app.common.widgets.EmptyScreen
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.models.course.AllowedCourseAccessData
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.BackgorundColor
import com.lessonmaker.app.theme.REdF14336
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.ic_empty_courses
import org.koin.compose.viewmodel.koinViewModel

data class AllowedStudent(
    val id: Int,
    val name: String,
    val arid: String,
)

@Composable
fun AllowedAccessScreenFlow(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {
    val allowedAccessVM = koinViewModel<AllowedAccessVM>()
    val teacherId = dashboardViewModel.sharedPreferenceManager.getTeacher()?.id?:0
    val students by  allowedAccessVM.students.collectAsState()
    LaunchedEffect(Unit){
        allowedAccessVM.getAllStudents(teacherId = teacherId)
    }

//    val students = remember {
//        mutableStateListOf(
//            AllowedStudent(1, "Sajid Mahmood", "25-ARID-0126"),
//            AllowedStudent(2, "Muhammad Fahad", "25-ARID-0011"),
//        )
//    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgorundColor)

    )
    {
        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            BaseListWithRefresh(
                viewModel = allowedAccessVM,
                emptyView = {
                    if (allowedAccessVM.showEmptyScreen) {
                        EmptyScreen(
                            emptyImage = Res.drawable.ic_empty_courses,
                            message = "No courses assigned yet",
                            subHeading = "Your courses will appear here once assigned. In\n the meantime, explore your colleagues’ courses",
                            showHomeButton = false,
                            imgModifier = Modifier.size(128.dp)
                        ) {
                            dashboardRoutes.invoke(DashboardSubScreen.LaunchHomeScreenFlow.route)
                        }
                    }
                },
                refreshCall = {
                    allowedAccessVM.getAllStudents(teacherId = teacherId)
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(students) { s ->
                        AllowedStudentCard(s, onRevoke = {
                            allowedAccessVM.revokeAccess(s.accessId?:0, callBack = {
                                allowedAccessVM.getAllStudents(teacherId = teacherId)
                            })
                        })
                    }
                }
            }
        }
    }


}

@Composable
private fun AllowedStudentCard(student: AllowedCourseAccessData?, onRevoke: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, RoundedCornerShape(12.dp))
            .border(BorderStroke(1.dp, fieldBorder), RoundedCornerShape(12.dp))
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(student?.studentName?:"", color = bgColor, style = TextSize13().bold())
                Spacer(modifier = Modifier.width(3.dp))

            }

            Spacer(modifier = Modifier.height(3.dp))
            Text(student?.aridNo?:"", color = loginTabUnselectedTxtClr, style = TextSize12().Regular())


        }
        Column(){
            Text(student?.courseName?:"", color = fieldCursor, style = TextSize13().semiBold())
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "Revoke Access",
                color = REdF14336,
                style = TextSize12().semiBold(),
                modifier = Modifier
                    .background(REdF14336.copy(alpha = 0.1f), RoundedCornerShape(6.dp))
                    .clickable { onRevoke() }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }

    }
}