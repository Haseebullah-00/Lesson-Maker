package com.lessonmaker.app.mainScreen.teacherFlow.profile


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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.ActionButtonStyle
import com.lessonmaker.app.common.TeacherActionButton

import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.navigationControler.Screen
import com.lessonmaker.app.theme.BackgorundColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
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
import lessonmaker.composeapp.generated.resources.edit_ic_new
import lessonmaker.composeapp.generated.resources.ic_logout

@Composable
fun TeacherProfileScreenFlow(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {
    // Use the saved user when available, otherwise show the design's sample data.
    val user = remember {
        runCatching { dashboardViewModel.sharedPreferenceManager.getCurrentUser() }.getOrNull()
    }
    val name = user?.name?.takeIf { it.isNotBlank() } ?: "Dr. Adnan Ahmed"
    val email = user?.email?.takeIf { it.isNotBlank() } ?: "dr.adnan@biit.edu.pk"
    // SingleUserModel has no designation / department yet, so these are fixed for now.
    val designation = "Associate Professor, CS Dept"
    val department = "Computer Science"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgorundColor)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // profile info card
        val shape = RoundedCornerShape(12.dp)
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
                    Text(
                        text = name,
                        color = bgColor,
                        style = TextSize16().bold()
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = designation,
                        color = loginTabUnselectedTxtClr,
                        style = TextSize12().Regular()
                    )
                }
            }

            Spacer(Modifier.height(14.dp))
            Box(Modifier.fillMaxWidth().height(1.dp).background(fieldBorder))
            Spacer(Modifier.height(14.dp))

            InfoRow(label = "Email", value = email)
            Spacer(Modifier.height(10.dp))
            InfoRow(label = "Department", value = department)
        }

        Spacer(Modifier.height(16.dp))

        TeacherActionButton(
            text = "Edit Profile",
            style = ActionButtonStyle.Outline,
            height = 44.dp,
            icon = Res.drawable.edit_ic_new,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                dashboardRoutes.invoke(DashboardSubScreen.EditProfileScreen.route)
            }
        )

        Spacer(Modifier.height(10.dp))

        TeacherActionButton(
            text = "Logout",
            style = ActionButtonStyle.Danger,
            height = 44.dp,
            icon = Res.drawable.ic_logout,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                // same logout behaviour as the existing AccountScreenFlow
                dashboardViewModel.sharedPreferenceManager.isStudentLoggedIn = false
                dashboardViewModel.sharedPreferenceManager.isTeacherLoggedIn = false
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
        Text(
            text = label,
            color = loginTabUnselectedTxtClr,
            style = TextSize12().Regular()
        )
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

@Composable
fun InitialsAvatar(
    name: String,
    modifier: Modifier = Modifier,
    size: Dp = 32.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .background(dataCellInfoBoxBg, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name.toInitials(),
            color = fieldCursor,
            style = if (size >= 44.dp) TextSize16().bold() else TextSize12().bold()
        )
    }
}

fun String.toInitials(maxChars: Int = 2): String =
    split(" ", ".")
        .filter { it.isNotBlank() }
        .take(maxChars)
        .joinToString("") { it.first().uppercase() }
        .ifEmpty { "T" }