package com.lessonmaker.app.mainScreen.home.AboutMe

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize24
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.borderClr
import com.lessonmaker.app.theme.grey59
import com.lessonmaker.app.theme.profileCardClr
import com.lessonmaker.app.theme.youtubeCardBorderClr
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.dummy_profile
import lessonmaker.composeapp.generated.resources.ic_fb
import lessonmaker.composeapp.generated.resources.ic_insta
import lessonmaker.composeapp.generated.resources.ic_linkdin
import lessonmaker.composeapp.generated.resources.ic_x
import lessonmaker.composeapp.generated.resources.ic_youtube
import lessonmaker.composeapp.generated.resources.youtube_icon

@Composable
fun AboutMeScreen(
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
)
{
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(235.dp)
                    .border(
                        width = 0.dp,
                        color = profileCardClr,
                        shape = RoundedCornerShape(12.dp)
                    ),
                shape = RoundedCornerShape(12.dp),
                backgroundColor = profileCardClr,
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                )
                {
                    Image(painter = painterResource(Res.drawable.dummy_profile),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                    )

                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Follow me On",
                color = White,
                style = TextSize13().Regular()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            )
            {
                socialDetail(Res.drawable.youtube_icon, onClick = {})
                socialDetail(Res.drawable.ic_insta, onClick = {})
                socialDetail(Res.drawable.ic_fb, onClick = {})
                socialDetail(Res.drawable.ic_x, onClick = {})
                socialDetail(Res.drawable.ic_linkdin, onClick = {})

            }
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
            )
            {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 18.dp, vertical = 12.dp)

                )
                {
                    Text(
                    text = "Hey, I’m Ehsan",
                    color = Black,
                    style = TextSize24().bold()
                     )

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Nunc rutrum porta interdum. Nam orci felis, vehicula et luctus a, venenatis id erat. Nunc varius ipsum nec nibh imperdiet, eget rutrum orci porta. Integer nec neque tortor. Morbi feugiat risus a rutrum malesuada. Nam a dignissim ante. Aenean tempor venenatis lorem lobortis accumsan. Sed ornare neque nunc, ac tristique massa dignissim id. Donec commodo sem eu justo posuere ullamcorper. Maecenas a sapien maximus, molestie arcu ultricies, dapibus risus. Vivamus ac sagittis dolor, in accumsan erat. Donec ac sem iaculis, placerat ipsum scelerisque, congue dolor. Morbi sit amet interdum libero. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec posuere porta ex, nec ornare massa tempus ut. Ut malesuada felis id orci rutrum fringilla id eget lectus.",
                        color = grey59,
                        style = TextSize13().Regular().copy(
                            lineHeight = 22.sp
                        )
                    )




                }

            }
            Spacer(modifier = Modifier.height(20.dp))


        }

    }


}
@Composable
fun socialDetail(
    img: DrawableResource,
    onClick:()-> Unit
)
{
    Card(
        modifier = Modifier
            .size(48.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick.invoke()

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
                painter = painterResource(img),
                contentDescription = "Social",
            )


        }

    }


}