package com.lessonmaker.app.mainScreen.category.subCategory.subCategoryDetail.quizDetail.quizQuestion

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lessonmaker.app.common.MainButton
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize17
import com.lessonmaker.app.theme.TextSize24
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.banner2Clr
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.borderClr
import com.lessonmaker.app.theme.buttonBackClr
import com.lessonmaker.app.theme.optionClr

import com.lessonmaker.app.theme.progressBarClr1
import com.lessonmaker.app.theme.progressBarClr2
import com.lessonmaker.app.theme.semiBold
import okio.Options
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.arrow_right
import lessonmaker.composeapp.generated.resources.selected_icon
import lessonmaker.composeapp.generated.resources.un_selected_icon

@Composable
fun QuizQuestionsScreen(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
)
{
    val list = listOf(
        Option("A","Ronaldo"),
        Option("B","Zice"),
        Option("C","Palley"),
        Option("D","Messi")
    )
    var currentQuestion by remember { mutableStateOf(0) }
    val totalQuestions = 15

    // Always re-calculated when currentQuestion changes
    val progress = currentQuestion.toFloat() / totalQuestions.toFloat()
    val progressPercentage = (100)-((progress * 100).toInt())
    var selectedOption by remember { mutableStateOf<String?>("C") }


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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {

                    Text(
                        text = "your progress",
                        color = White,
                        style = TextSize12().Regular()
                    )

                    Text(
                        text = "${progressPercentage}% to complete",
                        color = White,
                        style = TextSize16().bold()
                    )



                }
                Spacer(modifier = Modifier.height(10.dp))
                RectangleProgressBar(progress = progress)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {

                    Text(
                        text = if(currentQuestion>9) {"your Question: ${currentQuestion}"} else{"your Question: 0${currentQuestion}"},
                        color = White,
                        style = TextSize16().bold()
                    )

                    Text(
                        text = if(currentQuestion>9) {"${currentQuestion}/${totalQuestions}"} else{"0${currentQuestion}/${totalQuestions}"},
                        color = White,
                        style = TextSize16().bold()
                    )



                }
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                       ,
                    contentAlignment = Alignment.Center
                )
                {
                    Text(
                        text = if(currentQuestion==15) {"Which Brazilian legend is the only player to have won the World Cup three times?" }
                        else {"Who scored the infamous \"Hand of God\" goal and then the \"Goal of the Century\" just minutes later in the same 1986 World Cup match?"} ,
                        color = White,
                        style = TextSize24().bold().copy(
                            lineHeight = 33.sp

                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                }
                Spacer(modifier = Modifier.height(20.dp))

                questionOption(
                    list = list,
                    selectedOption = selectedOption,
                    onOptionSelected = { option ->
                        selectedOption = option
                    }
                )


            }
            if(currentQuestion==15)
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )
                {
                    MainButton(buttonBackClr,
                        Color.Transparent,
                        "Completed",
                        60.dp,
                        0.dp,
                        onClick = {
                            dashboardRoutes.invoke(DashboardSubScreen.YourPositionScreen.route)

                        }
                    )

                }

            }
            else
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )
                {
                    MainButton(buttonBackClr,
                        Color.Transparent,
                        "Continue",
                        60.dp,
                        0.dp,
                        onClick = {
                            currentQuestion++
                        }
                    )

                }

            }




            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
@Composable
fun RectangleProgressBar(progress: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(15.dp) // fixed height
            .background(progressBarClr1, shape = RoundedCornerShape(30.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.coerceIn(0f, 1f)) // keep it between 0..1
                .background(progressBarClr2, shape = RoundedCornerShape(30.dp))
        )
    }

}
@Composable
fun questionOption(
    list: List<Option>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
)
{
    list.forEach {  item->
        val isSelected = item.option == selectedOption
        Card(
            modifier = Modifier
                .fillMaxWidth()

                .height(54.dp)
                .border(
                    width = 1.dp,
                    color = borderClr,
                    shape = RoundedCornerShape(12.dp)

                )
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                )
                {
                    onOptionSelected(item.option)

                },
            shape = RoundedCornerShape(12.dp),
            backgroundColor = White,
            elevation = 0.dp
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Row(
                    modifier = Modifier
                      ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                )
                {
                    Text(
                        text = "${item.option})",
                        color = optionClr,
                        style = TextSize17().bold()
                    )
                    Text(
                        text = item.chose,
                        color = Black,
                        style = TextSize16().semiBold()
                    )

                }
                Image(
                    painter = painterResource(if(isSelected)Res.drawable.selected_icon else Res.drawable.un_selected_icon),
                    contentDescription = "Selection",
                )

            }
        }
        Spacer(modifier = Modifier.height(15.dp))
    }

}
data class Option(

    val option: String,
    val chose: String

    )

