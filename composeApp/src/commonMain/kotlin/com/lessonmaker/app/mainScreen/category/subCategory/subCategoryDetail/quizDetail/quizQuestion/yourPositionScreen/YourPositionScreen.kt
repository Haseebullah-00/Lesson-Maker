package com.lessonmaker.app.mainScreen.category.subCategory.subCategoryDetail.quizDetail.quizQuestion.yourPositionScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lessonmaker.app.common.MainButton
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.navigationControler.Screen
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.Gray
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize10
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize15
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.TextSize24
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.banner2Clr
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.buttonBackClr
import com.lessonmaker.app.theme.hintClr
import com.lessonmaker.app.theme.semiBold
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.medal_img
import lessonmaker.composeapp.generated.resources.position1
import lessonmaker.composeapp.generated.resources.trophy_icon

@Composable
fun YourPositionScreen(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
)
{
    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
        )
        {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.dp)
                    .weight(1f)
                    .padding(horizontal = 12.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center

                )
                {
                    Image(painter = painterResource(Res.drawable.trophy_icon),
                        contentDescription = "Trophy",
                        )

                }
                Spacer(modifier = Modifier.height(38.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center

                )
                {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    )
                    {
                        Text(
                            text = "World Class Talent",
                            color = White,
                            style = TextSize24().bold(),

                            )


                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center

                )
                {
                    Text(
                        text = "Our knowledge of the beautiful game is incredible! You know your Maradona from your Messi.",
                        color = White,
                        style = TextSize14().Regular().copy(
                            lineHeight = 23.sp
                        ),
                        textAlign = TextAlign.Center

                        )

                }
                Spacer(modifier = Modifier.height(38.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center

                )
                {
                    Text(
                        text = "Your Position",
                        color = White,
                        style = TextSize16().bold(),
                        textAlign = TextAlign.Center

                    )

                }
                Spacer(modifier = Modifier.height(16.dp))
                yourPositionCard()



            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )
            {
                MainButton(buttonBackClr,
                    Color.Transparent,
                    "Overall Leaderboard ",
                    60.dp,
                    0.dp,
                    onClick = {
                       dashboardRoutes.invoke(DashboardSubScreen.LeaderBoardScreenFlow.route)


                    }
                )

            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}
@Composable
fun yourPositionCard()
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(67.dp)
            ,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = banner2Clr,
        elevation = 0.dp
    ) {


         Row(
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(horizontal = 12.dp),
             verticalAlignment = Alignment.CenterVertically,
             horizontalArrangement = Arrangement.SpaceBetween
         )
         {
             Row(
                 verticalAlignment = Alignment.CenterVertically,
                 horizontalArrangement = Arrangement.spacedBy(12.dp)
             )
             {
                 Text(
                     text = "4",
                     color = White,
                     style = TextSize14().Medium()
                 )
                 Image(
                     painter = painterResource(Res.drawable.position1),
                     contentDescription = "progile",
                     modifier = Modifier.size(40.dp)
                 )
                 Column(
                     verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                 )
                 {
                     Text(
                         text = "Angelina Tuff",
                         color = White,
                         style = TextSize16().semiBold()
                     )
                     Spacer(modifier = Modifier.height(4.dp))
                     Text(
                         text = "@serenathebird",
                         color = White.copy(alpha = 0.8f),
                         style = TextSize10().Regular()
                     )
                 }
             }
             Row(
                 horizontalArrangement = Arrangement.spacedBy(5.dp)
             )
             {
                 Image(painter = painterResource(Res.drawable.medal_img),
                     contentDescription = "Position",
                     modifier = Modifier.size(18.dp)
                 )
                 Text(
                     text = "889 XP",
                     color = White,
                     style = TextSize16().bold()
                 )
             }
         }









    }
}