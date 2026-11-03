

package com.lessonmaker.app.mainScreen.bottomNavigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.di.SharedPreferenceManager
import com.russhwolf.settings.Settings
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.*
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.NavigationButtonText
import com.lessonmaker.app.theme.dataCellInfoBoxBg
import com.lessonmaker.app.theme.greyE3
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.selectedTabbarClr

data class SingleNavigationItem(
    val name: String,
    val icon: DrawableResource,
    val route: String,
)


private fun studentTabs() = listOf(
    SingleNavigationItem("My Courses", Res.drawable.my_courses_unseleted, DashboardSubScreen.StudentMyCoursesScreenFLow.route),
    SingleNavigationItem("Profile", Res.drawable.profile_unselected, DashboardSubScreen.StudentProfileScreenFlow.route),
)


private fun teacherTabs() = listOf(
    SingleNavigationItem("Courses", Res.drawable.my_courses_unseleted, DashboardSubScreen.TeacherMyCoursesScreenFlow.route),
    SingleNavigationItem("Requests", Res.drawable.requests_unselected, DashboardSubScreen.TeacherRequestsScreenFlow.route),
    SingleNavigationItem("Profile", Res.drawable.profile_unselected, DashboardSubScreen.TeacherProfileScreenFlow.route),
)

@Composable
fun NavigationWidget(
    navigationViewModel: NavigationViewModel,
    homeNewRoute: (String) -> Unit,
    currentRoute: String? = null
) {
    val sharedPref = remember { SharedPreferenceManager(Settings()) }


    val isTeacher = sharedPref.isTeacherLoggedIn
    val navItems = remember(isTeacher) { if (isTeacher) teacherTabs() else studentTabs() }


    var selectedIndex by remember(isTeacher) { mutableStateOf(0) }
    LaunchedEffect(currentRoute, navItems) {
        val index = navItems.indexOfFirst { it.route == currentRoute }
        if (index >= 0) selectedIndex = index
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(greyE3)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEachIndexed { index, item ->
                val selected = selectedIndex == index
                val color = if (selected) selectedTabbarClr else loginTabUnselectedTxtClr

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            selectedIndex = index
                            homeNewRoute(item.route)
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .width(52.dp)
                            .height(28.dp)
                            .clip(RoundedCornerShape(50))
                            .background(if (selected) dataCellInfoBoxBg else White),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(item.icon),
                            contentDescription = item.name,
                            colorFilter = ColorFilter.tint(color),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.name,
                        color = color,
                        style = NavigationButtonText()
                    )
                }
            }
        }
    }
}
