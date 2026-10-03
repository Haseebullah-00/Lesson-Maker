package com.lessonmaker.app.common.dialogue.filter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lessonmaker.app.common.ClickableSimpleField
import com.lessonmaker.app.common.ClickableSimpleFieldStartIcon
import com.lessonmaker.app.common.MainButton
import com.lessonmaker.app.common.bottomSheets.BottomSheetCommonInterface
import com.lessonmaker.app.extensionFunctions.handleNull
import com.lessonmaker.app.navigationControler.Screen
import com.lessonmaker.app.theme.TextSize24
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.backClr
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.buttonBackClr
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.arrow_drop_down
import lessonmaker.composeapp.generated.resources.ic_arrow_down
import lessonmaker.composeapp.generated.resources.ic_camera
import lessonmaker.composeapp.generated.resources.ic_close
import lessonmaker.composeapp.generated.resources.ic_down
import lessonmaker.composeapp.generated.resources.ic_uae_flag

@Composable
fun FilterDialogue(
    bottomSheetCommonInterface: BottomSheetCommonInterface
)
{

    val lazyGridState = rememberLazyListState()




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
                                text = "Filter by",
                                style = TextSize24().bold(),
                                color = White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                            Image(
                                painter = painterResource(Res.drawable.ic_close),
                                contentDescription = "Close",
                                modifier = Modifier.align(Alignment.CenterEnd).clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    bottomSheetCommonInterface.closeSheet()
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(30.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                            ClickableSimpleField(
                                text = "",
                                hint = "Globally",
                                endIcon = Res.drawable.ic_arrow_down,
                                onCleared = {

                                },
                                startIcon = null,
                                onClick = {

                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                            ClickableSimpleFieldStartIcon(
                                text = "",
                                hint = "United Arab Emirates",
                                endIcon = Res.drawable.ic_arrow_down,
                                onCleared = {

                                },
                                startIcon = Res.drawable.ic_uae_flag,
                                onClick = {

                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                            ClickableSimpleField(
                                text = "",
                                hint = "School",
                                endIcon = Res.drawable.ic_arrow_down,
                                onCleared = {

                                },
                                startIcon = null,
                                onClick = {

                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(40.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                            MainButton(
                                buttonBackClr,
                                Color.Transparent,
                                "Apply Filter",
                                60.dp,
                                0.dp,
                                onClick = {
                                    bottomSheetCommonInterface.moveNextScreen()
                                }
                            )
                        }


                    }
                }
            }

        }


    }


}
