package com.lessonmaker.app.mainScreen.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.Gray
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.PrimaryColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize10
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize15
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.TextSize20
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.banner1Clr
import com.lessonmaker.app.theme.banner2Clr
import com.lessonmaker.app.theme.banner3Clr
import com.lessonmaker.app.theme.blurClr
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.borderClr
import com.lessonmaker.app.theme.borderColor
import com.lessonmaker.app.theme.buttonClr
import com.lessonmaker.app.theme.categoryTxtClr
import com.lessonmaker.app.theme.hintClr
import com.lessonmaker.app.theme.hintTextColor
import com.lessonmaker.app.theme.selectedTabbarClr
import com.lessonmaker.app.theme.semiBold
import com.lessonmaker.app.theme.youtubeCardBorderClr
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.BannerImg
import lessonmaker.composeapp.generated.resources.HomeBannerDummy
import lessonmaker.composeapp.generated.resources.HomeBannerDummy1
import lessonmaker.composeapp.generated.resources.HomeBannerDummy2
import lessonmaker.composeapp.generated.resources.HomeBannerDummy3
import lessonmaker.composeapp.generated.resources.MicroScopeImg
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.arrow_right
import lessonmaker.composeapp.generated.resources.history
import lessonmaker.composeapp.generated.resources.ic_dummy_top_profile
import lessonmaker.composeapp.generated.resources.ic_home_profile
import lessonmaker.composeapp.generated.resources.ic_medal_home
import lessonmaker.composeapp.generated.resources.ic_youtube
import lessonmaker.composeapp.generated.resources.medal_img
import lessonmaker.composeapp.generated.resources.position1
import lessonmaker.composeapp.generated.resources.position2
import lessonmaker.composeapp.generated.resources.science
import lessonmaker.composeapp.generated.resources.social
import lessonmaker.composeapp.generated.resources.sports
import lessonmaker.composeapp.generated.resources.trending1
import lessonmaker.composeapp.generated.resources.trending2
import lessonmaker.composeapp.generated.resources.trending3

@Composable
fun LaunchHomeScreenFlow(dashboardViewModel: DashboardViewModel,
                         dashboardRoutes: (String) -> Unit)
{
    val list = listOf(
        Category("Sport", Res.drawable.sports),
        Category("Science", Res.drawable.science),
        Category("History", Res.drawable.history),
        Category("Social", Res.drawable.social),
        Category("Social", Res.drawable.social),
        Category("Sport", Res.drawable.sports),
        Category("Science", Res.drawable.science),
        Category("History", Res.drawable.history),
    )
    val list1=listOf(
        Trending("Back to Schools Quizzzz","Math • 2.6k Attendees",Res.drawable.trending1),
        Trending("The Bright Minds Science Quiz","Math • 2.6k Attendees",Res.drawable.trending2),
        Trending("Explore the Wonders of Science!","Math • 2.6k Attendees",Res.drawable.trending3)
    )
    val list2=listOf(
        Position("1","Kevin P","@angelinanerd","980",Res.drawable.position1),
        Position("2","Caroline H","@jakboth","970",Res.drawable.position2),
        Position("3","Teressa A","@angelinanerd","957",Res.drawable.position1),
        Position("4","Angelina Tuff","@serenathebird","956",Res.drawable.position1),
    )
    Box(
        modifier = Modifier
            .fillMaxSize()

    )
    {
        Column(
            modifier = Modifier.fillMaxSize()




        )
        {

            TopBarSection()
            LazyColumn(
                modifier = Modifier.weight(1f)

            )
            {

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    HomeBanner(function = {})
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Text(
                            text = "Explore Categories",
                            color = White,
                            style = TextSize16().semiBold()
                        )
                        Text(
                            text = "View All",
                            color = White,
                            style = TextSize12().Regular(),
                            modifier = Modifier
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                )
                                {
                                    dashboardRoutes.invoke(DashboardSubScreen.CategoryScreen.route)
                                }
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    CategoriesItems(list)
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Text(
                            text = "Trending",
                            color = White,
                            style = TextSize16().semiBold()
                        )

                        Text(
                            text = "View All",
                            color = White,
                            style = TextSize12().Regular(),
                            modifier = Modifier
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                )
                                {

                                }
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    trendingList(list1)
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Text(
                            text = "Leaderboard",
                            color = White,
                            style = TextSize16().semiBold()
                        )

                        Text(
                            text = "View All",
                            color = White,
                            style = TextSize12().Regular(),
                            modifier = Modifier
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                )
                                {
                                    dashboardRoutes.invoke(DashboardSubScreen.LeaderBoardScreenFlow.route)

                                }
                        )

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    leaderBoard(list2)
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    )
                    {
                        Card(
                            modifier = Modifier
                                .height(54.dp)
                                .padding(horizontal = 2.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    dashboardRoutes.invoke(DashboardSubScreen.AboutMeScreen.route)

                                }

                                .border(
                                    width = 1.dp,
                                    color = borderClr,
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            shape = RoundedCornerShape(12.dp),
                            backgroundColor = White,
                        )
                        {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            )
                            {
                                Text(
                                    text = "About me",
                                    color = Black,
                                    style = TextSize15().semiBold(),

                                    )
                                Card(
                                    modifier = Modifier
                                        .size(30.dp)
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
                                        Image(
                                            painter = painterResource(Res.drawable.arrow_right),
                                            contentDescription = "Arrow Right",
                                        )


                                    }

                                }

                            }


                        }


                    }

                }
                item {
                    Spacer(modifier = Modifier.height(80.dp))

                }
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(bottom = 30.dp)
                .align(Alignment.BottomEnd)

        )
        {
            Card(
                modifier = Modifier

                    .size(60.dp)
                    .align(Alignment.BottomEnd)

                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {

                    }

                    .border(
                        width = 1.dp,
                        color = youtubeCardBorderClr,
                        shape = CircleShape
                    ),
                shape = CircleShape,
                backgroundColor = White,
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                )
                {
                    Image(
                        painter = painterResource(Res.drawable.ic_youtube),
                        contentDescription = "Youtube",
                    )


                }

            }

        }


    }

}

