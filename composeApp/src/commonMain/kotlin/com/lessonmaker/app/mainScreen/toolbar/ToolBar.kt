package com.lessonmaker.app.mainScreen.toolbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize22
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.semiBold


import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.back_img
import lessonmaker.composeapp.generated.resources.show_share_icon

@Composable
fun ToolBar(toolBarViewModel: ToolBarViewModel, dashboardRoutes: (String) -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(toolBarViewModel.backgroundColor)
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 40.dp, bottom = 10.dp, start = 16.dp, end = 16.dp)
                .background(toolBarViewModel.backgroundColor),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            if (toolBarViewModel.showAppLogo) {
                Box(
                    modifier = Modifier.padding(end = 15.dp), contentAlignment = Alignment.Center
                ) {
//                    Image(
//                        painter = painterResource(Res.drawable.ic_logo_toolbar),
//                        contentDescription = ""
//                    )
                }
            }

            if (toolBarViewModel.showBackImage) {
                Box(
                    modifier = Modifier.padding(end = 16.dp)
                        .size(44.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null // Disables the ripple effect
                        ) {
                            dashboardRoutes.invoke(DashboardSubScreen.POPBackStack.route)
                        }, contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(Res.drawable.back_img),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            }

            if (toolBarViewModel.showBackText) {
                Box(modifier = Modifier
                    .weight(1f)
                    ,
                    contentAlignment = Alignment.Center

                    ) {


                    Text(
                        text = toolBarViewModel.backHeading,
                        style = TextSize16().semiBold(),
                        color = White
                    )
                }
            }
            if (toolBarViewModel.showShare){

                Image(
                    painter = painterResource(Res.drawable.show_share_icon),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(44.dp)
                )
            }
            else
            {

            }




            /* if (toolBarViewModel.showProfileImage) {
            Image(
                painter = painterResource(resource = Res.drawable.ic_locaffy),
                contentDescription = "App Logo",
                modifier = Modifier.width(111.dp)
            )
            Spacer(modifier = Modifier.weight(1f, fill = true))

        }*/

//            if (toolBarViewModel.showMenu) {
//                Box() {
//                    SingleToolbarActionButton(drawableResource = Res.drawable.ic_filter) {
//                     //   dashboardRoutes.invoke(DashboardSubScreen.NotificationScreen.route)
//                    }
//
//                }
//            }
//            if (toolBarViewModel.showNotification) {
//                Box() {
//                    SingleToolbarActionButton(drawableResource = Res.drawable.ic_notification) {
//                     //   dashboardRoutes.invoke(DashboardSubScreen.NotificationScreen.route)
//                    }
//
//                }
//            }

//            if (toolBarViewModel.showMenu) {
//                Card(
//                    shape = RoundedCornerShape(8.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
//                    colors = CardDefaults.cardColors(
//                        containerColor = secondaryColor
//                    ),
//                    modifier = Modifier
//                        .width(40.dp)
//                        .height(40.dp)
//                        .background(secondaryColor, RoundedCornerShape(8.dp))
//                        .clickable(
//                            interactionSource = MutableInteractionSource(),
//                            indication = null
//                        ) {
//
//                        }
//                )
//                {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Image(
//                            painter = painterResource(resource = Res.drawable.ic_menu),
//                            contentDescription = "Event Back image",
//                            modifier = Modifier
//
//                        )
//                    }
//                }
//            }
//
//
//
//            if (toolBarViewModel.showClearAll) {
//                Box(
//                    modifier = Modifier
//                        .width(0.dp).weight(1f).clickable(
//                            interactionSource = MutableInteractionSource(),
//                            indication = null
//                        ) {
//                            NetworkLogs("clearAll", "Clicked")
//                            toolBarViewModel.clearAllClicked?.invoke()
//                        },
//                    contentAlignment = Alignment.CenterEnd
//                ) {
//                    Text(
//                        text = "Clear All",
//                        style = TextSize14().Medium(),
//                        color = White
//                    )
//                }
//            }
        }
    }
}


@Composable
fun SingleToolbarActionButton(
    modifier: Modifier = Modifier.size(40.dp),
    drawableResource: DrawableResource,
    rotation: Float = 0f,
    callback: () -> Unit
) {
    Box(
        modifier = modifier.clip(shape = CircleShape).clickable { callback.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(resource = drawableResource),
            contentDescription = "Event Back image",
            modifier = Modifier.rotate(rotation)
        )
    }
}

@Composable
fun counterBadge(modifier: Modifier, intCount: Int) {
    if (intCount > 0) {
        Box(
            modifier = modifier
                .size(18.dp)
                .background(Color(0xFFFF3030), shape = CircleShape).padding(2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = intCount.toString(),
                color = White,
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}