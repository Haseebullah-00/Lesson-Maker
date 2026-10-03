package com.lessonmaker.app.common.bottomSheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.TextSize19
import com.lessonmaker.app.theme.TextSize20
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.inputBorderColorActive
import com.lessonmaker.app.theme.semiBold
import com.lessonmaker.app.theme.spaceBetweenFields


import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res

@Composable
fun BottomSheetCustomDrag(closeClicked:()->Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Drag handle at the center
        Box(
            modifier = Modifier
                .align(Alignment.Center) // Align the drag handle in the center
                .width(50.dp)
                .height(6.dp)
                .clip(RoundedCornerShape(50))
                .background(White)
        )

        // Close button at the right
        IconButton(
            onClick = {
                closeClicked.invoke()
            },
            modifier = Modifier.align(Alignment.CenterEnd) // Align the close button to the end
        ) {
            Icon(
                imageVector = Icons.Default.Close, // Material close icon
                contentDescription = "Close",
                tint = inputBorderColorActive
            )
        }
    }
}

@Composable
fun BottomSheetToolBar(heading:String,subHeading:String,onClosed:(Unit)-> Unit){
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = spaceBetweenFields).padding(top = spaceBetweenFields),
    ) {
        Spacer(
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = heading,
            style = TextSize20().semiBold(),
            color = Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)

        )
//        Image(
//            painter = painterResource(Res.drawable.ic_new_close),
//            contentDescription = "Close",
//            modifier = Modifier.size(30.dp).clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null // Disables the ripple effect
//            ) {
//                onClosed.invoke(Unit)
//            }
//        )
    }

    if (subHeading.isNotBlank()){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 25.dp),
                text = subHeading,
                style = TextSize14().copy(textAlign = TextAlign.Center),
                color = Black
            )
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheet(
    fullScreenDefault: Boolean=true,
    heading: String,
    subHeading: String,
    space: Dp = spaceBetweenFields,
    bottomSheetCommonInterface: BottomSheetCommonInterface,
    content: @Composable ColumnScope.() -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = fullScreenDefault)

    ModalBottomSheet(
        onDismissRequest = {
            bottomSheetCommonInterface.closeSheet()
        },
        scrimColor = Color.Transparent,
        sheetState = sheetState,
        containerColor = Color.Transparent,
        shape = RectangleShape,
        modifier = Modifier.fillMaxWidth(),
        dragHandle = {
            Box(modifier = Modifier.height(0.dp))
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth().statusBarsPadding()
                .imePadding()
                .background(White, shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space)
        ) {
            BottomSheetToolBar(heading = heading, subHeading = subHeading) {
                bottomSheetCommonInterface.closeSheet()
            }
            content()
        }
    }
}


               ///////    sheet    with cross sign


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheetCart(
    fullScreenDefault: Boolean=true,
    heading: String,
    subHeading: String,
    space: Dp = spaceBetweenFields,
    bottomSheetCommonInterface: BottomSheetCommonInterface,
    content: @Composable ColumnScope.() -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = fullScreenDefault)

    ModalBottomSheet(
        onDismissRequest = {
            bottomSheetCommonInterface.closeSheet()
        },
        scrimColor = Color.Transparent,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp),
        tonalElevation = 16.dp,
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 0.dp), // removes bottom gap,
        dragHandle = {
            Box(modifier = Modifier.height(0.dp))
        },

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .background(White, shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space)
        ) {
            BottomSheetToolBarBetwwnSpace(heading = heading, subHeading = subHeading) {
                bottomSheetCommonInterface.closeSheet()
            }
            content()
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheetNewCart(
    fullScreenDefault: Boolean=true,
    heading: String,
    subHeading: String,
    space: Dp = spaceBetweenFields,
    bottomSheetCommonInterface: BottomSheetCommonInterface,
    content: @Composable ColumnScope.() -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = fullScreenDefault)

    ModalBottomSheet(
        onDismissRequest = {
            bottomSheetCommonInterface.closeSheet()
        },
        scrimColor = Color.Transparent,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp),
        tonalElevation = 16.dp,
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 0.dp), // removes bottom gap,
        dragHandle = {
            Box(modifier = Modifier.height(0.dp))
        },

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .background(White, shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space)
        ) {
            BottomSheetToolBarBetwwnSpacenew(heading = heading, subHeading = subHeading) {
                bottomSheetCommonInterface.closeSheet()
            }
            content()
        }
    }
}

@Composable
fun BottomSheetToolBarBetwwnSpace(heading:String,subHeading:String,onClosed:(Unit)-> Unit){
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 12.dp)
            .padding(start = 20.dp, end = 8.dp),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = heading,
            style = TextSize19().semiBold(),
            color = Black,
        )
        IconButton(
            onClick = { onClosed.invoke(Unit) }
        ) {

//            Image(
//                painter = painterResource(resource = Res.drawable.ic_new_close), // your image
//                contentDescription = "Close",
//                modifier = Modifier.size(36.dp) // adjust size to match default Icon size
//            )
        }
    }

    if (subHeading.isNotBlank()){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 25.dp),
                text = subHeading,
                style = TextSize14().copy(textAlign = TextAlign.Center),
                color = Black
            )
        }
    }
}


@Composable
fun BottomSheetToolBarBetwwnSpacenew(heading:String,subHeading:String,onClosed:(Unit)-> Unit){
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 12.dp)
            .padding(start = 15.dp, end = 0.dp),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = heading,
            style = TextSize18().semiBold(),
            color = Black,
        )
        IconButton(
            onClick = {
                onClosed.invoke(Unit)
            },

            ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color(0xFF000000).copy(alpha = 0.5f)
            )
        }
    }

    if (subHeading.isNotBlank()){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 25.dp),
                text = subHeading,
                style = TextSize14().copy(textAlign = TextAlign.Center),
                color = Black
            )
        }
    }
}
