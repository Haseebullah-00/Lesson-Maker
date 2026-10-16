package com.lessonmaker.app.common.widgets


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.lessonmaker.app.common.dialogue.AlertMessageDialog
import com.lessonmaker.app.common.dialogue.ImageSourceOptionDialog
import com.lessonmaker.app.common.dialogue.MediaPickerInterface
import com.lessonmaker.app.common.dialogue.MediaSourcePicker
import com.lessonmaker.app.extensionFunctions.handleNull
import com.lessonmaker.app.getPlatform
import com.lessonmaker.app.shared.PermissionCallback
import com.lessonmaker.app.shared.PermissionStatus
import com.lessonmaker.app.shared.PermissionType
import com.lessonmaker.app.shared.createPermissionsManager
import com.lessonmaker.app.shared.media.excel.rememberGalleryManagerForExcel
import com.lessonmaker.app.shared.media.pdf.rememberGalleryManagerForPdf
import com.lessonmaker.app.shared.media.rememberGalleryManager
import com.lessonmaker.app.shared.media.video.rememberCameraManagerForVideo
import com.lessonmaker.app.shared.media.video.rememberGalleryManagerForVideo
import com.lessonmaker.app.shared.rememberCameraManager
import com.lessonmaker.app.theme.banner2Clr

import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.dummy_profile
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@Composable
fun LocalImagePicker(
    modifier: Modifier = Modifier,
    onImageSelected: (ImageBitmap, String,ByteArray?) -> Unit
) {
/*    val coroutineScope = rememberCoroutineScope()
    var imageSourceOptionDialog by remember { mutableStateOf(value = false) }
    var launchCamera by remember { mutableStateOf(value = false) }
    var launchGallery by remember { mutableStateOf(value = false) }
    var launchSetting by remember { mutableStateOf(value = false) }
    var permissionRationalDialog by remember { mutableStateOf(value = false) }
    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.CAMERA -> launchCamera = true
                        PermissionType.GALLERY -> launchGallery = true
                    }
                }

                else -> {
                    permissionRationalDialog = true
                }
            }
        }


    })

    val cameraManager = rememberCameraManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) { it?.toImageBitmap() }
            val fileName = withContext(Dispatchers.Default) { it?.getFileName() }
            val fileBytes = withContext(Dispatchers.Default) { it?.toByteArray() }

            bitmap?.let {
                onImageSelected.invoke(it, fileName.handleNull(), fileBytes)
            }
        }
    }

    val galleryManager = rememberGalleryManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) { it?.toImageBitmap() }
            val fileName = withContext(Dispatchers.Default) { it?.getFileName() }
            val fileBytes = withContext(Dispatchers.Default) { it?.toByteArray() }

            bitmap?.let {
                onImageSelected.invoke(it, fileName.handleNull(), fileBytes)
            }
        }
    }
    if (imageSourceOptionDialog) {
        ImageSourceOptionDialog(onDismissRequest = {
            imageSourceOptionDialog = false
        }, onGalleryRequest = {
            imageSourceOptionDialog = false
            launchGallery = true
        }, onCameraRequest = {
            imageSourceOptionDialog = false
            launchCamera = true
        })
    }
    if (launchGallery) {
        if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
            galleryManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.GALLERY)
        }
        launchGallery = false
    }
    if (launchCamera) {
        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
            cameraManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.CAMERA)
        }
        launchCamera = false
    }
    if (launchSetting) {
        permissionsManager.launchSettings()
        launchSetting = false
    }


    if (permissionRationalDialog) {
        AlertMessageDialog(title = "Permission Required",
            message = "To set your profile picture, please grant this permission. You can manage permissions in your device settings.",
            positiveButtonText = "Settings",
            negativeButtonText = "Cancel",
            onClick = {
                if (it){
                    permissionRationalDialog = false
                    launchSetting = true
                }else{
                    permissionRationalDialog = false
                }
            },
        )
    }

    Box(modifier = modifier.clickable {
        imageSourceOptionDialog = true
    })*/

    val coroutineScope = rememberCoroutineScope()
    var imageSourceOptionDialog by remember { mutableStateOf(value = false) }
    var launchCamera by remember { mutableStateOf(value = false) }
    var launchGallery by remember { mutableStateOf(value = false) }
    var launchSetting by remember { mutableStateOf(value = false) }
    var permissionRationalDialog by remember { mutableStateOf(value = false) }
    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType:
            PermissionType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.CAMERA -> launchCamera = true
                        PermissionType.GALLERY -> launchGallery = true
                        else -> {}
                    }
                }

                else -> {
                    permissionRationalDialog = true
                }
            }
        }


    })

    val cameraManager = rememberCameraManager(allowsEditing = true) {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) { it?.toImageBitmap() }?:return@launch
            val fileName = withContext(Dispatchers.Default) { it?.getFileName() }
            val fileBytes = withContext(Dispatchers.Default) { it?.toByteArray() }?:return@launch
            onImageSelected.invoke(bitmap,fileName.handleNull(),fileBytes)
        }
    }

    val galleryManager = rememberGalleryManager() {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) { it?.toImageBitmap() }?:return@launch
            val fileName = withContext(Dispatchers.Default) { it?.getFileName() }
            val fileBytes = withContext(Dispatchers.Default) { it?.toByteArray() }?:return@launch
            onImageSelected.invoke(bitmap,fileName.handleNull(),fileBytes)
        }
    }
    if (imageSourceOptionDialog) {
        ImageSourceOptionDialog(onDismissRequest = {
            imageSourceOptionDialog = false
        }, onGalleryRequest = {
            imageSourceOptionDialog = false
            launchGallery = true
        }, onCameraRequest = {
            imageSourceOptionDialog = false
            launchCamera = true
        })
    }
    if (launchGallery) {
        if (getPlatform().isIOS){
            if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
                galleryManager.launch()
            } else {
                permissionsManager.askPermission(PermissionType.GALLERY)
            }
        }else{
            permissionsManager.isPermissionGrantedAndroid(PermissionType.GALLERY){
                if (it){
                    galleryManager.launch()
                }
            }
        }
        launchGallery = false
    }
    if (launchCamera) {
        if (getPlatform().isIOS){
            if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
                cameraManager.launch()
            } else {
                permissionsManager.askPermission(PermissionType.CAMERA)
            }
        }else{
            permissionsManager.isPermissionGrantedAndroid(PermissionType.CAMERA){
                if (it){
                    cameraManager.launch()
                }
            }
        }
        launchCamera = false
    }
    if (launchSetting) {
        permissionsManager.launchSettings()
        launchSetting = false
    }



    if (permissionRationalDialog) {
        AlertMessageDialog(title = "Permission Required",
            message = "To set your profile picture, please grant this permission. You can manage permissions in your device settings.",
            positiveButtonText = "Settings",
            negativeButtonText = "Cancel",
            onClick = {
                if (it){
                    permissionRationalDialog = false
                    launchSetting = true
                }else{
                    permissionRationalDialog = false
                }
            },
        )
    }

    Box(modifier = modifier.clickable(interactionSource = null,indication = null) {
        imageSourceOptionDialog = true
    })
}


