package com.lessonmaker.app.mainScreen.leaderBoard

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.bottomSheets.BottomSheetCommonInterface
import com.lessonmaker.app.common.dialogue.filter.FilterDialogue
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.home.Position
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.Gray
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize10
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize15
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.banner2Clr
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.hintClr
import com.lessonmaker.app.theme.profileBorderClr
import com.lessonmaker.app.theme.semiBold
import com.lessonmaker.app.theme.youtubeCardBorderClr
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.blue_blur_effect
import lessonmaker.composeapp.generated.resources.cross_icon
import lessonmaker.composeapp.generated.resources.crown_icon
import lessonmaker.composeapp.generated.resources.decreasing_icon
import lessonmaker.composeapp.generated.resources.filter_icon
import lessonmaker.composeapp.generated.resources.golden_blur_effect
import lessonmaker.composeapp.generated.resources.increasing_icon
import lessonmaker.composeapp.generated.resources.light_golden_blur
import lessonmaker.composeapp.generated.resources.medal_img
import lessonmaker.composeapp.generated.resources.position1
import lessonmaker.composeapp.generated.resources.position2
import lessonmaker.composeapp.generated.resources.position3
import lessonmaker.composeapp.generated.resources.position_icon
import lessonmaker.composeapp.generated.resources.position_icon_2
import lessonmaker.composeapp.generated.resources.position_icon_3
import lessonmaker.composeapp.generated.resources.profile_icon
import lessonmaker.composeapp.generated.resources.quiz_detail_banner_dummy1
import lessonmaker.composeapp.generated.resources.quiz_detail_banner_dummy2
import lessonmaker.composeapp.generated.resources.video_pause_icon

