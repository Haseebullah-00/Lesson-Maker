package com.lessonmaker.app.common.htmlViewer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.dialogue.playerDialogue.YouTubePlayRequest
import com.lessonmaker.app.common.dialogue.playerDialogue.YouTubePlayerDialog
import com.lessonmaker.app.shared.textkit.editor.models.createTextKitConfiguration
import com.lessonmaker.app.shared.textkit.theme.TextKitTheme
import com.lessonmaker.app.shared.textkit.ui.TextKitViewer
import com.lessonmaker.app.shared.textkit.ui.YouTubeLinkUtils
import com.lessonmaker.app.shared.textkit.ui.state.rememberTextKitState


/**
 * Shows the lesson HTML returned by the API (read-only) for students.
 * Tap a YouTube link -> plays the saved parts in a popup; any other link -> browser.
 */
@Composable
fun StudentLessonHtmlViewer(
    lessonHtml: String,
    modifier: Modifier = Modifier,
    scrollable: Boolean = true,          // false when the parent screen already scrolls
    contentPadding: Dp = 16.dp,
) {
    val config = remember { createTextKitConfiguration { viewerMode { true } } }
    val state = rememberTextKitState(json = "{}", configuration = config)
    var playRequest by remember { mutableStateOf<YouTubePlayRequest?>(null) }

    LaunchedEffect(lessonHtml) { state.importHtml(lessonHtml) }

    TextKitTheme(darkTheme = false) {
        TextKitViewer(
            state = state,
            modifier = modifier
                .fillMaxWidth()
                .then(if (scrollable) Modifier.verticalScroll(rememberScrollState()) else Modifier)
                .padding(contentPadding),
            onYouTubeClick = { videoId, parts -> playRequest = YouTubePlayRequest(videoId, parts) },
        )
    }

    YouTubePlayerDialog(request = playRequest, onClose = { playRequest = null })
}