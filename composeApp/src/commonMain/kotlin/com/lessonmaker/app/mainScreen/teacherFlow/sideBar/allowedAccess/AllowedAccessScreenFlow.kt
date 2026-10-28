package com.lessonmaker.app.mainScreen.teacherFlow.sideBar.allowedAccess

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.theme.REdF14336
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold

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

    val students = remember {
        mutableStateListOf(
            AllowedStudent(1, "Sajid Mahmood", "25-ARID-0126"),
            AllowedStudent(2, "Muhammad Fahad", "25-ARID-0011"),
        )
    }

    Column(Modifier.fillMaxSize().background(White)) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(students, key = { it.id }) { s ->
                AllowedStudentCard(s) {

                    students.remove(s)
                }
            }
        }
    }
}

@Composable
private fun AllowedStudentCard(student: AllowedStudent, onRevoke: () -> Unit) {
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
            Text(student.name, color = bgColor, style = TextSize13().bold())
            Text(student.arid, color = loginTabUnselectedTxtClr, style = TextSize12().Regular())
        }
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