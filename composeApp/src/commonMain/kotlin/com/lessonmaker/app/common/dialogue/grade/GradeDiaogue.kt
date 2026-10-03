package com.lessonmaker.app.common.dialogue.grade

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lessonmaker.app.common.MainButton
import com.lessonmaker.app.common.bottomSheets.BottomSheetCommonInterface
import com.lessonmaker.app.theme.PrimaryColor
import com.lessonmaker.app.theme.TextSize15
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize24
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.backClr
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.buttonBackClr


@Composable
fun GradeDialogue(
    bottomSheetCommonInterface: BottomSheetCommonInterface,
    onGradeSelected: (String) -> Unit

)
{

    val lazyGridState = rememberLazyListState()

    var selectedGrade by remember { mutableStateOf("All") } // <-- add this state
    val grades = listOf("All") + (1..12).map { it.toString() }
    Dialog(
        onDismissRequest = {
            bottomSheetCommonInterface.closeSheet()
        },
        properties = DialogProperties
            (
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true

        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),

                ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = 0.dp,
                    backgroundColor = backClr,
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)
                    )
                    {

                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        )
                        {
                            Text(
                                text = "Select Grade",
                                style = TextSize24().bold(),
                                color = White,
                                modifier = Modifier.align(Alignment.Center)
                            )

                        }

                        Spacer(modifier = Modifier.height(30.dp))

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(5),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                            items(grades) { grade ->
                                GradeItem(
                                    text = grade,
                                    isSelected = grade == selectedGrade,
                                    onClick = { selectedGrade = grade }

                                )
                            }
                        }


                        Spacer(modifier = Modifier.height(30.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                            MainButton(
                                buttonBackClr,
                                androidx.compose.ui.graphics.Color.Transparent,
                                "Submit",
                                60.dp,
                                0.dp,
                                onClick = {
                                    bottomSheetCommonInterface.moveNextScreen()
                                    onGradeSelected(selectedGrade)
                                }
                            )
                        }


                    }
                }
            }

        }


    }


}

@Composable
fun GradeItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 6.dp, // <-- shadow/elevation
        backgroundColor = Color.White,
        modifier = Modifier
            .size(55.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // <-- removes ripple/click effect
            ) { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) PrimaryColor else Color.Transparent,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = TextSize15().bold(),
                color = if (isSelected) PrimaryColor else Color.Black
            )
        }
    }
}