@Composable
fun LocalMediaPickerWithTypeZeroDp(
    onClicked: Boolean,
    type:String,
    onImageSelected: (ByteArray, String, ImageBitmap?,type: MediaType) -> Unit,
    onDismiss: () -> Unit = {}
) {



    val coroutineScope = rememberCoroutineScope()
    var imageSourceOptionDialog by remember { mutableStateOf(value = false) }
    var launchCameraImage by remember { mutableStateOf(value = false) }
    var launchGalleryImage by remember { mutableStateOf(value = false) }

    var launchCameraVideo by remember { mutableStateOf(value = false) }
    var launchGalleryVideo by remember { mutableStateOf(value = false) }

    var launchPdfPicker by remember { mutableStateOf(value = false) }

    var launchExcelPicker by remember { mutableStateOf(value = false) }


    var launchSetting by remember { mutableStateOf(value = false) }
    var permissionRationalDialog by remember { mutableStateOf(value = false) }
    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.CAMERA -> launchCameraImage = true
                        PermissionType.GALLERY -> launchGalleryImage = true
                        else -> {}
                    }
                }

                else -> {
                    permissionRationalDialog = true
                }
            }
        }


    })


    val cameraManager = rememberCameraManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) { it?.toImageBitmap() }?:return@launch
            val fileName = withContext(Dispatchers.Default) { it?.getFileName() }
            val fileBytes = withContext(Dispatchers.Default) { it?.toByteArray() }?:return@launch
            onImageSelected.invoke(fileBytes,fileName.handleNull(),bitmap,
                MediaType.image)
        }
    }

    val galleryManager = rememberGalleryManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) { it?.toImageBitmap() }?:return@launch
            val fileName = withContext(Dispatchers.Default) { it?.getFileName() }
            val fileBytes = withContext(Dispatchers.Default) { it?.toByteArray() }?:return@launch
            onImageSelected.invoke(fileBytes,fileName.handleNull(),bitmap,
                MediaType.image)
        }
    }

    val videoCameraManager = rememberCameraManagerForVideo {
        coroutineScope.launch {
            val videoUri = it?.toByteArray()
            val fileName = it?.getFileName()
            val thumbNail = it?.getThumbnail()
            videoUri?.let { uri ->
                thumbNail?.let {
                    onImageSelected.invoke(uri,fileName.handleNull(),it,
                        MediaType.video)
                }
            }
        }
    }

    val videoGalleryManager = rememberGalleryManagerForVideo {
        coroutineScope.launch {
            val videoUri = it?.toByteArray()
            val fileName = it?.getFileName()
            val thumbNail = it?.getThumbnail()
            videoUri?.let { uri ->
                thumbNail?.let {
                    onImageSelected.invoke(uri,fileName.handleNull(),it,
                        MediaType.video)
                }
            }
        }
    }

    val pdfGalleryManager = rememberGalleryManagerForPdf {
        coroutineScope.launch {
            val videoUri = it?.toByteArray()
            val fileName = it?.getFileName()
            videoUri?.let { uri ->
                onImageSelected.invoke(uri,fileName.handleNull(),null,
                    MediaType.pdf)
            }
        }
    }

    val excelGalleryManager = rememberGalleryManagerForExcel {
        coroutineScope.launch {
            val bytes = it?.toByteArray()
            val fileName = it?.getFileName()
            bytes?.let { data ->
                onImageSelected.invoke(data, fileName.handleNull(), null, MediaType.excel)
            }
        }
    }



    if (imageSourceOptionDialog) {
        MediaSourcePicker(type = type,object : MediaPickerInterface {
            override fun dismissed() {
                imageSourceOptionDialog = false
                onDismiss()
            }
            override fun pickerImageCamera() {
                imageSourceOptionDialog = false
                launchCameraImage = true
                onDismiss()
            }
            override fun pickImageGallery() {
                imageSourceOptionDialog = false
                launchGalleryImage = true
                onDismiss()
            }

            override fun pickVideoGallery() {
                imageSourceOptionDialog = false
                launchGalleryVideo = true
                onDismiss()
            }

            override fun pickVideoCamera() {
                imageSourceOptionDialog = false
                launchCameraVideo=true
                onDismiss()
            }

            override fun pickPdf() {
                imageSourceOptionDialog = false
                launchPdfPicker = true
                onDismiss()
            }
            override fun pickExcel() {
                imageSourceOptionDialog = false
                launchExcelPicker = true
                onDismiss()
            }
        })
    }

    if (launchGalleryImage) {
        if (getPlatform().isIOS){
            if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
                galleryManager.launch()
            } else {
                permissionsManager.askPermission(PermissionType.GALLERY)
            }
        }else{
            permissionsManager.isPermissionGrantedAndroid(PermissionType.GALLERY){
                if (it){
                    galleryManager.launch()
                }
            }
        }
        launchGalleryImage = false
    }
    if (launchCameraImage) {
        if (getPlatform().isIOS){
            if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
                cameraManager.launch()
            } else {
                permissionsManager.askPermission(PermissionType.CAMERA)
            }
        }else{
            permissionsManager.isPermissionGrantedAndroid(PermissionType.CAMERA){
                if (it){
                    cameraManager.launch()
                }
            }
        }
        launchCameraImage = false
    }


    if (launchGalleryVideo) {
        if (getPlatform().isIOS){
            if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
                videoGalleryManager.launch()
            } else {
                permissionsManager.askPermission(PermissionType.GALLERY)
            }
        }else{
            permissionsManager.isPermissionGrantedAndroid(PermissionType.GALLERY){
                if (it){
                    videoGalleryManager.launch()
                }
            }
        }
        launchGalleryVideo = false
    }
    if (launchCameraVideo) {
        if (getPlatform().isIOS){
            if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
                videoCameraManager.launch()
            } else {
                permissionsManager.askPermission(PermissionType.CAMERA)
            }
        }else{
            permissionsManager.isPermissionGrantedAndroid(PermissionType.CAMERA){
                if (it){
                    videoCameraManager.launch()
                }
            }
        }
        launchCameraVideo = false
    }


    if (launchPdfPicker) {
        if (getPlatform().isIOS){
            if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
                pdfGalleryManager.launch()
            } else {
                permissionsManager.askPermission(PermissionType.GALLERY)
            }
        }else{
            permissionsManager.isPermissionGrantedAndroid(PermissionType.GALLERY){
                if (it){
                    pdfGalleryManager.launch()
                }
            }
        }
        launchPdfPicker = false
    }

    if (launchExcelPicker) {
        if (getPlatform().isIOS) {
            if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
                excelGalleryManager.launch()
            } else {
                permissionsManager.askPermission(PermissionType.GALLERY)
            }
        } else {
            permissionsManager.isPermissionGrantedAndroid(PermissionType.GALLERY) {
                if (it) {
                    excelGalleryManager.launch()
                }
            }
        }
        launchExcelPicker = false
    }



    if (launchSetting) {
        permissionsManager.launchSettings()
        launchSetting = false
    }

    if (permissionRationalDialog) {
        AlertMessageDialog(title = "Permission Required",
            message = "To set your profile picture, please grant this permission. You can manage permissions in your device settings.",
            positiveButtonText = "Settings",
            negativeButtonText = "Cancel",
            onClick = {
                if (it){
                    permissionRationalDialog = false
                    launchSetting = true
                }else{
                    permissionRationalDialog = false
                }
            },
        )
    }


    if (onClicked && !imageSourceOptionDialog){
        imageSourceOptionDialog = true
    }
}




