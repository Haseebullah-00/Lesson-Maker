package com.lessonmaker.app.common.bottomSheets.courseSelectionBottomSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.MainButtonNew
import com.lessonmaker.app.common.bottomSheets.BottomSheetCommonInterface
import com.lessonmaker.app.models.teacher.myCourses.TeacherCourseData
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize15
import com.lessonmaker.app.theme.TextSize24
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.semiBold
import com.lessonmaker.app.theme.spaceBetweenFields
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.crown_icon
import lessonmaker.composeapp.generated.resources.new_cross_ic


import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseSelectionBottomSheet(selectedCourse:TeacherCourseData?,courseList: List<TeacherCourseData>, commonInterface: BottomSheetCommonInterface,onSubmit:(TeacherCourseData)-> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val lazyListState = rememberLazyListState()

    var picked by remember { mutableStateOf(selectedCourse) }


    ModalBottomSheet(
        onDismissRequest = {
            commonInterface.closeSheet()
        },
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp),
        tonalElevation = 16.dp,
        modifier = Modifier.fillMaxWidth().padding(bottom = 0.dp),
        dragHandle = {
            Box(modifier = Modifier.height(0.dp))
        },
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().wrapContentSize().padding(horizontal = 5.dp),
            backgroundColor = White,
            shape = RoundedCornerShape(5.dp),
            elevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(White)
                    .padding(horizontal = spaceBetweenFields),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Select Course",
                        style = TextSize24().semiBold(),
                        color = Black,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(fieldCursor)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                commonInterface.closeSheet()
                            },
                        contentAlignment = Alignment.Center
                    ){
                        Image(
                            painter = painterResource(Res.drawable.new_cross_ic),
                            contentDescription = "Close",
                            modifier = Modifier
                                .size(18.dp),
                            colorFilter = ColorFilter.tint(White)

                        )

                    }

                }

                if (courseList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No Courses Found",
                            style = TextSize24().semiBold(),
                            color = Black,
                            modifier = Modifier
                        )


                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 400.dp),
                        state = lazyListState,
                        contentPadding = PaddingValues(bottom = 10.dp)
                    ) {
                        items( courseList?:emptyList()) { item ->
                            CourseItemView(
                            item = item,
                            isSelected = picked?.courseId == item.courseId,
                            onClick = { picked = item }
                        )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MainButtonNew(heading = "Submit") {
                        picked?.let {
                            onSubmit(it)
                        }

                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
private fun CourseItemView(
    item: TeacherCourseData,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, if (isSelected) fieldCursor else fieldBorder, RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item?.courseName?:"",
            style = TextSize15().Regular(),
            color = if (isSelected) fieldCursor else Black,
            modifier = Modifier.weight(1f)
        )
        // simple radio dot (no extra drawables needed)
        Box(
            modifier = Modifier
                .size(21.dp)
                .border(1.5.dp, if (isSelected) fieldCursor else fieldBorder, CircleShape)
                .padding(4.dp)
                .background(if (isSelected) fieldCursor else White, CircleShape)
        )
    }
}
