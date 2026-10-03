package com.lessonmaker.app.mainScreen.account

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
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.navigationControler.Screen
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize15
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.TextSize20
import com.lessonmaker.app.theme.TextSize24
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.banner2Clr
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.borderClr
import com.lessonmaker.app.theme.profileBorderClr
import com.lessonmaker.app.theme.selectedTabbarClr
import com.lessonmaker.app.theme.semiBold
import kotlinx.serialization.builtins.ArraySerializer
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.arrow_right
import lessonmaker.composeapp.generated.resources.blue_blur_effect
import lessonmaker.composeapp.generated.resources.blur_effect_1
import lessonmaker.composeapp.generated.resources.blur_effect_2
import lessonmaker.composeapp.generated.resources.golden_blur_effect
import lessonmaker.composeapp.generated.resources.light_golden_blur
import lessonmaker.composeapp.generated.resources.medal_img
import lessonmaker.composeapp.generated.resources.position2
import lessonmaker.composeapp.generated.resources.position_icon
import lessonmaker.composeapp.generated.resources.profile_icon

@Composable
fun AccountScreenFlow(dashboardViewModel: DashboardViewModel,
                      dashboardRoutes: (String) -> Unit
)
{
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    )
    {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp)
        )
        {
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(124.dp)
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
                        Image(painter = painterResource(Res.drawable.blue_blur_effect),
                            contentDescription = "Blue Effect",
                            modifier = Modifier

                        )

                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                    )
                    {
                        Image(painter = painterResource(Res.drawable.golden_blur_effect),
                            contentDescription = "Golden Effect",
                            modifier = Modifier

                        )

                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                    )
                    {
                        Image(painter = painterResource(Res.drawable.light_golden_blur),
                            contentDescription = "Golden Effect",
                            modifier = Modifier

                        )

                    }


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp)
                                .align(Alignment.Center),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)

                        )
                        {
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
                                        Image(painter = painterResource(Res.drawable.profile_icon),
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
                            Column()
                            {
                                Text(
                                    text = "Jakob Bothman",
                                    color = White,
                                    style = TextSize20().Medium()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                )
                                {
                                    Image(
                                        painter = painterResource(Res.drawable.medal_img),
                                        contentDescription = "Position",
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = "980",
                                        color = White,
                                        style = TextSize24().bold()
                                    )
                                }
                            }
                        }
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            )
            {
                Box(
                    modifier = Modifier
                        .weight(1f)
                )
                {
                    countryId("#12","Dubai")

                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                )
                {
                    countryId("#3333","UAE")

                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                )
                {
                    countryId("#49494949","Globally")

                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            listItems("Edit Profile", onCLick = {dashboardRoutes.invoke(DashboardSubScreen.EditProfileScreen.route)})
            Spacer(modifier = Modifier.height(10.dp))
            listItems("Settings", onCLick = {})
            Spacer(modifier = Modifier.height(10.dp))
            listItems("Change Passwords", onCLick = {dashboardRoutes.invoke(DashboardSubScreen.ChangePasswordScreen.route)})
            Spacer(modifier = Modifier.height(10.dp))
            listItems("Privacy Policy", onCLick = {dashboardRoutes.invoke(DashboardSubScreen.PrivacyAndPolicy.route)})
            Spacer(modifier = Modifier.height(10.dp))
            listItems("Terms and Conditions", onCLick = {dashboardRoutes.invoke(DashboardSubScreen.TermsAndConditions.route)})
            Spacer(modifier = Modifier.height(25.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            )
            {
                Text(
                    text = "Logout",
                    color = White,
                    style = TextSize16().bold(),
                    modifier = Modifier
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        )
                        {
                            dashboardRoutes.invoke(Screen.LoginScreenFlow.route)

                        }
                )

            }





        }

    }


}
@Composable
fun countryId(
    id: String,
    country: String

)
{
    Card(
        modifier = Modifier

            .height(65.dp)
            .border(
                width = 0.dp,
                color = banner2Clr,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
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
                Image(painter = painterResource(Res.drawable.light_golden_blur),
                    contentDescription = "Golden Effect",
                    modifier = Modifier

                )

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = id,
                    color = White,
                    style = TextSize14().bold()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = country,
                    color = White,
                    style = TextSize12().Medium()
                )

            }
        }


    }


}
@Composable
fun listItems(
    label: String,
    onCLick:()-> Unit
)
{
    Card(
        modifier = Modifier
            .height(54.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onCLick.invoke()


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
                text = label,
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