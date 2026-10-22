package com.lessonmaker.app.shared.textkit.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

import com.lessonmaker.app.shared.textkit.theme.TextKitTheme
import com.lessonmaker.app.shared.textkit.ui.listlayout.TextKitViewerBlocks
import com.lessonmaker.app.shared.textkit.ui.listlayout.withListLineMetrics
import com.lessonmaker.app.shared.textkit.ui.state.TextKitState

@Composable
fun TextKitViewer(
    state: TextKitState,
    modifier: Modifier = Modifier
) {
    val highlightColor = TextKitTheme.colors.highlight
    SideEffect { state.setThemeHighlightColor(highlightColor) }

    TextKitViewerBlocks(
        blocks = state.viewerBlocks,
        textStyle = TextStyle(color = TextKitTheme.colors.onSurface).withListLineMetrics(),
        modifier = modifier,
    )
}
