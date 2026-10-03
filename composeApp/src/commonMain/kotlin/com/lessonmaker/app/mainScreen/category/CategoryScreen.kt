package com.lessonmaker.app.mainScreen.category

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.navigationControler.DashboardSubScreen
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.InputFieldBorderClr
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.blurClr
import com.lessonmaker.app.theme.bold
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.category_general
import lessonmaker.composeapp.generated.resources.category_math
import lessonmaker.composeapp.generated.resources.category_science
import lessonmaker.composeapp.generated.resources.category_sport
import kotlin.collections.chunked
import kotlin.collections.forEach

@Composable
fun CategoryScreen(dashboardViewModel: DashboardViewModel,
                   dashboardRoutes: (String) -> Unit
)
{
    val list = listOf(
        Category("Maths", Res.drawable.category_math),
        Category("Science", Res.drawable.category_science),
        Category("General", Res.drawable.category_general),
        Category("Sports", Res.drawable.category_sport),
        Category("Maths", Res.drawable.category_math),
        Category("Science", Res.drawable.category_science),
        Category("General", Res.drawable.category_general),
        Category("Sports", Res.drawable.category_sport),
        Category("Maths", Res.drawable.category_math),
        Category("Science", Res.drawable.category_science),
        Category("General", Res.drawable.category_general),
        Category("Sports", Res.drawable.category_sport)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(35.dp))
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
                                        dashboardRoutes.invoke(DashboardSubScreen.SubCategoryScreen.route)

                                    }
                                )
                                {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(94.dp)
//
                                            .border(
                                                width = 1.dp,
                                                color = InputFieldBorderClr,
                                                shape = RoundedCornerShape((8).dp)
                                            ),
                                        shape = RoundedCornerShape((8).dp),
                                        backgroundColor = White,
                                        elevation = 10.dp,

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


}
data class Category(
    val type: String,
    val img: DrawableResource
)