@Composable
fun LeaderBoardScreenFlow(dashboardViewModel: DashboardViewModel,
                          dashboardRoutes: (String) -> Unit
)
{
    var applyFilter by remember { mutableStateOf(false) }
    var filterApplied by remember { mutableStateOf(false) }
    val list=listOf(
        LeaderBord("4","Kevin P","@angelinanerd","980 XP",Res.drawable.position1,true),
        LeaderBord("5","Caroline H","@jakboth","970 XP",Res.drawable.position2,false),
        LeaderBord("6","Teressa A","@angelinanerd","957 XP",Res.drawable.position1,true),
        LeaderBord("7","Angelina Tuff","@serenathebird","950 XP",Res.drawable.position1,false),
        LeaderBord("8","Angelina Tuff","@serenathebird","950 XP",Res.drawable.position1,false),
        LeaderBord("9","Angelina Tuff","@serenathebird","950 XP",Res.drawable.position1,false),
        LeaderBord("10","Angelina Tuff","@serenathebird","950 XP",Res.drawable.position1,false),
        LeaderBord("11","Angelina Tuff","@serenathebird","950 XP",Res.drawable.position1,false),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.Transparent)
    )
    {
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 12.dp)
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
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                )
                {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(interactionSource = MutableInteractionSource(),
                                indication = null
                            )
                            {
                                applyFilter=true

                            }
                    )
                    {
                        Image(painter = painterResource(Res.drawable.filter_icon),
                            contentDescription = "Filter ",
                            modifier = Modifier
                                .fillMaxSize()
                        )

                    }
                    if(filterApplied)
                    {
                        Column(
                            modifier = Modifier
                        )
                        {
                            Text(
                                text = "Country",
                                color = White,
                                style = TextSize12().Regular()
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            )
                            {
                                Text(
                                    text = "🇦🇪",
                                    modifier = Modifier
                                        .width(24.dp)
                                        .height(18.dp)
                                )
                                Text(
                                    text = "UAE",
                                    color = White,
                                    style = TextSize14().Medium(),

                                    )


                            }

                        }

                    }

                }

                Column(
                    modifier = Modifier
                )
                {
                    Text(
                        text = if(filterApplied) "School" else "View as",
                        color = White,
                        style = TextSize12().Regular()
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = if(filterApplied) "AMC School" else "Globally",
                        color = Color.White,
                        style = TextSize14().Medium()
                    )

                }
                if (filterApplied)
                {
                    Box(
                        modifier = Modifier
                            .size(11.dp)
                            .clickable(interactionSource = MutableInteractionSource(),
                                indication = null
                            )
                            {
                                filterApplied=false

                            }

                    )
                    {
                        Image(painter = painterResource(Res.drawable.cross_icon),
                            contentDescription = "Cross Icon",
                            modifier = Modifier
                                .fillMaxSize()
                        )

                    }

                }
                else
                {
                    Spacer(modifier = Modifier.width(24.dp))
                }




            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        positionHolderBoard()
        Spacer(modifier = Modifier.height(20.dp))
        leaderBoard(list)






    }

    if (applyFilter){
        FilterDialogue(
            bottomSheetCommonInterface= object : BottomSheetCommonInterface{
                override fun closeSheet() {
                    applyFilter = false
                }

                override fun moveNextScreen() {
                    applyFilter = false
                    filterApplied= true
                }

            }

        )
    }

}
@Composable
fun positionHolderBoard()
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(228.dp)
            .padding(horizontal = 12.dp)
            .border(
                width = 0.dp,
                color = banner2Clr,
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        backgroundColor = banner2Clr,
        elevation = 0.dp,

        )
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            Box(
                modifier = Modifier

                    .align(Alignment.TopEnd)
            )
            {
                Image(
                    painter = painterResource(Res.drawable.blue_blur_effect),
                    contentDescription = "Blue Effect",
                    modifier = Modifier

                )

            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
            )
            {
                Image(
                    painter = painterResource(Res.drawable.golden_blur_effect),
                    contentDescription = "Golden Effect",
                    modifier = Modifier

                )

            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal =40.dp)
                    .padding(bottom = 10.dp)
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Spacer(modifier = Modifier.height(70.dp))
                        Box( modifier = Modifier
                            .size(52.dp)
                        )
                        {
                            Card(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .border(
                                        width = 0.dp,
                                        color = White,
                                        shape = CircleShape
                                    ),
                                shape = CircleShape,
                                backgroundColor = White,
                                elevation = 0.dp,

                                )
                            {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                                {
                                    Image(painter = painterResource(Res.drawable.profile_icon),
                                        contentDescription = "Profile",
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter))
                                }
                            }
                            Image(painter = painterResource(Res.drawable.position_icon_2),
                                contentDescription = "Position Icon",
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .size(16.dp)
                                    .offset(y=10.dp)
                            )

                        }


                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "Caroline H",
                            color = White,
                            style = TextSize14().Medium()
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        )
                        {
                            Image(
                                painter = painterResource(Res.drawable.medal_img),
                                contentDescription = "Position",
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "970",
                                color = White,
                                style = TextSize18().bold()
                            )



                        }

                    }
                    Column(
                        modifier = Modifier
                        ,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    )
                    {
                        Box(
                            modifier = Modifier
                                .width(55.dp)
                                .height(40.dp)
                        )
                        {
                            Image(painter = painterResource(Res.drawable.crown_icon),
                                contentDescription = "Profile Icon",
                                modifier = Modifier
                                    .fillMaxSize()
                            )

                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Box( modifier = Modifier
                            .size(80.dp)
                        )
                        {
                            Card(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .border(
                                        width = 2.dp,
                                        color = profileBorderClr,
                                        shape = CircleShape
                                    ),
                                shape = CircleShape,
                                backgroundColor = White,
                                elevation = 0.dp,

                                )
                            {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                                {
                                    Image(painter = painterResource(Res.drawable.position3),
                                        contentDescription = "Profile",
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter))
                                }
                            }
                            Image(painter = painterResource(Res.drawable.position_icon),
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .size(20.dp)
                                    .offset(y=10.dp))
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "Kevin P",
                            color = White,
                            style = TextSize14().Medium()
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        )
                        {
                            Image(
                                painter = painterResource(Res.drawable.medal_img),
                                contentDescription = "Position",
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "980",
                                color = White,
                                style = TextSize18().bold()
                            )



                        }
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Spacer(modifier = Modifier.height(70.dp))
                        Box( modifier = Modifier
                            .size(52.dp)
                        )
                        {
                            Card(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .border(
                                        width = 0.dp,
                                        color = White,
                                        shape = CircleShape
                                    ),
                                shape = CircleShape,
                                backgroundColor = White,
                                elevation = 0.dp,

                                )
                            {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                                {
                                    Image(painter = painterResource(Res.drawable.position1),
                                        contentDescription = "Profile",
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter))
                                }
                            }
                            Image(painter = painterResource(Res.drawable.position_icon_3),
                                contentDescription = "Position Icon",
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .size(16.dp)
                                    .offset(y=10.dp))
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "Teressa A",
                            color = White,
                            style = TextSize14().Medium()
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        )
                        {
                            Image(
                                painter = painterResource(Res.drawable.medal_img),
                                contentDescription = "Position",
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "957",
                                color = White,
                                style = TextSize18().bold()
                            )



                        }
                    }


                }


            }

        }

    }


}
@Composable
fun leaderBoard(
    list: List<LeaderBord>
)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = banner2Clr,
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            ),
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        backgroundColor = banner2Clr,
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        )
        {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        color = White,
                        shape = RoundedCornerShape(40.dp)
                    )
            )

        }
        Column {
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
                    text = "Board Standings",
                    color = White,
                    style = TextSize16().semiBold()
                )

                Text(
                    text = "View All",
                    color = White,
                    style = TextSize12().Regular()
                )



            }


            list.forEach { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                )
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = item.position,
                                color = White,
                                style = TextSize14().Medium()
                            )
                            Image(
                                painter = painterResource(if(item.increasing) Res.drawable.increasing_icon else Res.drawable.decreasing_icon),
                                contentDescription = item.name,
                                modifier = Modifier.size(16.dp)
                            )

                            Image(
                                painter = painterResource(item.img),
                                contentDescription = item.name,
                                modifier = Modifier.size(40.dp)
                            )
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                androidx.compose.material.Text(
                                    text = item.name,
                                    color = White,
                                    style = TextSize16().semiBold()
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = item.email,
                                    color = White,
                                    style = TextSize10().Regular()
                                )
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {

                           Text(
                                text = item.marks,
                                color = White,
                                style = TextSize12().Medium()
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()

                            .height(1.dp)
                            .background(White)
                    )

                }

            }
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .background(banner2Clr)
            )
            {}

        }
    }





}
data class LeaderBord(
    val position:String,
    val name:String,
    val email:String,
    val marks: String,
    val img: DrawableResource,
    val increasing: Boolean
)

