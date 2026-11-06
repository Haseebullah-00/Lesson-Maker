package com.lessonmaker.app.shared.textkit.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle

import com.lessonmaker.app.shared.textkit.theme.TextKitTheme
import com.lessonmaker.app.shared.textkit.ui.listlayout.TextKitViewerBlocks
import com.lessonmaker.app.shared.textkit.ui.listlayout.withListLineMetrics
import com.lessonmaker.app.shared.textkit.ui.state.TextKitState

//@Composable
//fun TextKitViewer(
//    state: TextKitState,
//    modifier: Modifier = Modifier
//) {
//    val highlightColor = TextKitTheme.colors.highlight
//    SideEffect { state.setThemeHighlightColor(highlightColor) }
//
//    TextKitViewerBlocks(
//        blocks = state.viewerBlocks,
//        textStyle = TextStyle(color = TextKitTheme.colors.onSurface).withListLineMetrics(),
//        modifier = modifier,
//    )
//}

@Composable
fun TextKitViewer(
    state: TextKitState,
    modifier: Modifier = Modifier,
    onYouTubeClick: (videoId: String, parts: List<YouTubeLinkUtils.Segment>) -> Unit = { _, _ -> },
) {
    val highlightColor = TextKitTheme.colors.highlight
    val uriHandler = LocalUriHandler.current
    SideEffect {
        state.setThemeHighlightColor(highlightColor)
        state.onUrlClicked = { url, _, _ ->
            val id = YouTubeLinkUtils.videoId(url)
            if (id != null) onYouTubeClick(id, YouTubeLinkUtils.readSegments(url))
            else runCatching { uriHandler.openUri(url) }
        }
    }

    TextKitViewerBlocks(
        blocks = state.viewerBlocks,
        textStyle = TextStyle(color = TextKitTheme.colors.onSurface).withListLineMetrics(),
        modifier = modifier,
    )
}
