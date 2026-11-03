package com.lessonmaker.app.mainScreen.studentFlow.sideMenu


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.studentFlow.StudentDummyStore
import com.lessonmaker.app.mainScreen.teacherFlow.SingleSideMenu
import com.lessonmaker.app.mainScreen.teacherFlow.profile.InitialsAvatar
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.boldExtraBold
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.fieldIcon
import com.lessonmaker.app.theme.grey94
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.book_ic
import lessonmaker.composeapp.generated.resources.edit_ic_new
import org.jetbrains.compose.resources.painterResource

@Composable
fun StudentSideMenuScreen(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {
    val studentName = remember {
        runCatching { dashboardViewModel.sharedPreferenceManager.getStudent()?.name }
            .getOrNull()?.takeIf { it.isNotBlank() } ?: ""
    }
    val menu = listOf(SingleSideMenu("Edit Access", Res.drawable.edit_ic_new))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .statusBarsPadding()
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(40.dp).background(fieldCursor, RoundedCornerShape(10.dp)),
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
            Text(text = "Lesson Maker", color = bgColor, style = TextSize18().boldExtraBold())
        }
        Spacer(Modifier.height(14.dp))
        Text(text = "STUDENT WORKSPACE", color = grey94, style = TextSize12().semiBold())
        Spacer(Modifier.height(12.dp))
        HorizontalDivider(color = fieldBorder, thickness = 1.dp)
        Spacer(Modifier.height(12.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
            items(menu) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            dashboardRoutes.invoke(DashboardSubScreen.StudentEditAccessScreenFlow.route)
                        }
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
                    Text(text = item.title, color = bgColor, style = TextSize14().semiBold())
                }
            }
        }

        HorizontalDivider(color = fieldBorder, thickness = 1.dp)
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InitialsAvatar(name = studentName, size = 32.dp)
            Spacer(Modifier.width(10.dp))
            Column {
                Text(text = studentName, color = bgColor, style = TextSize13().bold())
                Text(text = "Student account", color = loginTabUnselectedTxtClr, style = TextSize12().Regular())
            }
        }
    }
}