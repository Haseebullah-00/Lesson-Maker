package com.lessonmaker.app.shared.textkit.editor.core.transactions.marks.models

import androidx.compose.ui.text.TextRange
import com.lessonmaker.app.shared.textkit.editor.core.models.TextEditorModel
import com.lessonmaker.app.shared.textkit.editor.utils.fastForEach
import com.lessonmaker.app.shared.textkit.editor.utils.isLineBreak

internal data class MultiParagraph(
    val range: TextRange,
    val texts: List<TextEditorModel>
) {
    private val fullText
        get() = buildString {
            texts.fastForEach { append(it.text) }
        }

    internal fun isBreakLine() = fullText.isLineBreak()

    internal fun isEmpty() = fullText.isLineBreak() || fullText.isEmpty()

    internal fun isNotEmpty() = !fullText.isLineBreak() && fullText.isNotEmpty()
}
