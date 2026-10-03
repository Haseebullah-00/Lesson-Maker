package com.lessonmaker.app.mainScreen.category.subCategory.subCategoryDetail.quizDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lessonmaker.app.common.MainButton
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.account.privacyAndPolicy.Heading
import com.lessonmaker.app.mainScreen.account.privacyAndPolicy.SubHeading
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize15
import com.lessonmaker.app.theme.TextSize24
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.banner2Clr
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.buttonBackClr
import com.lessonmaker.app.theme.grey59
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.quiz_detail_banner_dummy1
import lessonmaker.composeapp.generated.resources.quiz_detail_banner_dummy2
import lessonmaker.composeapp.generated.resources.video_pause_icon

@Composable
fun QuizDetailScreen(dashboardViewModel: DashboardViewModel,
                     dashboardRoutes: (String) -> Unit)
{
    val headings = listOf(
        Heading(1, "Embark on a journey through over 90 years of World Cup history! This isn't just any soccer quiz; it's a challenge for true connoisseurs of the beautiful game.","What can you expect?"),
        Heading(2, "Grab your virtual jersey, lace up your mental boots, and see if you have the knowledge to be crowned champion of the World Cup Quizzzz!","What can you expect?")
    )
    val subHeadings = listOf(
        SubHeading(1, "The Golden Era: Questions about the legends who defined the sport—Pele, Beckenbauer, Cruyff, and Zidane."),
        SubHeading(1, "Modern Maestros: Test your knowledge on the recent greats like Messi, Ronaldo, and the stars of today."),
        SubHeading(1,"Iconic Moments: From the \"Miracle of Bern\" to \"The Hand of God\" and beyond. Do you remember what happened and when?"),
        SubHeading(1,"Hosts & Winners: Which nation has won the most titles? Who shocked the world as a host?"),
        SubHeading(1,"The Numbers Game: Questions about record goalscorers, biggest wins, and unbelievable stats."),

        SubHeading(2, "The Golden Era: Questions about the legends who defined the sport—Pele, Beckenbauer, Cruyff, and Zidane."),
        SubHeading(2, "Modern Maestros: Test your knowledge on the recent greats like Messi, Ronaldo, and the stars of today."),
        SubHeading(2,"Iconic Moments: From the \"Miracle of Bern\" to \"The Hand of God\" and beyond. Do you remember what happened and when?"),
        SubHeading(2,"Hosts & Winners: Which nation has won the most titles? Who shocked the world as a host?"),
        SubHeading(2,"The Numbers Game: Questions about record goalscorers, biggest wins, and unbelievable stats."),
    )
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
                QuizDetailBanner(function = {})
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 60.dp)
                )
                {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 0.dp,
                                color = White,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        shape = RoundedCornerShape(12.dp),
                        backgroundColor = White,
                        elevation = 0.dp,

                        )
                    {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 20.dp)
                        )
                        {
                            Text(
                                text = "World Cup Quizzzz",
                                color = Black,
                                style = TextSize24().bold()
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Sports • 2.6k Attendees",
                                color = Black,
                                style = TextSize15().Medium()
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            headings.forEach { heading ->
                                Text(
                                    text = heading.title,
                                    color = grey59,
                                    style = TextSize13().Regular().copy(
                                        lineHeight = 22.sp
                                    ),
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = heading.description,
                                    color = grey59,
                                    style = TextSize13().Regular().copy(
                                        lineHeight = 22.sp
                                    ),

                                    )
                                Spacer(modifier = Modifier.height(5.dp))
                                subHeadings.filter { it.id==heading.id  }.forEach { sub->
                                    Row (
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp),
                                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                                    )
                                    {
                                        androidx.compose.material.Text(
                                            text = "•",
                                            color = grey59,
                                            style = TextSize13().Regular()
                                        )
                                        androidx.compose.material.Text(
                                            text = sub.text,
                                            color = grey59,
                                            style = TextSize13().Regular().copy(
                                                lineHeight = 22.sp
                                            ),
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                            }

                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 2.dp)
                            .align(Alignment.BottomCenter)
                            .offset(y=50.dp)
                    )
                    {
                        MainButton(buttonBackClr,
                            Color.Transparent,
                            "Participate This Quizz",
                            60.dp,
                            0.dp,
                            onClick = {
                                dashboardRoutes.invoke(DashboardSubScreen.QuizQuestionsScreen.route)

                            }
                        )

                    }

                }




            }





            Spacer(modifier = Modifier.height(10.dp))
        }
    }

}
@Composable
fun QuizDetailBanner(function: () -> Unit) {
    val images = listOf(
        QuizDetail(Res.drawable.quiz_detail_banner_dummy2,true),
        QuizDetail(Res.drawable.quiz_detail_banner_dummy1,false),
        QuizDetail(Res.drawable.quiz_detail_banner_dummy1,false),
        QuizDetail(Res.drawable.quiz_detail_banner_dummy1,false),

        )

    val pagerState = rememberPagerState(pageCount = { images.size })

    Column {
        Column(
            modifier = Modifier.fillMaxWidth()
                .height(270.dp)
        ) {
            // Pager
            androidx.compose.material3.Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(235.dp)
                    .border(
                        width = 0.dp,
                        color = Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    )

            ) {
                HorizontalPager(
                    state = pagerState, modifier = Modifier.fillMaxSize().border(
                        width = 0.dp, color = Transparent, // Light purple border
                        shape = RoundedCornerShape(10.dp)
                    )
                ) { page ->
                    val item = images[page]
                    if (item.video) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        {
                            Image(
                                painter = painterResource(resource = item.img),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxSize().border(
                                    width = 0.dp, color = Transparent, // Light purple border
                                    shape = RoundedCornerShape(10.dp)
                                )
                            )
                            Image(
                                painter = painterResource(Res.drawable.video_pause_icon),
                                contentDescription = "Video on off",
                                modifier = Modifier
                                    .size(48.dp)
                                    .align(Alignment.Center)
                            )

                        }
                    } else {
                        Image(
                            painter = painterResource(resource = item.img),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxSize().border(
                                width = 0.dp, color = Transparent, // Light purple border
                                shape = RoundedCornerShape(10.dp)
                            )
                        )

                    }

                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // Indicators on top
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                repeat(images.size) { index ->
                    Box(
                        modifier = Modifier.padding(horizontal = 2.dp)
                            .size(12.dp)
                            .background(
                                if (pagerState.currentPage == index) banner2Clr else White,
                                shape = RoundedCornerShape(3.dp)
                            )
                    )
                }
            }
        }
    }
}
data class QuizDetail(
    val img: DrawableResource,
    val video: Boolean
)