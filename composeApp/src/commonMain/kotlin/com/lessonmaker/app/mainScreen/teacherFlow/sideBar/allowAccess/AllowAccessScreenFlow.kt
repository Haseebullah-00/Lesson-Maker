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
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.theme.*

data class AccessStudent(val id: Int, val name: String, val arid: String)

@Composable
fun AllowAccessScreenFlow(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {

    val courses = remember { listOf("Programming Fundamentals", "Object Oriented Programming","Data Structure $ Algorithms") }
    val students = remember {
        listOf(
            AccessStudent(1, "Muhammad Zain", "25-ARID-1148-BSCS"),
            AccessStudent(2, "Muhammad Ali", "25-ARID-1068-BSCS"),
            AccessStudent(3, "Haris Saleem", "25-ARID-1002-BSCS"),
        )
    }

    var selectedCourse by remember { mutableStateOf("") }
    var courseError by remember { mutableStateOf<String?>(null) }
    var menuOpen by remember { mutableStateOf(false) }
    var fieldWidthPx by remember { mutableStateOf(0) }
    var query by remember { mutableStateOf("") }
    var selectedStudent by remember { mutableStateOf<AccessStudent?>(null) }
    val density = LocalDensity.current

    val filtered = students.filter {
        it.name.contains(query, ignoreCase = true) || it.arid.contains(query, ignoreCase = true)
    }

    Column(Modifier.fillMaxSize().background(White)) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Dropdown: field only displays; the menu is handled here via onClick
            Box(Modifier.onSizeChanged { fieldWidthPx = it.width }) {
                AppDropdownField(
                    text = selectedCourse,
                    hint = "Select the course you want to give access",
                    errorMessage = courseError,
                    onClick = {
                        menuOpen = true
                    },
                    onClear = {
                        selectedCourse = ""
                        courseError = null
                    }
                )
                DropdownMenu(
                    expanded = menuOpen,
                    onDismissRequest = { menuOpen = false },
                    modifier = Modifier
                        .width(with(density) { fieldWidthPx.toDp() })
                        .background(White)
                ) {
                    courses.forEach { c ->
                        DropdownMenuItem(
                            text = { Text(c, color = fieldText, style = TextSize14()) },
                            onClick = {
                                selectedCourse = c
                                courseError = null
                                menuOpen = false
                            }
                        )
                    }
                }
            }

            AppSearchField(
                text = query,
                hint = "Search by name or ARID",
                onValueChange = { query = it }
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filtered, key = { it.id }) { s ->
                    val selected = selectedStudent?.id == s.id
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(if (selected) fieldCursor else White, RoundedCornerShape(8.dp))
                            .border(
                                BorderStroke(1.dp, if (selected) fieldCursor else fieldBorder),
                                RoundedCornerShape(8.dp)
                            )
                            .clickable(interactionSource = MutableInteractionSource(),
                                indication = null) { selectedStudent = s }
                            .padding(12.dp)
                    ) {
                        Text(s.name, color = if (selected) Color.White else bgColor, style = TextSize13().bold())
                        Text(
                            s.arid,
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
                    if (selectedCourse.isEmpty()) {
                        courseError = "Please select a course"
                    } else if (selectedStudent != null) {

                    }
                }
            }
        }
        Spacer(Modifier.height(10.dp))
    }
}