package com.lessonmaker.app.mainScreen.category.subCategory

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lessonmaker.app.common.bottomSheets.BottomSheetCommonInterface
import com.lessonmaker.app.common.dialogue.grade.GradeDialogue
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.category.Category
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.InputFieldBorderClr
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize15
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize24
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bold
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.athleticd_img
import lessonmaker.composeapp.generated.resources.badminton_img
import lessonmaker.composeapp.generated.resources.baseBall_img
import lessonmaker.composeapp.generated.resources.basketball_img
import lessonmaker.composeapp.generated.resources.cricket_img
import lessonmaker.composeapp.generated.resources.golf_img
import lessonmaker.composeapp.generated.resources.gymnastics_img
import lessonmaker.composeapp.generated.resources.hocky_img
import lessonmaker.composeapp.generated.resources.no_quizfound_icon
import lessonmaker.composeapp.generated.resources.scocer_img
import lessonmaker.composeapp.generated.resources.selected_check
import lessonmaker.composeapp.generated.resources.swiming_img
import lessonmaker.composeapp.generated.resources.tennis_img
import lessonmaker.composeapp.generated.resources.un_selected_check
import lessonmaker.composeapp.generated.resources.volleyBall_img

@Composable
fun SubCategoryScreen(dashboardViewModel: DashboardViewModel,
                      dashboardRoutes: (String) -> Unit
)
{
    var all by remember { mutableStateOf(true) }
    var seventh by remember { mutableStateOf(false) }
    var six by remember { mutableStateOf(false) }
    var applyFilter by remember { mutableStateOf(false) }
    var filterApplied by remember { mutableStateOf(false) }
    var selectedGrade by remember { mutableStateOf("All") }
    val list = listOf(
        Category("Soccer", Res.drawable.scocer_img),
        Category("Cricket", Res.drawable.cricket_img),
        Category("Tennis", Res.drawable.tennis_img),
        Category("Basketball", Res.drawable.basketball_img),
        Category("Volleyball", Res.drawable.volleyBall_img),
        Category("Hockey", Res.drawable.hocky_img),
        Category("Badminton", Res.drawable.badminton_img),
        Category("Swimming", Res.drawable.swiming_img),
        Category("Golf", Res.drawable.golf_img),
        Category("Baseball", Res.drawable.baseBall_img),
        Category("Gymnastics", Res.drawable.gymnastics_img),
        Category("Athletics", Res.drawable.athleticd_img)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    )
    {
        Spacer(modifier = Modifier.height(15.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 12.dp)
                .clickable(interactionSource = MutableInteractionSource(),
                    indication = null
                )
                {

                }
                .border(
                    width = 1.dp,
                    color = White,
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp,

            )
        {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(interactionSource = MutableInteractionSource(),
                        indication = null
                    )
                    {
                        applyFilter=true
                    }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {

                Text(
                    text = "Select Grade",
                    color = White,
                    style = TextSize14().Medium()
                )
                Text(
                    text = if(selectedGrade=="All") selectedGrade else "Grade ${selectedGrade}",
                    color = White,
                    style = TextSize16().bold()
                )

            }
        }
       // Spacer(modifier = Modifier.height(10.dp))
        if(selectedGrade=="All")
        {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    list.chunked(3).forEach { rowItems ->


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            rowItems.forEach { category ->

                                Column (modifier = Modifier
                                    .weight(1f)
                                    .padding(bottom = 18.dp)
                                    .clickable(interactionSource = MutableInteractionSource(),
                                        indication = null)
                                    {
                                        dashboardRoutes.invoke(DashboardSubScreen.SubCategoryDetailScreen.route)

                                    }
                                )
                                {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(94.dp)

                                            .border(
                                                width = 1.dp,
                                                color = InputFieldBorderClr,
                                                shape = RoundedCornerShape((8).dp)
                                            ),
                                        shape = RoundedCornerShape((8).dp),
                                        backgroundColor = White,
                                        elevation = 10.dp
                                    )
                                    {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        )
                                        {
                                            Image(
                                                painter = painterResource(category.img),
                                                contentDescription = category.type,
                                                modifier = Modifier
                                                ,
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = category.type,
                                            color = White,
                                            style = TextSize14().bold()
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(5.dp))
                                }


                            }
                            repeat(3 - rowItems.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

        }
        else
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Spacer(modifier = Modifier.height(100.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                )
                {
                    Image(painter = painterResource(Res.drawable.no_quizfound_icon),
                        contentDescription = "N0 Quiz found")


                }
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "No Quizz Found!",
                    color = White,
                    style = TextSize24().bold()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Vivamus rhoncus ipsum turpis, a hendrerit leo vulputate quis. Praesent sed ex quam.",
                    color = White,
                    style = TextSize15().Medium().copy(
                        lineHeight = 24.sp
                    ),
                    textAlign = TextAlign.Center
                )



            }

        }




    }
    if (applyFilter){
        GradeDialogue(
            bottomSheetCommonInterface= object : BottomSheetCommonInterface {
                override fun closeSheet() {
                    applyFilter = false
                }

                override fun moveNextScreen() {
                    applyFilter = false
                    filterApplied= true
                }

            },
            onGradeSelected = {selectedGrade=it}

        )
    }

}
@Composable
private fun classSelection(
    label: String,
    enabled: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { onToggle() }
        ) {
            Image(
                painter = painterResource(if (enabled) Res.drawable.selected_check else Res.drawable.un_selected_check),
                contentDescription = "toggle",
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = label,
            color = Black,
            style = TextSize14().Medium()
        )

    }
}