enum class MediaType {
    image,
    video,
    pdf,
    excel,
    uploadedExcel,
    imageUploaded,
    videoUploaded,
    uploadedPdf,
    uploaded,
    dummyAdded
}


fun MediaType.getUploadTypeString(): String {
    return when(this) {
        MediaType.video -> "video/mp4"
        MediaType.pdf -> "application/pdf"
        MediaType.excel -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" // NEW
        else -> "image/jpeg"
    }
}

fun String.getUploadTypeMediaType():MediaType  {
    return when(this) {
        "video" -> MediaType.video
        "document" -> MediaType.pdf
        "excel" -> MediaType.excel
        else -> MediaType.image
    }
}

fun MediaType.getContentDisposition(index: Int): String {
    return when(this) {
        MediaType.video -> "filename=video_\"${index}\".mp4"
        MediaType.pdf -> "filename=pdf_\"${index}\".pdf"
        MediaType.excel -> "filename=excel_\"${index}\".xlsx"   // NEW
        else -> "filename=image_\"${index}\".jpg"
    }
}
@Composable
fun LocalNetworkImage(
    modifier: Modifier,
    url: String,
    contentScale: ContentScale = ContentScale.Crop,
    canZoom: Boolean = false,
    placeholder: DrawableResource? = null  // ✅ Optional custom placeholder
) {
    var showZoomedImage by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    // ✅ Use custom placeholder if provided, otherwise use default
    val placeholderPainter = painterResource(
        resource = placeholder ?: Res.drawable.dummy_profile
    )

    if (url.isEmpty() || url.contains("camera_placeholder.png")) {
        Image(
            painter = placeholderPainter,
            contentDescription = "Placeholder",
            modifier = modifier,
            contentScale = contentScale
        )
        return
    }

    AsyncImage(
        modifier = if (canZoom) {
            modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) { showZoomedImage = true }
        } else modifier,
        model = url,
        contentScale = contentScale,
        contentDescription = "NetworkImage",
        placeholder = placeholderPainter,
        error = placeholderPainter,
        onError = {
            println("Image load error: ${it.result.throwable.message}")
        }
    )

    if (showZoomedImage) {
        ShowZoomAbleImage(imageUrl = url) {
            showZoomedImage = false
        }
    }
}
@Composable
fun ShowZoomAbleImage(imageUrl: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize().aspectRatio(1f)
                .padding(16.dp).clip(RoundedCornerShape(16.dp)).border(border = BorderStroke(1.dp, color = banner2Clr), shape = RoundedCornerShape(16.dp))
        ) {
            LocalNetworkImageZoomAble(imageUrl)
            Box(modifier = Modifier.padding(10.dp).align(Alignment.TopEnd)) {
//                TopButtonWithBackground(
//                    modifier = Modifier.size(30.dp),
//                    Res.drawable.vector_notification
//                ) {
//                    onDismiss.invoke()
//                }
            }
        }
    }
}
@Composable
fun LocalNetworkImageZoomAble(url:String){
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f) // Make it a square
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1f, 5f) // Zoom limits
                    offset += pan
                }
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                ),
            model = url,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            placeholder = painterResource(resource = Res.drawable.dummy_profile),
            error = painterResource(resource = Res.drawable.dummy_profile),
        )
    }
}
