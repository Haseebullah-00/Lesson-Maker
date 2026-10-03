package com.lessonmaker.app.mainScreen.bottomNavigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.*
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.ButtonBackgroundColor
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.secondaryColor
import com.lessonmaker.app.theme.PrimaryColor
import com.lessonmaker.app.theme.buttonClr
import com.lessonmaker.app.theme.NavigationButtonText
import com.lessonmaker.app.theme.blurClr
import com.lessonmaker.app.theme.selectedTabbarClr


@Composable
fun NavigationWidget(navigationViewModel: NavigationViewModel,
                     homeNewRoute: (String) -> Unit) {
    var selectedIndex by remember { mutableStateOf(2) }

    val navItems = listOf(
        SingleNavigationItem("Leaderboard", Res.drawable.selected_trophy_icon, Res.drawable.unselected_trophy_icon),
        SingleNavigationItem("Categories", Res.drawable.selected_category_icon, Res.drawable.unselected_category_icon),
        SingleNavigationItem("Home", Res.drawable.home_icon, Res.drawable.home_icon),
        SingleNavigationItem("Notification", Res.drawable.selected_notification_icon, Res.drawable.unselected_notification_icon),
        SingleNavigationItem("Profile", Res.drawable.selected_profile_icon, Res.drawable.unselected_profile_icon)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(WindowInsets.navigationBars.asPaddingValues())
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(82.dp)
        ) {

            Image(
                painter = painterResource(Res.drawable.ic_bottom_bg), // your background image
                contentDescription = "Bottom Bar Background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()

                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            )
            {
                navItems.forEachIndexed { index, item ->

                    if (index == 2) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .weight(1f)

                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    selectedIndex = index
                                    homeNewRoute(DashboardSubScreen.LaunchHomeScreenFlow.route)
                                }
                        ) {
                            // Circle button
                            Card(
                                modifier = Modifier
                                    .size(70.dp)
                                    .offset(y = (-32).dp) // float only the button
                                    .border(
                                        width = 5.dp,
                                        color = White,
                                        shape = CircleShape
                                    ),
                                shape = CircleShape,
                                backgroundColor = Color.Transparent,
                            ) {
                                Box(
                                    modifier = Modifier.background(selectedTabbarClr, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(item.selectedIcon),
                                        contentDescription = item.name,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }

                            // Home text - push it up so it aligns with others
                            Text(
                                text = item.name,
                                color = if (selectedIndex == index) selectedTabbarClr else Black,
                                style = NavigationButtonText(),
                                modifier = Modifier.offset(y = (-16).dp) // 🔥 adjust only text
                            )
                        }
                    }

                    else {
                        // Other icons
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .weight(1f)
                                .offset(y = 5.dp)
                                .padding(top = 16.dp)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    selectedIndex = index
                                    when (index) {
                                         0 -> homeNewRoute(DashboardSubScreen.LeaderBoardScreenFlow.route)
                                          1 -> homeNewRoute(DashboardSubScreen.CategoryScreen.route)
                                          3 -> homeNewRoute(DashboardSubScreen.NotificationScreen.route)
                                          4 -> homeNewRoute(DashboardSubScreen.AccountScreenFlow.route)
                                    }
                                }
                        ) {
                            Image(
                                painter = painterResource(
                                    item.unSelectedIcon
                                ),
                                contentDescription = item.name,
                                colorFilter = ColorFilter.tint(
                                    if (selectedIndex == index) selectedTabbarClr else Black
                                ),
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = item.name,
                                color = if (selectedIndex == index) selectedTabbarClr else Black,
                                style = NavigationButtonText()
                            )
                        }
                    }
                }
            }
        }
    }
}

data class SingleNavigationItem(
    var name: String,
    var selectedIcon: DrawableResource,
    var unSelectedIcon: DrawableResource,
)
