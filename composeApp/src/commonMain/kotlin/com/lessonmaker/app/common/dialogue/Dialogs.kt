package com.lessonmaker.app.common.dialogue


import KottieAnimation
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.lessonmaker.app.common.DialogButton
import com.lessonmaker.app.common.MainTransparentBox
import com.lessonmaker.app.common.buttonHeight45
import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.extensionFunctions.handleNull
import com.lessonmaker.app.theme.Black
import com.lessonmaker.app.theme.PrimaryColor
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize20
import com.lessonmaker.app.theme.White

import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition


import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.ic_camera
import lessonmaker.composeapp.generated.resources.ic_gallery
import lessonmaker.composeapp.generated.resources.ic_next


@Composable
fun ImageSourceOptionDialog(
    onDismissRequest: () -> Unit,
    onGalleryRequest: () -> Unit = {},
    onCameraRequest: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        MainTransparentBox {
            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Select an Image Source",
                    style = TextSize20(),
                    color = Black,
                    maxLines = 5,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                SinglePickerOption(title = "Camera", icon = Res.drawable.ic_camera){
                    onCameraRequest.invoke()
                }
                Divider(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp), thickness = 1.dp, color = PrimaryColor)
                SinglePickerOption(title = "Gallery", icon = Res.drawable.ic_gallery){
                   onGalleryRequest.invoke()
                }

            }
        }
    }
}

@Composable
private fun SinglePickerOption(title: String,icon:DrawableResource,onClick:()->Unit){
    Row(
        modifier = Modifier.fillMaxWidth().clickable {
            onClick.invoke()
        }.padding(vertical = 15.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            tint = PrimaryColor,
            modifier = Modifier.size(25.dp),
            painter = painterResource(icon),
            contentDescription = null
        )
        Text(modifier = Modifier.weight(1f),text = title, color = PrimaryColor, style = TextSize14())
        Icon(
            tint = PrimaryColor,
            modifier = Modifier.size(20.dp),
            painter = painterResource(Res.drawable.ic_next),
            contentDescription = null
        )
    }
}

@Composable
fun AlertMessageDialog(
    title: String?=null,
    message: String? = null,
    resource: DrawableResource? = null,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    onClick: (Boolean) -> Unit = {},
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        MainTransparentBox {
            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                resource?.let { drawableRes ->
                    Image(
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(drawableRes),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

                title?.let {
                    Text(
                        text = title,
                        style = TextSize20(),
                        color = Black,
                        maxLines = 5,
                        textAlign = TextAlign.Center
                    )
                }

                message?.let {
                    Text(
                        text = it,
                        style = TextSize16(),
                        color = Black,
                        maxLines = 10,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    negativeButtonText?.let {

                        DialogButton(isNegative = true,heading = it, modifier = Modifier.weight(1f).fillMaxWidth().height(
                            buttonHeight45
                        ).padding(horizontal = 10.dp)) {
                            onClick.invoke(false)
                        }
                    }

                    positiveButtonText?.let {
                        DialogButton(heading = it, modifier = Modifier.weight(1f).fillMaxWidth().height(
                            buttonHeight45).padding(horizontal = 10.dp)) {
                            onClick.invoke(true)
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun APIExceptionHandleDialog(commonLoading: CommonLoading, apiDialogInterface: APIDialogInterface){
    var rememberDialogType by remember { mutableStateOf(0) }
    var rememberMessage by remember { mutableStateOf("") }
    var rememberActionButton by remember { mutableStateOf("") }
    AlertMessageDialog(
        message = rememberMessage,
        positiveButtonText = rememberActionButton
    ) {
        when(rememberDialogType){
            1->apiDialogInterface.showLogin()
            else->apiDialogInterface.simpleDismiss()
        }
    }
    LaunchedEffect(commonLoading.apiException){
        when(commonLoading.apiException?.statusCode?.value){
            401->{
                rememberDialogType=1
                rememberMessage="Login Session expired"
                rememberActionButton="Login"
            }
            else->{
                rememberDialogType=0
                rememberMessage= buildString {
                    append(commonLoading.apiException?.customMessage.handleNull() )
                    append(commonLoading.apiException?.apiName)
                }
                rememberActionButton="OK"
            }
        }
    }
}

interface APIDialogInterface{
    fun showLogin()
    fun simpleDismiss()
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoadingDialog() {
    var animation by remember { mutableStateOf("") }

    var playing by remember { mutableStateOf(true) }

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File(animation)
    )
    val animationState by animateKottieCompositionAsState(
        composition = composition,
        isPlaying = playing
    )

    Popup(onDismissRequest = {},
        properties = PopupProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            KottieAnimation(
                composition = composition,
                progress = { animationState.progress },
                modifier = Modifier.height(150.dp)
            )
        }
    }
    LaunchedEffect(Unit){
        animation = Res.readBytes("files/loading_animation.json").decodeToString()
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun MediaSourcePicker(type: String, mediaPickerInterface: MediaPickerInterface) {
    Dialog(
        onDismissRequest = {
            mediaPickerInterface.dismissed()
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        MainTransparentBox(
            backgroundColor = White
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 10.dp).background(White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Select an Image Source",
                    style = TextSize20(),
                    color = Black,
                    maxLines = 5,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                SinglePickerOption(
                    title = "Camera",
                    icon = Res.drawable.ic_camera
                ) {
                    mediaPickerInterface.pickerImageCamera()
                }
                Divider(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    thickness = 1.dp,
                    color = Black
                )
                SinglePickerOption(
                    title = "Gallery",
                    icon = Res.drawable.ic_gallery
                ) {
                    mediaPickerInterface.pickImageGallery()
                }

                if (type.contains("mp4")) {
                    Divider(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        thickness = 1.dp,
                        color = Black
                    )
                    SinglePickerOption(
                        title = "Video from Camera",
                        icon = Res.drawable.ic_camera
                    ) {
                        mediaPickerInterface.pickVideoCamera()
                    }

                    Divider(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        thickness = 1.dp,
                        color = Black
                    )
                    SinglePickerOption(
                        title = "Video from Gallery",
                        icon = Res.drawable.ic_gallery
                    ) {
                        mediaPickerInterface.pickVideoGallery()
                    }
                }

                if (type.contains("pdf")) {
                    Divider(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        thickness = 1.dp,
                        color = Black
                    )
                    SinglePickerOption(
                        title = "PDF file",
                        icon = Res.drawable.ic_gallery
                    ) {
                        mediaPickerInterface.pickPdf()
                    }
                }
            }
        }
    }
}

interface MediaPickerInterface {
    fun pickerImageCamera()
    fun pickVideoGallery()
    fun pickVideoCamera()
    fun pickImageGallery()
    fun dismissed()
    fun pickPdf()
}

/*@Composable
fun LoadingDialog() {
    // Dialog with CircularProgressIndicator
    Dialog(onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .background(Color.Transparent)
            ,
            contentAlignment = Alignment.Center
            *//*horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center*//*
        ) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
        }
    }
}*/





