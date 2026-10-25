package com.lessonmaker.app.mainScreen.teacherFlow.requests.reviewEditRequestScreen



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lessonmaker.app.common.ActionButtonStyle
import com.lessonmaker.app.common.TeacherActionButton

import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.teacherFlow.requests.TeacherRequestsStore
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.BackgorundColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize10
import com.lessonmaker.app.theme.TextSize11
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.approvedGreen
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.fieldHint
import com.lessonmaker.app.theme.fieldText
import com.lessonmaker.app.theme.greyE3
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.ic_gallery
import org.jetbrains.compose.resources.painterResource

// Dummy content so the screen looks like the design. Replace with the real suggested edit
// (e.g. render it with TextKitViewer) once the API is ready.
private val dummyToc = listOf(
    "Introduction to Programming Fundamentals",
    "Variables and Data Types",
    "Control Structures (Conditions and Loops)",
)
private const val dummyResourceUrl = "https://www.youtube.com/watch?v=KvFSMDsRUA4&utm_source"

@Composable
fun ReviewEditRequestScreenFlow(
    requestId: String,
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgorundColor)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TableOfContentsCard(items = dummyToc)
            LessonContentCard(
                onOpenLink = { runCatching { uriHandler.openUri(dummyResourceUrl) } }
            )
        }

        // bottom action bar (same look as the "Add Lecture Note" bar)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .navigationBarsPadding()
        ) {
            Box(Modifier.fillMaxWidth().height(1.dp).background(greyE3))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TeacherActionButton(
                    text = "Reject",
                    style = ActionButtonStyle.Danger,
                    height = 44.dp,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        TeacherRequestsStore.resolveEdit(requestId)
                        dashboardRoutes.invoke(DashboardSubScreen.POPBackStack.route)
                    }
                )
                TeacherActionButton(
                    text = "Approve",
                    style = ActionButtonStyle.Primary,
                    height = 44.dp,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        TeacherRequestsStore.resolveEdit(requestId)
                        dashboardRoutes.invoke(DashboardSubScreen.POPBackStack.route)
                    }
                )
            }
        }
    }
}

/* ---------------------------------------------------------------- cards */

@Composable
private fun ReviewCard(content: @Composable () -> Unit) {
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
private fun TableOfContentsCard(items: List<String>) {
    ReviewCard {
        Text(
            text = "TABLE OF CONTENTS",
            color = fieldCursor,
            style = TextSize10().bold()
        )
        Spacer(Modifier.height(8.dp))
        items.forEachIndexed { index, title ->
            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                Text(
                    text = (index + 1).toString().padStart(2, '0'),
                    color = fieldCursor,
                    style = TextSize11().bold()
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = title,
                    color = bgColor,
                    style = TextSize11().Regular()
                )
            }
        }
    }
}

@Composable
private fun LessonContentCard(onOpenLink: () -> Unit) {
    val bodyStyle = TextSize13().Regular().copy(lineHeight = 20.sp)

    ReviewCard {
        Text(
            text = "Programming Fundamentals Basics",
            color = bgColor,
            style = TextSize16().bold()
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Programming Fundamentals provides the basic concepts behind how programs " +
                    "are designed and developed. In this lesson, we will cover the foundational " +
                    "concepts of programming, including variables, data types, operators, " +
                    "input/output, and basic control structures.",
            color = fieldText,
            style = bodyStyle
        )
        Spacer(Modifier.height(10.dp))
        BulletPoint(
            lead = "Variables & Data Types:",
            body = "Defines how data is stored, represented, and used in a program.",
            bodyStyle = bodyStyle
        )
        Spacer(Modifier.height(4.dp))
        BulletPoint(
            lead = "Control Structures:",
            body = "Controls program flow using conditions, loops, and decision-making statements.",
            bodyStyle = bodyStyle
        )

        Spacer(Modifier.height(12.dp))
        DiagramPlaceholder(label = "Diagram: Common & Expected Flow")

        Spacer(Modifier.height(14.dp))
        Text(
            text = "RESOURCE REFERENCE",
            color = loginTabUnselectedTxtClr,
            style = TextSize10().bold()
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = dummyResourceUrl,
            color = fieldCursor,
            style = TextSize12().semiBold(),
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onOpenLink
            )
        )
    }
}

@Composable
private fun BulletPoint(
    lead: String,
    body: String,
    bodyStyle: androidx.compose.ui.text.TextStyle
) {
    Row {
        Text(
            text = "•",
            color = approvedGreen,
            style = bodyStyle
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = approvedGreen, fontWeight = FontWeight.Bold)) {
                    append(lead)
                }
                append(" ")
                append(body)
            },
            color = fieldText,
            style = bodyStyle
        )
    }
}

@Composable
private fun DiagramPlaceholder(label: String) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .background(BackgorundColor, shape)
            .border(1.dp, fieldBorder, shape),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(Res.drawable.ic_gallery),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                colorFilter = ColorFilter.tint(fieldHint)
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = label,
                color = fieldHint,
                style = TextSize10().Regular()
            )
        }
    }
}