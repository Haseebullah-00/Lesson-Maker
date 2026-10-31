package com.lessonmaker.app.mainScreen.studentFlow.editAccess.lessons.editLesson


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.MainButtonNew
import com.lessonmaker.app.common.dialogue.MediaPickerInterface
import com.lessonmaker.app.common.dialogue.MediaSourcePicker
import com.lessonmaker.app.common.lesson.imageEmbedJson
import com.lessonmaker.app.common.lesson.uploadLessonImage
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.mainScreen.studentFlow.StudentDummyStore
import com.lessonmaker.app.mainScreen.studentFlow.StudentEditStore
import com.lessonmaker.app.mainScreen.studentFlow.editAccess.lessons.StudentInfoBanner

import com.lessonmaker.app.shared.media.rememberGalleryManager
import com.lessonmaker.app.shared.rememberCameraManager
import com.lessonmaker.app.shared.textkit.editor.core.parser.EmbedTypes
import com.lessonmaker.app.shared.textkit.editor.models.createTextKitConfiguration
import com.lessonmaker.app.shared.textkit.editor.utils.DocumentUtils
import com.lessonmaker.app.shared.textkit.theme.TextKitTheme
import com.lessonmaker.app.shared.textkit.ui.TextKitAlignPopup
import com.lessonmaker.app.shared.textkit.ui.TextKitColorsPopup
import com.lessonmaker.app.shared.textkit.ui.TextKitEditor
import com.lessonmaker.app.shared.textkit.ui.TextKitEmbedPopup
import com.lessonmaker.app.shared.textkit.ui.TextKitFormattingBar
import com.lessonmaker.app.shared.textkit.ui.TextKitLinkPopup
import com.lessonmaker.app.shared.textkit.ui.state.rememberTextKitFormattingBarState
import com.lessonmaker.app.shared.textkit.ui.state.rememberTextKitState
import com.lessonmaker.app.shared.textkit.ui.utils.TextKitPickerPallete
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.greyE3
import kotlinx.coroutines.launch

@Composable
fun StudentEditLessonScreenFlow(
    lessonId: String,
    courseCode: String,
    dashboardViewModel: DashboardViewModel,
    dashboardRoutes: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val lessonTitle = remember(lessonId) { StudentDummyStore.lesson(lessonId)?.title ?: "Lesson" }
    val teacher = remember(courseCode) { StudentEditStore.teacherOf(courseCode) }

    val barState = rememberTextKitFormattingBarState(colors = TextKitPickerPallete.DefaultPallete)
    val configuration = remember { createTextKitConfiguration { } }
    // TODO: load the real lesson JSON for lessonId instead of the sample document
    val state = rememberTextKitState(json = DocumentUtils.complexJsonV1, configuration = configuration)

    var showPicker by remember { mutableStateOf(false) }
    var isUploading by remember { mutableStateOf(false) }

    LaunchedEffect(state.lastMarks, state.lastListItem, state.lastEmbedType) {
        barState.syncFrom(state.lastMarks, state.lastListItem, state.lastEmbedType)
    }

    TextKitTheme(darkTheme = false) {
        Column(modifier = Modifier.fillMaxSize().background(White).imePadding()) {
            StudentInfoBanner(
                text = "Changes go to $teacher for approval. The lesson stays unchanged until then.",
                square = true
            )

            TextKitFormattingBar(
                barState = barState,
                onBoldClick = state::applyBold,
                onItalicClick = state::applyItalic,
                onUnderlineClick = state::applyUnderline,
                onStrikeThroughClick = state::applyStrikeThrough,
                onHighlightClick = state::applyHighlight,
                onLinkClick = { state.applyLink() },
                onImageClick = { showPicker = true },
                onTableClick = { },
                onDocumentClick = { },
                onTextAndColorClick = { state.openColorPicker(it) },
                onHeadingClick = { level, selected -> state.applyHeading(if (selected) level else 0) },
                onOrderedListClick = state::toggleOrderedList,
                onBulletedListClick = state::toggleUnorderedList,
                onBlockquoteClick = { state.applyBlockquote() },
                isBlockquoteActive = state.isBlockquoteActive,
                onTextAlignClick = { state.openAlignPicker(it) },
                onUndoClick = { state.undo() },
                onRedoClick = { state.redo() },
                canUndo = state.canUndo,
                canRedo = state.canRedo
            )

            Box(modifier = Modifier.weight(1f)) {
                TextKitEditor(
                    state = state,
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 16.dp)
                )
                TextKitEmbedPopup(state = state)
                TextKitColorsPopup(state = state, colors = barState.colors)
                TextKitAlignPopup(state = state)
                TextKitLinkPopup(
                    state = state,
                    onEdit = { link ->
                        state.updateLinkText(newText = link.text, url = link.url, range = link.range)
                    },
                    onRemove = { link -> state.removeLink(link.range) },
                )
                if (isUploading) CircularProgressIndicator(Modifier.align(Alignment.Center))
            }

            Column(modifier = Modifier.fillMaxWidth().background(White)) {
                Box(Modifier.fillMaxWidth().height(1.dp).background(greyE3))
                Box(modifier = Modifier.padding(16.dp)) {
                    MainButtonNew(heading = "Send Edit Request") {
                        // TODO: send state.toHtml() / state.toJson() to the API
                        StudentEditStore.sendRequest(courseCode, lessonTitle)

                    }
                }
            }
        }
    }

    fun insertPickedImage(bytes: ByteArray?, fileName: String) {
        if (bytes == null) return
        scope.launch {
            isUploading = true
            try {
                val url = uploadLessonImage(fileName, bytes)
                state.insertEmbed(EmbedTypes.Image, imageEmbedJson(url), "🖼")
                val v = state.textFieldValue
                if (v.selection.max >= v.text.length) state.insertText("\n")
            } finally { isUploading = false }
        }
    }

    val galleryManager = rememberGalleryManager { shared ->
        if (shared == null) return@rememberGalleryManager
        insertPickedImage(shared.toByteArray(), "image.jpg")
    }
    val cameraManager = rememberCameraManager { shared ->
        if (shared == null) return@rememberCameraManager
        insertPickedImage(shared.toByteArray(), "camera.jpg")
    }

    if (showPicker) {
        MediaSourcePicker(
            type = "image",
            mediaPickerInterface = object : MediaPickerInterface {
                override fun pickImageGallery() { showPicker = false; galleryManager.launch() }
                override fun pickerImageCamera() { showPicker = false; cameraManager.launch() }
                override fun pickExcel() {}
                override fun pickPdf() {}
                override fun pickVideoGallery() {}
                override fun pickVideoCamera() {}
                override fun dismissed() { showPicker = false }
            }
        )
    }
}