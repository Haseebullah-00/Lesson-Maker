//package com.app.sensys.mainScreen.sideBar
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ColumnScope
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.asPaddingValues
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.navigationBars
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Card
//import androidx.compose.material.DrawerDefaults
//import androidx.compose.material.Text
//import androidx.compose.material3.DrawerState
//import androidx.compose.material3.DrawerValue
//import androidx.compose.material3.ModalNavigationDrawer
//import androidx.compose.material3.rememberDrawerState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.CompositionLocalProvider
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.ColorFilter
//import androidx.compose.ui.platform.LocalLayoutDirection
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.LayoutDirection
//import androidx.compose.ui.unit.dp
//import com.lessonmaker.app.theme.PrimaryColor
//import com.lessonmaker.app.theme.White
//
//
//import org.jetbrains.compose.resources.painterResource
//
//
//@Composable
//fun TeacherSideMenuScreen(navigation: (String) -> Unit) {
//    Column(
//        modifier =
//            Modifier
//                .background(White)
//    ) {
//
//        Column(
//            modifier =
//                Modifier
//                    .statusBarsPadding()
//                    .padding(WindowInsets.navigationBars.asPaddingValues())
//        ) {
//            Box(modifier = Modifier.fillMaxWidth()) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Center,
//                ) {
//                    Image(
//                        modifier = Modifier,
//                        painter = painterResource(resource = Res.drawable.ic_side_menu_icon),
//                        contentDescription = "Background"
//                    )
//
//                }
//                Image(
//                    modifier = Modifier.align(Alignment.TopEnd)
//                        .padding(end = 15.dp)
//                        .clickable(
//                            interactionSource = MutableInteractionSource(),
//                            indication = null
//                        ) {
//                            navigation.invoke("close")
//                        },
//                    painter = painterResource(resource = Res.drawable.ic_close_side_menu),
//                    contentDescription = "Background",
//                )
//            }
//
//
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            val newMenu = arrayListOf(
//                SingleSideMenu(
//                    "About Us",
//                    DashboardSubScreen.LaunchAboutUsScreenFlow.createRoute("1")
//
//                ),
//                SingleSideMenu(
//                    "Terms and Conditions",
//                    DashboardSubScreen.TermsAndConditionScreen.route
//
//                ),
//                SingleSideMenu(
//                    "Privacy Policy",
//                    DashboardSubScreen.LaunchPrivacyPolicyScreenFlow.route
//
//                ),
//                SingleSideMenu(
//                    "FAQs",
//                    DashboardSubScreen.LaunchFAQScreenFlow.route
//
//                ),
//                SingleSideMenu(
//                    "Contact Us",
//                    DashboardSubScreen.LaunchContactUsScreenFlow.route
//
//                ),
//            )
//
//
//
//
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(0.dp)
//                    .weight(1f),
//                contentPadding = PaddingValues(bottom = 50.dp),
//                verticalArrangement = Arrangement.spacedBy(10.dp)
//            ) {
//                items(newMenu) {
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(60.dp)
//                            .padding(horizontal = 12.dp)
//                            .border(
//                                width = 1.dp,
//                                color = inputFilterBackgroundColor,
//                                shape = RoundedCornerShape(10.dp)
//                            ),
//                        shape = RoundedCornerShape(10.dp),
//                        backgroundColor = inputFilterBackgroundColor,
//                        elevation = 0.dp,
//
//                        )
//                    {
//
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .fillMaxHeight()
//                                .padding(horizontal = 16.dp)
//                                .clickable(interactionSource = null, indication = null) {
//                                    navigation.invoke(it.newScreenRoute)
//                                },
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.spacedBy(10.dp)
//                        )
//                        {
//
//
//                            Column(
//                                modifier = Modifier.width(0.dp).weight(1f)
//                            ) {
//                                Text(
//                                    text = it.title,
//                                    color = White,
//                                    style = TextSize16().bold(),
//                                )
//                            }
//                            Box(
//                                modifier = Modifier,
//                                contentAlignment = Alignment.Center
//                            ){
//                                Image(
//                                    painter = painterResource(Res.drawable.ic_next_arrow),
//                                    contentDescription = "Arrow Right",
//                                    modifier = Modifier,
//                                    colorFilter = ColorFilter.tint(Color.White)
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//
//
//            Text(
//                text = "Version 1.1",
//                color = TextColorE2,
//                style = TextSize15().boldLight(),
//                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
//                textAlign = TextAlign.Center
//            )
//
//
//        }
//    }
//}
//
//
//data class SingleSideMenu(
//    val title: String, val newScreenRoute: String
//)
//
//
//@Composable
//fun RightModalDrawer(
//    modifier: Modifier = Modifier,
//    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
//    gesturesEnabled: Boolean = true,
//    scrimColor: Color = DrawerDefaults.scrimColor,
//    drawerContent: @Composable ColumnScope.() -> Unit,
//    content: @Composable () -> Unit,
//) {
//    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
//        ModalNavigationDrawer(
//            modifier = modifier,
//            drawerState = drawerState,
//            gesturesEnabled = gesturesEnabled || drawerState.isOpen,
//            scrimColor = scrimColor,
//            drawerContent = {
//                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
//
//                    Column(
//                        modifier = Modifier.fillMaxWidth(0.85f),
//                        content = drawerContent
//                    )
//                }
//            },
//            content = {
//                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
//                    content()
//                }
//            },
//        )
//
//    }
//
//}


package com.lessonmaker.app.mainScreen.teacherFlow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.theme.REdF14336
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.boldExtraBold
import com.lessonmaker.app.theme.dataCellInfoBoxBg
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.fieldIcon
import com.lessonmaker.app.theme.grey94
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.book_ic

import lessonmaker.composeapp.generated.resources.ic_archived
import lessonmaker.composeapp.generated.resources.ic_colleagues_courses
import lessonmaker.composeapp.generated.resources.ic_notes_approvals
import lessonmaker.composeapp.generated.resources.ic_allow_students
import lessonmaker.composeapp.generated.resources.ic_allowed_students
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class SingleSideMenu(
    val title: String,
    val newScreenRoute: String,
    val icon: DrawableResource,
    val badgeCount: Int = 0
)

@Composable
fun TeacherSideMenuScreen(
    teacherName: String = "Dr. Adnan",
    navigation: (String) -> Unit
) {

    val menu = listOf(
        SingleSideMenu("Archived", "archived", Res.drawable.ic_archived),
        SingleSideMenu("Colleagues' Courses", "colleagues_courses", Res.drawable.ic_colleagues_courses),
        SingleSideMenu("Notes Approvals", "notes_approvals", Res.drawable.ic_notes_approvals, badgeCount = 2),
        SingleSideMenu("Allow Students", "allow_students", Res.drawable.ic_allow_students),
        SingleSideMenu("Allowed Students", "allowed_students", Res.drawable.ic_allowed_students),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .statusBarsPadding()
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(24.dp))

        // Logo + app name
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(fieldCursor, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.book_ic),
                    contentDescription = "Logo",
                    modifier = Modifier.size(22.dp),
                    colorFilter = ColorFilter.tint(White)
                )
            }
            Spacer(Modifier.width(10.dp))
            Text(
                text = "Lesson Maker",
                color = bgColor,
                style = TextSize18().boldExtraBold()
            )
        }

        Spacer(Modifier.height(14.dp))
        Text(
            text = "TEACHER WORKSPACE",
            color = grey94,
            style = TextSize12().semiBold()
        )
        Spacer(Modifier.height(12.dp))
        HorizontalDivider(color = fieldBorder, thickness = 1.dp)
        Spacer(Modifier.height(12.dp))

        // Menu items
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(menu) { item ->
                SideMenuItem(item = item, onClick = { navigation.invoke(item.newScreenRoute) })
            }
        }

        // Profile footer
        HorizontalDivider(color = fieldBorder, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(dataCellInfoBoxBg, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = teacherName.toInitials(),
                    color = fieldCursor,
                    style = TextSize12().bold()
                )
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(text = teacherName, color = bgColor, style = TextSize13().bold())
                Text(
                    text = "Teacher account",
                    color = loginTabUnselectedTxtClr,
                    style = TextSize12().Regular()
                )
            }
        }
    }
}

@Composable
private fun SideMenuItem(item: SingleSideMenu, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(item.icon),
            contentDescription = item.title,
            modifier = Modifier.size(18.dp),
            colorFilter = ColorFilter.tint(fieldIcon)
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = item.title,
            color = bgColor,
            style = TextSize14().semiBold(),
            modifier = Modifier.weight(1f)
        )
        if (item.badgeCount > 0) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .background(REdF14336, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.badgeCount.toString(),
                    color = Color.White,
                    style = TextSize12().bold()
                )
            }
        }
    }
}

private fun String.toInitials(): String =
    split(" ", ".")
        .filter { it.isNotBlank() && it.lowercase() != "dr" }
        .take(2)
        .joinToString("") { it.first().uppercase() }
        .ifEmpty { "T" }

@Composable
fun RightModalDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    gesturesEnabled: Boolean = true,
    scrimColor: Color = DrawerDefaults.scrimColor,
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        ModalNavigationDrawer(
            modifier = modifier,
            drawerState = drawerState,
            gesturesEnabled = gesturesEnabled || drawerState.isOpen,
            scrimColor = scrimColor,
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.85f),
                        content = drawerContent
                    )
                }
            },
            content = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    content()
                }
            },
        )
    }
}
