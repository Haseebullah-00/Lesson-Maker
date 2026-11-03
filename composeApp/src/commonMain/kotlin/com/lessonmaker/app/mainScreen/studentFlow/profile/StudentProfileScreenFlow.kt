package com.lessonmaker.app.mainScreen.studentFlow.profile



import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.ActionButtonStyle
import com.lessonmaker.app.common.TeacherActionButton
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.studentFlow.StudentDummyStore
import com.lessonmaker.app.mainScreen.teacherFlow.profile.InitialsAvatar
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.navigationControler.Screen
import com.lessonmaker.app.theme.BackgorundColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.edit_ic_new
import lessonmaker.composeapp.generated.resources.ic_logout

@Composable
fun StudentProfileScreenFlow(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {
    val dummy = StudentDummyStore.student
    val user = remember {
        runCatching { dashboardViewModel.sharedPreferenceManager.getStudent() }.getOrNull()
    }
    val name = user?.name?.takeIf { it.isNotBlank() } ?: ""
    val email = user?.email?.takeIf { it.isNotBlank() } ?:""
    val aridNo = user?.aridNo?.takeIf { it.isNotBlank() } ?:""
    val shape = RoundedCornerShape(12.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgorundColor)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White, shape)
                .border(1.dp, fieldBorder, shape)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                InitialsAvatar(name = name, size = 48.dp)
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(text = name, color = bgColor, style = TextSize16().bold())
                  //  Spacer(Modifier.height(2.dp))
                    //Text(text = dummy.program, color = loginTabUnselectedTxtClr, style = TextSize12().Regular())
                }
            }
            Spacer(Modifier.height(14.dp))
            Box(Modifier.fillMaxWidth().height(1.dp).background(fieldBorder))
            Spacer(Modifier.height(14.dp))

            InfoRow("ARID Number", aridNo)
            Spacer(Modifier.height(14.dp))
            Box(Modifier.fillMaxWidth().height(1.dp).background(fieldBorder))
            Spacer(Modifier.height(14.dp))
            InfoRow("Email", email)
//            Spacer(Modifier.height(10.dp))
//            InfoRow("Department", dummy.department)
//            Spacer(Modifier.height(10.dp))
//            InfoRow("Semester", dummy.semester)
        }

        Spacer(Modifier.height(16.dp))

        TeacherActionButton(
            text = "Edit Profile",
            style = ActionButtonStyle.Outline,
            height = 44.dp,
            icon = Res.drawable.edit_ic_new,
            modifier = Modifier.fillMaxWidth(),
            onClick = { dashboardRoutes.invoke(DashboardSubScreen.EditProfileScreen.route) }
        )
        Spacer(Modifier.height(10.dp))
        TeacherActionButton(
            text = "Logout",
            style = ActionButtonStyle.Danger,
            height = 44.dp,
            icon = Res.drawable.ic_logout,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                dashboardViewModel.sharedPreferenceManager.isStudentLoggedIn = false
                dashboardViewModel.sharedPreferenceManager.isTeacherLoggedIn = false
                dashboardViewModel.sharedPreferenceManager.isLoggedIn=false
                dashboardViewModel.sharedPreferenceManager.teacherUser = null
                dashboardViewModel.sharedPreferenceManager.studentUser = null
                dashboardRoutes.invoke(Screen.LoginScreenFlow.route)
            }
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = loginTabUnselectedTxtClr, style = TextSize12().Regular())
        Spacer(Modifier.width(12.dp))
        Text(
            text = value,
            color = bgColor,
            style = TextSize12().semiBold(),
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}