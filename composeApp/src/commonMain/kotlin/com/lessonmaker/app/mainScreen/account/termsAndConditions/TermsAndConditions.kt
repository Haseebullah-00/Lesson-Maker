package com.lessonmaker.app.mainScreen.account.termsAndConditions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.account.privacyAndPolicy.Heading
import com.lessonmaker.app.mainScreen.account.privacyAndPolicy.SubHeading
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize15
import com.lessonmaker.app.theme.TextSize24
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.grey59

@Composable
fun TermsAndCondition(dashboardViewModel: DashboardViewModel,
                     dashboardRoutes: (String) -> Unit
)
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        )
        {
            Spacer(modifier = Modifier.height(20.dp))
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
                        text = "Terms And Conditions",
                        color = Black,
                        style = TextSize24().bold()
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "12 Sep 2025 - 10:30 AMSign ",
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
                                Text(
                                    text = "•",
                                    color = grey59,
                                    style = TextSize13().Regular()
                                )
                                Text(
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
            Spacer(modifier = Modifier.height(30.dp))
        }

    }
}
