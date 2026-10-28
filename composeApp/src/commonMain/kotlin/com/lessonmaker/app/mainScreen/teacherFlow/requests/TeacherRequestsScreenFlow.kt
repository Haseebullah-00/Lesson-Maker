package com.lessonmaker.app.mainScreen.teacherFlow.requests


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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.ActionButtonStyle
import com.lessonmaker.app.common.TeacherActionButton

import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.teacherFlow.profile.InitialsAvatar
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.BackgorundColor
import com.lessonmaker.app.theme.REdF14336
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize10
import com.lessonmaker.app.theme.TextSize11
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.dataCellInfoBoxBg
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.loginTabSelectionBg
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold

@Composable
fun TeacherRequestsScreenFlow(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {

    var selectedTab by rememberSaveable { mutableStateOf(0) }

    val noteRequests = TeacherRequestsStore.noteRequests
    val editRequests = TeacherRequestsStore.editRequests

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgorundColor),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            RequestTabSwitcher(
                selected = selectedTab,
                notesCount = noteRequests.size,
                editsCount = editRequests.size,
                onSelect = { selectedTab = it }
            )
        }

        if (selectedTab == 0) {
            if (noteRequests.isEmpty()) {
                item { EmptyRequestsText("No note requests") }
            }
            items(noteRequests, key = { it.id }) { request ->
                NoteRequestCard(
                    request = request,
                    onReject = { TeacherRequestsStore.resolveNote(request.id) },
                    onAccept = { TeacherRequestsStore.resolveNote(request.id) }
                )
            }
        } else {
            if (editRequests.isEmpty()) {
                item { EmptyRequestsText("No edit requests") }
            }
            items(editRequests, key = { it.id }) { request ->
                EditRequestCard(
                    request = request,
                    onReview = {
                        dashboardRoutes.invoke(
                            DashboardSubScreen.ReviewEditRequestScreenFlow.createRoute(request.id)
                        )
                    },
                    onLater = { /* nothing to do, request stays in the list */ }
                )
            }
        }
    }
}

/* ---------------------------------------------------------------- tabs */

@Composable
private fun RequestTabSwitcher(
    selected: Int,
    notesCount: Int,
    editsCount: Int,
    onSelect: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(loginTabSelectionBg, RoundedCornerShape(12.dp))
            .padding(4.dp)
    ) {
        RequestTab(
            text = "Notes Requests",
            count = notesCount,
            selected = selected == 0,
            onClick = { onSelect(0) },
            modifier = Modifier.weight(1f)
        )
        RequestTab(
            text = "Edit Requests",
            count = editsCount,
            selected = selected == 1,
            onClick = { onSelect(1) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun RequestTab(
    text: String,
    count: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier = modifier
            .fillMaxSize()
            .then(if (selected) Modifier.shadow(2.dp, shape) else Modifier)
            .background(if (selected) White else Color.Transparent, shape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                color = if (selected) bgColor else loginTabUnselectedTxtClr,
                style = if (selected) TextSize12().bold() else TextSize12().semiBold()
            )
            if (count > 0) {
                Spacer(Modifier.width(6.dp))
                CountBadge(count)
            }
        }
    }
}

@Composable
private fun CountBadge(count: Int) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .background(REdF14336, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.toString(),
            color = White,
            style = TextSize10().bold()
        )
    }
}

/* ---------------------------------------------------------------- cards */

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

@Composable
private fun NoteRequestCard(
    request: NoteAccessRequest,
    onReject: () -> Unit,
    onAccept: () -> Unit
) {
    RequestCardContainer {
        Text(
            text = request.teacherName,
            color = bgColor,
            style = TextSize13().bold()
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = "Requested your lecture notes for",
            color = loginTabUnselectedTxtClr,
            style = TextSize11().Regular()
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = request.courseTitle,
            color = bgColor,
            style = TextSize14().bold()
        )
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TeacherActionButton(
                text = "Reject",
                style = ActionButtonStyle.Danger,
                modifier = Modifier.weight(1f),
                onClick = onReject
            )
            TeacherActionButton(
                text = "Accept",
                style = ActionButtonStyle.Primary,
                modifier = Modifier.weight(1f),
                onClick = onAccept
            )
        }
    }
}

@Composable
private fun EditRequestCard(
    request: EditSuggestionRequest,
    onReview: () -> Unit,
    onLater: () -> Unit
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
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TeacherActionButton(
                text = "Review",
                style = ActionButtonStyle.Primary,
                modifier = Modifier.weight(1f),
                onClick = onReview
            )
            TeacherActionButton(
                text = "Later",
                style = ActionButtonStyle.Outline,
                modifier = Modifier.weight(1f),
                onClick = onLater
            )
        }
    }
}

@Composable
private fun EmptyRequestsText(message: String) {
    Text(
        text = message,
        color = loginTabUnselectedTxtClr,
        style = TextSize14().Regular(),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
    )
}

data class NoteAccessRequest(
    val id: String,
    val teacherName: String,
    val courseTitle: String
)

/** A student suggesting an edit on one of your lessons. */
data class EditSuggestionRequest(
    val id: String,
    val studentName: String,
    val regNo: String,
    val program: String,
    val lessonTitle: String,
    val courseCode: String,
    val courseTitle: String
)

/**
 * Temporary in-memory data so the screens work before the API is connected.
 * Replace with a ViewModel / repository later; the screens only read these lists
 * and call resolveNote / resolveEdit.
 */
object TeacherRequestsStore {

    val noteRequests = mutableStateListOf(
        NoteAccessRequest("n1", "Dr. Ahmed", "Programming Fundamentals"),
        NoteAccessRequest("n2", "Dr. Ali", "Object Oriented Programming"),
    )

    val editRequests = mutableStateListOf(
        EditSuggestionRequest(
            id = "e1",
            studentName = "Muhammad Ali",
            regNo = "23-ARID-1099",
            program = "BSCS",
            lessonTitle = "Introduction to Programming",
            courseCode = "CS-101",
            courseTitle = "Programming Fundamentals"
        ),
        EditSuggestionRequest(
            id = "e2",
            studentName = "Haris Saleem",
            regNo = "23-ARID-0562",
            program = "BSCS",
            lessonTitle = "Control Structures",
            courseCode = "CS-101",
            courseTitle = "Programming Fundamentals"
        ),
    )

    fun findEditRequest(id: String): EditSuggestionRequest? =
        editRequests.firstOrNull { it.id == id }

    /** Accept or reject a note-access request (it leaves the list either way). */
    fun resolveNote(id: String) {
        noteRequests.removeAll { it.id == id }
    }

    /** Approve or reject an edit suggestion (it leaves the list either way). */
    fun resolveEdit(id: String) {
        editRequests.removeAll { it.id == id }
    }
}