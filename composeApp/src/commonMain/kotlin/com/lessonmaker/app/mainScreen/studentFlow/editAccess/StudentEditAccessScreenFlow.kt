package com.lessonmaker.app.mainScreen.studentFlow.editAccess


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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.studentFlow.EditRequestStatus
import com.lessonmaker.app.mainScreen.studentFlow.StudentEditCourse
import com.lessonmaker.app.mainScreen.studentFlow.StudentEditRequest
import com.lessonmaker.app.mainScreen.studentFlow.StudentEditStore
import com.lessonmaker.app.mainScreen.teacherFlow.profile.InitialsAvatar
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.BackgorundColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
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
import com.lessonmaker.app.theme.segmentTabBg
import com.lessonmaker.app.theme.semiBold
import com.lessonmaker.app.theme.statusApprovedBg
import com.lessonmaker.app.theme.statusApprovedText
import com.lessonmaker.app.theme.statusPendingBg
import com.lessonmaker.app.theme.statusPendingText
import com.lessonmaker.app.theme.statusRejectedBg
import com.lessonmaker.app.theme.statusRejectedText
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.ic_next
import lessonmaker.composeapp.generated.resources.lesson_ic
import org.jetbrains.compose.resources.painterResource

@Composable
fun StudentEditAccessScreenFlow(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {
    var tab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgorundColor)
    ) {
        EditAccessTabs(selected = tab, onSelect = { tab = it })

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (tab == 0) {
                val byTeacher = StudentEditStore.courses.groupBy { it.teacher }
                byTeacher.forEach { (teacher, list) ->
                    item(key = "t_$teacher") {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            InitialsAvatar(name = teacher.replace("Dr. ", ""), size = 28.dp)
                            Spacer(Modifier.width(8.dp))
                            Text(text = teacher, color = bgColor, style = TextSize14().semiBold())
                        }
                    }
                    items(list, key = { "c_${it.id}" }) { course ->
                        EditCourseCard(course) {
                            dashboardRoutes.invoke(
                                DashboardSubScreen.StudentEditCourseLessonsScreenFlow.createRoute(
                                    course.id, course.code, course.title
                                )
                            )
                        }
                    }
                }
            } else {
                items(StudentEditStore.requests, key = { "r_${it.id}" }) { RequestCard(it) }
            }
        }
    }
}

@Composable
private fun EditAccessTabs(selected: Int, onSelect: (Int) -> Unit) {
    val outer = RoundedCornerShape(10.dp)
    val titles = listOf("Courses", "My Requests")
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(segmentTabBg, outer)
            .padding(4.dp)
    ) {
        titles.forEachIndexed { index, title ->
            val isSel = selected == index
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp)
                    .background(if (isSel) White else Transparent, RoundedCornerShape(8.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onSelect(index) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = if (isSel) bgColor else loginTabUnselectedTxtClr,
                    style = TextSize13().semiBold(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun CodeChip(code: String) {
    Box(
        modifier = Modifier
            .background(dataCellInfoBoxBg, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) { Text(text = code, color = fieldCursor, style = TextSize12().bold()) }
}

@Composable
private fun EditCourseCard(course: StudentEditCourse, onClick: () -> Unit) {
    val shape = RoundedCornerShape(12.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape)
            .border(1.dp, fieldBorder, shape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(16.dp)
    ) {
        CodeChip(course.code)
        Spacer(Modifier.height(12.dp))
        Text(text = course.title, color = bgColor, style = TextSize16().bold())
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Edit access since ${course.since}",
            color = loginTabUnselectedTxtClr,
            style = TextSize12().Regular()
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
                    modifier = Modifier.size(14.dp)
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = "${course.notesCount} Lecture Notes",
                    color = loginTabUnselectedTxtClr,
                    style = TextSize12().Regular()
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Edit Notes", color = fieldCursor, style = TextSize12().semiBold())
                Spacer(Modifier.width(4.dp))
                Image(
                    painter = painterResource(Res.drawable.ic_next),
                    contentDescription = "",
                    modifier = Modifier.size(9.dp),
                    colorFilter = ColorFilter.tint(fieldCursor)
                )
            }
        }
    }
}

@Composable
private fun RequestCard(req: StudentEditRequest) {
    val shape = RoundedCornerShape(12.dp)
    val (chipBg, chipText, label) = when (req.status) {
        EditRequestStatus.Pending -> Triple(statusPendingBg, statusPendingText, "Pending")
        EditRequestStatus.Approved -> Triple(statusApprovedBg, statusApprovedText, "Approved")
        EditRequestStatus.Rejected -> Triple(statusRejectedBg, statusRejectedText, "Rejected")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape)
            .border(1.dp, fieldBorder, shape)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CodeChip(req.code)
            Box(
                modifier = Modifier
                    .background(chipBg, RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) { Text(text = label, color = chipText, style = TextSize12().semiBold()) }
        }
        Spacer(Modifier.height(10.dp))
        Text(text = req.lessonTitle, color = bgColor, style = TextSize14().bold())
        Spacer(Modifier.height(4.dp))
        Text(text = req.info, color = loginTabUnselectedTxtClr, style = TextSize12().Regular())
        if (req.note.isNotBlank()) {
            Spacer(Modifier.height(6.dp))
            Text(
                text = req.note,
                color = if (req.status == EditRequestStatus.Rejected) statusRejectedText else loginTabUnselectedTxtClr,
                style = TextSize12().Regular()
            )
        }
    }
}