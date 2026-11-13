package com.lessonmaker.app.mainScreen.studentFlow.myCourses.lessons.lessonContent


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.htmlViewer.StudentLessonHtmlViewer
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.studentFlow.StudentDummyStore
import com.lessonmaker.app.models.student.lesson.lessonContent.TableOfContents
import com.lessonmaker.app.shared.textkit.editor.core.parser.TextAlign
import com.lessonmaker.app.theme.BackgorundColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.dataCellInfoBoxBg
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StudentLessonContentScreenFlow(
    lessonId: String,
    courseCode: String,
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {
    val studentLessonContentVM = koinViewModel<StudentLessonContentVM>()
    LaunchedEffect(Unit){
        studentLessonContentVM.getLessonContent(lessonId = lessonId.toInt())
    }
    val content = remember(lessonId) { StudentDummyStore.content(lessonId) }

    val lessonContent by studentLessonContentVM.lessonContent.collectAsState()
    val tocList by studentLessonContentVM.tableOfContents.collectAsState()
    val uriHandler = LocalUriHandler.current
    val cardShape = RoundedCornerShape(12.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgorundColor)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if(tocList.isNotEmpty()){
            LessonTableOfContents(items = tocList?:emptyList())
        }

        lessonContent?.let { data ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White, cardShape)
                    .border(1.dp, fieldBorder, cardShape)
                    .padding(14.dp)
            ) {
                Text(text = data.lessonTitle.orEmpty(), color = bgColor, style = TextSize16().bold())
                Spacer(Modifier.height(10.dp))

                StudentLessonHtmlViewer(
                    lessonHtml = data.lessonContent.orEmpty(),
                    scrollable = false,
                    contentPadding = 0.dp,
                )
            }
        }
        Spacer(Modifier.height(16.dp))


        // Lesson body
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(White, cardShape)
//                .border(1.dp, fieldBorder, cardShape)
//                .padding(14.dp)
//        ) {
//            Text(text = content.heading, color = bgColor, style = TextSize16().bold())
//            Spacer(Modifier.height(10.dp))
//            Text(text = content.intro, color = bgColor, style = TextSize13().Regular())
//            Spacer(Modifier.height(10.dp))
//
//            content.bullets.forEach { bullet ->
//                Row(modifier = Modifier.padding(vertical = 3.dp)) {
//                    Text(text = "•", color = fieldCursor, style = TextSize13().bold())
//                    Spacer(Modifier.width(8.dp))
//                    Text(
//                        text = buildAnnotatedString {
//                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(bullet.label) }
//                            append(" ")
//                            append(bullet.text)
//                        },
//                        color = bgColor,
//                        style = TextSize13().Regular()
//                    )
//                }
//            }
//
//            Spacer(Modifier.height(12.dp))
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(110.dp)
//                    .background(BackgorundColor, RoundedCornerShape(8.dp))
//                    .border(1.dp, fieldBorder, RoundedCornerShape(8.dp)),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = content.diagramCaption,
//                    color = loginTabUnselectedTxtClr,
//                    style = TextSize12().Regular()
//                )
//            }
//
//            Spacer(Modifier.height(12.dp))
//            Text(text = "RESOURCE REFERENCE", color = loginTabUnselectedTxtClr, style = TextSize12().bold())
//            Spacer(Modifier.height(4.dp))
//            Text(
//                text = content.resourceUrl,
//                color = fieldCursor,
//                style = TextSize13().semiBold(),
//                modifier = Modifier.clickable {
//                    runCatching { uriHandler.openUri(content.resourceUrl) }
//                }
//            )
//        }
      //  Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun LessonTableOfContents(
    items: List<TableOfContents>,
    modifier: Modifier = Modifier,
    title: String = "TABLE OF CONTENTS",
    onItemClick: ((TableOfContents) -> Unit)? = null,
) {
    if (items.isEmpty()) return
    val shape = RoundedCornerShape(12.dp)
    val sorted = remember(items) { items.sortedBy { it.sortOrder ?: 0 } }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(White, shape)
            .border(1.dp, fieldBorder, shape)
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = title,
                color = fieldCursor,
                style = TextSize12().bold(),

            )

        }

        Spacer(Modifier.height(8.dp))
        sorted.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(if (onItemClick != null) Modifier.clickable { onItemClick(item) } else Modifier)
                    .padding(start = (((item.headingLevel ?: 1) - 1).coerceAtLeast(0) * 12).dp)
                    .padding(vertical = 2.dp)
            ) {
//                Text(
//                    text = (index + 1).toString().padStart(2, '0'),
//                    color = loginTabUnselectedTxtClr,
//                    style = TextSize12().Regular()
//                )
//                Spacer(Modifier.width(10.dp))
                Text(text = item.headingTitle.orEmpty(), color = bgColor, style = TextSize12().semiBold())
            }
        }
    }
}