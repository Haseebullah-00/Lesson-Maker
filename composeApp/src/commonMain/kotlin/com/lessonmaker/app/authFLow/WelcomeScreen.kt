package com.lessonmaker.app.authFLow

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.MainButton
import com.lessonmaker.app.common.MainGradientBg
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.navigationControler.Screen
import com.lessonmaker.app.shared.textkit.editor.core.parser.EmbedTypes
import com.lessonmaker.app.shared.textkit.editor.models.TextKitTrigger
import com.lessonmaker.app.shared.textkit.editor.models.createTextKitConfiguration
import com.lessonmaker.app.shared.textkit.editor.utils.DocumentUtils
import com.lessonmaker.app.shared.textkit.ui.TextKitAlignPopup
import com.lessonmaker.app.shared.textkit.ui.TextKitColorsPopup
import com.lessonmaker.app.shared.textkit.ui.TextKitEditor
import com.lessonmaker.app.shared.textkit.ui.TextKitEmbedPopup
import com.lessonmaker.app.shared.textkit.ui.TextKitFormattingBar
import com.lessonmaker.app.shared.textkit.ui.TextKitLinkPopup
import com.lessonmaker.app.shared.textkit.ui.TextKitScreen
import com.lessonmaker.app.shared.textkit.ui.TextKitSlashCommandPopup
import com.lessonmaker.app.shared.textkit.ui.TextKitTokenPopup
import com.lessonmaker.app.shared.textkit.ui.model.TextKitCommand
import com.lessonmaker.app.shared.textkit.ui.model.TextKitTokenSuggestion
import com.lessonmaker.app.shared.textkit.ui.state.rememberTextKitFormattingBarState
import com.lessonmaker.app.shared.textkit.ui.state.rememberTextKitState
import com.lessonmaker.app.shared.textkit.ui.utils.TextKitPickerPallete
import com.lessonmaker.app.theme.Medium
import com.lessonmaker.app.theme.PrimaryColor
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize16
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.TextSize30
import com.lessonmaker.app.theme.TextSize32
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.backClr
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.buttonBackClr
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.painterResource
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.ic_youtube
import lessonmaker.composeapp.generated.resources.welcome_screen_logo


@Composable
fun WelcomeScreen(newRouts: (String) -> Unit) {
    ////

    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        MainGradientBg()
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
                Spacer(modifier = Modifier.height(95.dp))

                Image(
                    painter = painterResource(Res.drawable.welcome_screen_logo),
                    contentDescription = "Welcome Logo",
                    modifier = Modifier
                        .height(91.dp)
                )

                Spacer(modifier = Modifier.height(70.dp))

                Text(
                    text = "Welcome!",
                    color = White,
                    style = TextSize32().Medium()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Don’t have an account?",
                    color = Color.White,
                    style = TextSize18().Regular()
                )

                Spacer(modifier = Modifier.height(20.dp))
                MainButton(
                    buttonBackClr,
                    Color.Transparent,
                    "Join",
                    60.dp,
                    0.dp,
                    onClick = {
                        newRouts.invoke(Screen.AgeAskingScreen.route)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                MainButton(
                    Color.Transparent,
                    White,
                    "Sign In",
                    60.dp,
                    1.dp,
                    onClick = {
                        newRouts.invoke(Screen.LoginScreenFlow.route)
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween

                )
                {

                    Text(
                        text = "Continue as Guest",
                        color = White,
                        style = TextSize18().bold(),
                        modifier = Modifier
                            .clickable(interactionSource = MutableInteractionSource(),
                                indication = null
                            )
                            {
                                newRouts.invoke(Screen.DashboardScreen.route)

                            }

                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        )
                        {
                            Image(
                                painter = painterResource(Res.drawable.ic_youtube),
                                contentDescription = "youtube Icon",
                            )
                            Text(
                                text = "/@ettanehsan",
                                color = Color.White,
                                style = TextSize18().Medium()
                            )

                        }

                    }

                    Text(
                        text = "Privacy Policy",
                        color = White,
                        style = TextSize16().bold(),
                        modifier = Modifier
                            .clickable(interactionSource = MutableInteractionSource(),
                                indication = null)
                            {


                            }
                    )

                }
                Spacer(modifier = Modifier.height(30.dp))


            }
        }






    }
}




