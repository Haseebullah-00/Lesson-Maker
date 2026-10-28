package com.lessonmaker.app.mainScreen.teacherFlow.sideBar.notesApprovals

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
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
import com.lessonmaker.app.common.ActionButtonStyle
import com.lessonmaker.app.common.TeacherActionButton
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.teacherFlow.profile.InitialsAvatar
import com.lessonmaker.app.mainScreen.teacherFlow.requests.EditSuggestionRequest
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.shared.textkit.editor.core.transactions.models.TextEditorTransactionType
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize10
import com.lessonmaker.app.theme.TextSize11
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.dataCellInfoBoxBg
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr

@Composable
fun NotesApprovalsScreenFlow(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {

    val requests = remember {
        mutableStateListOf(

                EditSuggestionRequest(
                    id = "e1",
                    studentName = "Muhammad Adnan",
                    regNo = "23-ARID-1099",
                    program = "BSCS",
                    lessonTitle = "Introduction to Programming",
                    courseCode = "CS-101",
                    courseTitle = "Programming Fundamentals"
                ),
        EditSuggestionRequest(
            id = "e2",
            studentName = "Abdul Jabbar",
            regNo = "23-ARID-0562",
            program = "BSCS",
            lessonTitle = "Control Structures",
            courseCode = "CS-101",
            courseTitle = "Programming Fundamentals"
        ),
        )

    }

    Column(Modifier.fillMaxSize().background(White)) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(requests) { request ->
                EditRequestCard(
                    request = request,
                    onReview = {

                        dashboardRoutes.invoke(
                            DashboardSubScreen.ReviewEditRequestScreenFlow.createRoute(request.id)
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun EditRequestCard(
    request: EditSuggestionRequest,
    onReview: () -> Unit
) {
    RequestCardContainer {
        Row(verticalAlignment = Alignment.CenterVertically) {
            InitialsAvatar(name = request.studentName, size = 28.dp)
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    text = request.studentName,
                    color = bgColor,
                    style = TextSize13().bold()
                )
                Text(
                    text = "${request.regNo} · ${request.program}",
                    color = loginTabUnselectedTxtClr,
                    style = TextSize10().Regular()
                )
            }
        }

        Spacer(Modifier.height(10.dp))
        Text(
            text = "Requested an edit in",
            color = loginTabUnselectedTxtClr,
            style = TextSize11().Regular()
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = request.lessonTitle,
            color = bgColor,
            style = TextSize14().bold()
        )
        Spacer(Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(dataCellInfoBoxBg, RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = request.courseCode,
                    color = fieldCursor,
                    style = TextSize10().bold()
                )
            }
            Spacer(Modifier.width(6.dp))
            Text(
                text = request.courseTitle,
                color = loginTabUnselectedTxtClr,
                style = TextSize11().Regular()
            )
        }

        Spacer(Modifier.height(12.dp))
        TeacherActionButton(
            text = "Review Edit",
            style = ActionButtonStyle.Primary,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onReview()
            }
        )
    }
}
@Composable
private fun RequestCardContainer(content: @Composable () -> Unit) {
    val shape = RoundedCornerShape(12.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape)
            .border(1.dp, fieldBorder, shape)
            .padding(14.dp)
    ) {
        content()
    }
}