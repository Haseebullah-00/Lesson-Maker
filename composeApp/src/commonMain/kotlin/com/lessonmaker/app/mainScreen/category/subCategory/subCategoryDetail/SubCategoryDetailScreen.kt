package com.lessonmaker.app.mainScreen.category.subCategory.subCategoryDetail

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.home.Trending
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.borderClr
import com.lessonmaker.app.theme.selectedTabbarClr
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.arrow_right
import lessonmaker.composeapp.generated.resources.bright_mind_img
import lessonmaker.composeapp.generated.resources.explore_footbal_img
import lessonmaker.composeapp.generated.resources.trending1
import lessonmaker.composeapp.generated.resources.trending2
import lessonmaker.composeapp.generated.resources.trending3
import lessonmaker.composeapp.generated.resources.world_cup_img

@Composable
fun SubCategoryDetailScreen(dashboardViewModel: DashboardViewModel,
                            dashboardRoutes: (String) -> Unit
)
{
    val list1=listOf(
        Trending("World Cup Quizzzz","Sports• 2.6k Attendees",Res.drawable.world_cup_img),
        Trending("The Bright Minds Scoocer Quiz","Sports • 2.6k Attendees",Res.drawable.bright_mind_img),
        Trending("Explore the Football of Science!","Science • 2.6k Attendees",Res.drawable.explore_footbal_img),
        Trending("World Cup Quizzzz","Sports• 2.6k Attendees",Res.drawable.world_cup_img),
        Trending("The Bright Minds Scoocer Quiz","Sports • 2.6k Attendees",Res.drawable.bright_mind_img),
        Trending("Explore the Football of Science!","Science • 2.6k Attendees",Res.drawable.explore_footbal_img),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    )
    {
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            item {
                    showSubCategoryDetail(list1, onClick = {dashboardRoutes.invoke(DashboardSubScreen.QuizDetailScreen.route)})
            }
        }
    }

}
@Composable
fun showSubCategoryDetail(
    list: List<Trending>,
    onClick:()-> Unit
)
{
    list.forEach { item->
        Card(
            modifier = Modifier

                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onClick.invoke()
                }

                .border(
                    width = 1.dp,
                    color = borderClr,
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = White,
            elevation = 0.dp,

            )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Row(
                    modifier = Modifier
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                )
                {
                    Image(
                        painter = painterResource(item.img),
                        contentDescription = item.type,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center

                    )
                    {
                        Text(
                            text = item.type,
                            color = Black,
                            style = TextSize14().Medium()
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = item.detail,
                            color = Black,
                            style = TextSize12().Regular()
                        )


                    }

                }
                Card(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {

                        }

                        .border(
                            width = 0.dp,
                            color = selectedTabbarClr,
                            shape = CircleShape
                        ),
                    shape = CircleShape,
                    backgroundColor = selectedTabbarClr,
                )
                {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Image(painter = painterResource(Res.drawable.arrow_right),
                            contentDescription = "Arrow Right",
                        )


                    }

                }



            }

        }
        Spacer(modifier = Modifier.height(10.dp))

    }

}