//
//
//package com.lessonmaker.app.authFLow
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.imePadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Button
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.lessonmaker.app.common.dialogue.MediaPickerInterface
//import com.lessonmaker.app.common.dialogue.MediaSourcePicker
//import com.lessonmaker.app.common.lesson.imageEmbedJson
//import com.lessonmaker.app.common.lesson.uploadLessonImage
//import com.lessonmaker.app.common.widgets.LocalMediaPickerWithTypeZeroDp
//import com.lessonmaker.app.common.widgets.MediaType
//import com.lessonmaker.app.shared.media.rememberGalleryManager
//import com.lessonmaker.app.shared.rememberCameraManager
//
//import com.lessonmaker.app.shared.textkit.editor.core.parser.EmbedTypes
//import com.lessonmaker.app.shared.textkit.editor.models.TextKitTrigger
//import com.lessonmaker.app.shared.textkit.editor.models.createTextKitConfiguration
//import com.lessonmaker.app.shared.textkit.editor.utils.DocumentUtils
//import com.lessonmaker.app.shared.textkit.ui.TextKitAlignPopup
//import com.lessonmaker.app.shared.textkit.ui.TextKitColorsPopup
//import com.lessonmaker.app.shared.textkit.ui.TextKitEditor
//import com.lessonmaker.app.shared.textkit.ui.TextKitEmbedPopup
//import com.lessonmaker.app.shared.textkit.ui.TextKitFormattingBar
//import com.lessonmaker.app.shared.textkit.ui.TextKitLinkPopup
//import com.lessonmaker.app.shared.textkit.ui.TextKitScreen
//import com.lessonmaker.app.shared.textkit.ui.TextKitSlashCommandPopup
//import com.lessonmaker.app.shared.textkit.ui.TextKitTokenPopup
//import com.lessonmaker.app.shared.textkit.ui.model.TextKitCommand
//import com.lessonmaker.app.shared.textkit.ui.model.TextKitTokenSuggestion
//import com.lessonmaker.app.shared.textkit.ui.state.rememberTextKitFormattingBarState
//import com.lessonmaker.app.shared.textkit.ui.state.rememberTextKitState
//import com.lessonmaker.app.shared.textkit.ui.utils.TextKitPickerPallete
//import kotlinx.coroutines.launch
//
//private val DEMO_TABLE_JSON = """
//    {"type":"table","content":[
//      {"type":"tableRow","content":[
//        {"type":"tableHeader","attrs":{"colspan":1,"rowspan":1,"colwidth":null},"content":[{"type":"paragraph","content":[{"type":"text","text":"Nombre"}]}]},
//        {"type":"tableHeader","attrs":{"colspan":1,"rowspan":1,"colwidth":null},"content":[{"type":"paragraph","content":[{"type":"text","text":"Edad"}]}]}
//      ]},
//      {"type":"tableRow","content":[
//        {"type":"tableCell","attrs":{"colspan":1,"rowspan":1,"colwidth":null},"content":[{"type":"paragraph","content":[{"type":"text","text":"Juan"}]}]},
//        {"type":"tableCell","attrs":{"colspan":1,"rowspan":1,"colwidth":null},"content":[{"type":"paragraph","content":[{"type":"text","text":"30"}]}]}
//      ]}
//    ]}
//""".trimIndent()
//
//private const val DEMO_DOCUMENT_JSON =
//    """{"type":"document","attrs":{"name":"Reporte Q3.pdf","url":"https://example.com/reporte-q3.pdf"}}"""
//
//// Candidates for the '@' mention trigger
//private val sampleUsers = listOf(
//    TextKitTokenSuggestion(id = "111", label = "Jorge Rodriguez"),
//    TextKitTokenSuggestion(id = "222", label = "Ada Lovelace"),
//    TextKitTokenSuggestion(id = "333", label = "Alan Turing"),
//)
//
//// Candidates for the '#' hashtag trigger
//private val sampleTags = listOf(
//    TextKitTokenSuggestion(id = "1", label = "kotlin"),
//    TextKitTokenSuggestion(id = "2", label = "compose"),
//    TextKitTokenSuggestion(id = "3", label = "android"),
//)
//
//// Commands for the '/' slash trigger
//private val sampleCommands = listOf(
//    TextKitCommand.heading(1),
//    TextKitCommand.heading(2),
//    TextKitCommand.heading(3),
//    TextKitCommand.bulletList(),
//    TextKitCommand.orderedList(),
//    TextKitCommand.custom(id = "cmd-date", label = "Insert date") { it.insertText("2026-07-15") },
//)
//
///** Reads every image url out of toJson(), in document order. */
//fun extractImageUrls(json: String): List<String> {
//    val key = "\"type\":\"image\",\"attrs\":{\"url\":\""
//    val urls = mutableListOf<String>()
//    var from = 0
//    while (true) {
//        val s = json.indexOf(key, from)
//        if (s < 0) break
//        val start = s + key.length
//        var end = start
//        while (end < json.length && !(json[end] == '"' && json[end - 1] != '\\')) end++
//        urls += json.substring(start, end).replace("\\/", "/")
//        from = end
//    }
//    return urls
//}
//
///** TextKit's toHtml() leaves <img src=""> empty; refill it from the JSON (same order). */
//fun fixImageSrc(html: String, json: String): String {
//    val urls = extractImageUrls(json)
//    if (urls.isEmpty()) return html
//    val marker = "<img src=\"\""
//    val sb = StringBuilder()
//    var from = 0
//    var i = 0
//    while (i < urls.size) {
//        val idx = html.indexOf(marker, from)
//        if (idx < 0) break
//        sb.append(html, from, idx).append("<img src=\"").append(urls[i++]).append('"')
//        from = idx + marker.length
//    }
//    sb.append(html, from, html.length)
//    return sb.toString()
//}
//
//@Composable
//fun WelcomeScreen(newRouts: (String) -> Unit) {
//    val scope = rememberCoroutineScope()
//    val barState = rememberTextKitFormattingBarState(colors = TextKitPickerPallete.DefaultPallete)
//    val configuration = remember {
//        createTextKitConfiguration {
//            addTrigger { TextKitTrigger.TextKitMentionTrigger() }
//            addTrigger { TextKitTrigger.TextKitHashtagTrigger() }
//            addTrigger { TextKitTrigger.TextKitSlashTrigger() }
//        }
//    }
//    val state =
//        rememberTextKitState(json = DocumentUtils.complexJsonV1, configuration = configuration)
//
//    var showPicker by remember { mutableStateOf(false) }
//    var isUploading by remember { mutableStateOf(false) }
//
//    LaunchedEffect(state.lastMarks, state.lastListItem, state.lastEmbedType) {
//        barState.syncFrom(state.lastMarks, state.lastListItem, state.lastEmbedType)
//    }
//
//    // ---- Your picker: stays invisible until showImagePicker = true ----
//
//
//
//    TextKitScreen(
//        modifier = Modifier
//            .fillMaxSize()
//
//            .padding(16.dp),
//        darkTheme = false
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            TextKitFormattingBar(
//                barState = barState,
//                onBoldClick = state::applyBold,
//                onItalicClick = state::applyItalic,
//                onUnderlineClick = state::applyUnderline,
//                onStrikeThroughClick = state::applyStrikeThrough,
//                onHighlightClick = state::applyHighlight,
//                onLinkClick = { state.applyLink() },
//                onImageClick = { showPicker = true },      // opens your picker
//                onTableClick = {
//                    state.insertEmbed(EmbedTypes.Table, DEMO_TABLE_JSON, "📊 Tabla")
//                },
//                onDocumentClick = {
//                    state.insertEmbed(EmbedTypes.Document, DEMO_DOCUMENT_JSON, "📄 Documento")
//                },
//                onTextAndColorClick = { state.openColorPicker(it) },
//                onOrderedListClick = state::toggleOrderedList,
//                onBulletedListClick = state::toggleUnorderedList,
//                onBlockquoteClick = { state.applyBlockquote() },
//                isBlockquoteActive = state.isBlockquoteActive,
//                onTextAlignClick = { state.openAlignPicker(it) },
//                onUndoClick = { state.undo() },
//                onRedoClick = { state.redo() },
//                canUndo = state.canUndo,
//                canRedo = state.canRedo
//            )
//
//            Button(
//                onClick = {
//                    println("========== TEXTKIT EDITOR DATA ==========")
//                    println("----- JSON -----")
//                    println(state.toJson())
//                    println("----- HTML -----")
//
//                   println(state.toHtml())
//
//
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Print Editor Data")
//            }
//
//            Spacer(Modifier.size(6.dp))
//
//            Box(modifier = Modifier
//                .weight(1f)
//
//            ) {
//                TextKitEditor(
//                    state = state,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(10.dp)
//                )
//                // Tap the image -> popup (with Remove)
//                TextKitEmbedPopup(state = state)
//                TextKitColorsPopup(state = state, colors = barState.colors)
//                TextKitAlignPopup(state = state)
//                TextKitLinkPopup(
//                    state = state,
//                    onEdit = { link ->
//                        state.updateLinkText(
//                            newText = link.text,
//                            url = link.url,
//                            range = link.range
//                        )
//                    },
//                    onRemove = { link -> state.removeLink(link.range) },
//                )
//                TextKitTokenPopup(state = state) { trigger ->
//                    when (trigger) {
//                        is TextKitTrigger.TextKitHashtagTrigger -> sampleTags
//                        else -> sampleUsers
//                    }
//                }
//                TextKitSlashCommandPopup(state = state, commands = sampleCommands)
//
//                if (isUploading) {
//                    CircularProgressIndicator(Modifier.align(Alignment.Center))
//                }
//            }
//        }
//    }
//    fun insertPickedImage(bytes: ByteArray?, fileName: String) {
//        if (bytes == null) return
//        scope.launch {
//            isUploading = true
//            try {
//                val url = uploadLessonImage(fileName, bytes)
//                state.insertEmbed(EmbedTypes.Image, imageEmbedJson(url), "🖼")
//                val v = state.textFieldValue
//                if (v.selection.max >= v.text.length) state.insertText("\n")
//            } finally { isUploading = false }
//        }
//    }
//
//
//    /** TextKit's toHtml() leaves <img src=""> empty; refill it from toJson() (same order). */
//
//// Same idea as rememberGalleryManagerForExcel — image versions
//    val galleryManager = rememberGalleryManager { shared ->
//        if (shared == null) return@rememberGalleryManager
//        insertPickedImage(shared.toByteArray(), "image.jpg")
//    }
//    val cameraManager = rememberCameraManager { shared ->
//        if (shared == null) return@rememberCameraManager
//        insertPickedImage(shared.toByteArray(), "camera.jpg")
//    }
//
//
//
//// replace the LocalMediaPickerWithTypeZeroDp(...) block with:
//    if (showPicker) {
//        MediaSourcePicker(
//            type = "image",
//            mediaPickerInterface = object : MediaPickerInterface {
//                override fun pickImageGallery() { showPicker = false; galleryManager.launch() }
//                override fun pickerImageCamera() { showPicker = false; cameraManager.launch() }
//                override fun pickExcel() {}
//                override fun pickPdf() {}
//                override fun pickVideoGallery() {}
//                override fun pickVideoCamera() {}
//                override fun dismissed() { showPicker = false }
//            }
//        )
//    }
//}