@Composable
fun leaderBoard(
    list: List<Position>
)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = White,
        elevation = 4.dp
    ) {
        Column {
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
                                color = Black,
                                style = TextSize14().Medium()
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
                                Text(
                                    text = item.name,
                                    color = Black,
                                    style = TextSize16().semiBold()
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = item.email,
                                    color = hintClr,
                                    style = TextSize10().Regular()
                                )
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.medal_img),
                                contentDescription = "Position",
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = item.marks,
                                color = Black,
                                style = TextSize18().bold()
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()

                            .height(0.5.dp)
                            .background(Gray)
                    )

                }

            }
        }
    }





}
@Composable
fun CategoriesItems(
    list: List<Category>
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(list.size) { item ->
            val category = list[item]
            Card(
                modifier = Modifier
                    .width(70.dp)
                    .height(72.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { }
                    .border(
                        width = 1.dp,
                        color = White,
                        shape = RoundedCornerShape(10.dp)
                    ),
                shape = RoundedCornerShape(10.dp),
                backgroundColor = White,
                elevation = 0.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                       // .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Image in center
                    Image(
                        painter = painterResource(category.img),
                        contentDescription = category.name,
                        modifier = Modifier.size(40.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = category.name,
                        color = hintClr,
                        style = TextSize10().Regular(),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .height(14.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun trendingList(
    list: List<Trending>
)
{
    list.forEach { item->
        Card(
            modifier = Modifier
                .padding(horizontal = 12.dp)

                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {

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
@Composable
fun HomeBanner(function: () -> Unit) {
    // Example data class for dynamic content
    data class BannerItem(
        val title: String,
        val description: String,
        val imageRes: DrawableResource,
        val backgroundColor: Color,
        val percentage: Int
    )

    val items = listOf(
        BannerItem("Math Quiz", "Explore the Wonders of Math!", Res.drawable.BannerImg, banner1Clr,80),
        BannerItem("Science Quiz", "Explore the Wonders of Science!", Res.drawable.BannerImg, banner2Clr,65),
        BannerItem("General Quiz", "Explore the Wonders of General!", Res.drawable.BannerImg, banner3Clr,75),
    )

    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { items.size }
    )

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .height(328.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 40.dp)
            ) { page ->

                val scale = animateFloatAsState(
                    targetValue = if (pagerState.currentPage == page) 1f else 0.85f,
                    animationSpec = tween(durationMillis = 300)
                )
                val item = items[page]

                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .clickable(interactionSource = MutableInteractionSource(),
                            indication = null
                        )
                        {

                        }
                        .graphicsLayer {
                            scaleX = scale.value
                            scaleY = scale.value
                        },
                    backgroundColor = item.backgroundColor,
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 18.dp, top = 18.dp)
                    ) {

                        Card(
                            modifier = Modifier.height(34.dp),
                            shape = RoundedCornerShape(40.dp),
                            backgroundColor = White,
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.MicroScopeImg),
                                    contentDescription = item.title,
                                    modifier = Modifier.size(17.dp)
                                )
                                Text(
                                    text = item.title,
                                    color = Black,
                                    style = TextSize10().Medium()
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))


                        Text(
                            text = item.description,
                            color = White,
                            style = TextSize20().semiBold()
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Get it done now.",
                            color = White,
                            style = TextSize12().Regular()
                        )

                        Spacer(modifier = Modifier.height(20.dp))


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                CircularProgressWithPercentage(item.percentage)

                                Spacer(modifier = Modifier.height(15.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Continue",
                                        color = White,
                                        style = TextSize12().Regular()
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Image(
                                        painter = painterResource(Res.drawable.arrow_right),
                                        contentDescription = "Arrow Right"
                                    )
                                }
                            }

                            Image(
                                painter = painterResource(Res.drawable.BannerImg),
                                contentDescription = item.title
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CircularProgressWithPercentage(percentage: Int) {
    val sweepAngle = (percentage / 100f) * 360f

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(100.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            drawArc(
                color = Color.LightGray,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(
                    width = 10.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )


            drawArc(
                color = Color.White,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(
                    width = 10.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }


        Text(
            text = "$percentage%",
            style = TextSize20().semiBold(),
            color = Color.White
        )
    }
}
@Composable
fun TopBarSection(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Transparent)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(Res.drawable.ic_dummy_top_profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Hi, Welcome Back",
                        color = Color.White,
                        style = TextSize12().Regular()
                    )
                    Text(
                        text = "Jakob Bothman",
                        color = Color.White,
                        style = TextSize20().Medium()
                    )
                }
            }

            Card(
                shape = RoundedCornerShape(50),
                backgroundColor = Color.White,
                elevation = 0.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_medal_home),
                        contentDescription = "XP Medal",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "500",
                        color = PrimaryColor,
                        style = TextSize12().Medium()
                    )
                }
            }
        }
    }
}








data class Category(
    val name: String,
    val img: DrawableResource

)
data class Trending(
    val type:String,
    val detail: String,
    val img: DrawableResource
)
data class Position(
        val position:String,
        val name:String,
        val email:String,
        val marks: String,
        val img: DrawableResource
)

