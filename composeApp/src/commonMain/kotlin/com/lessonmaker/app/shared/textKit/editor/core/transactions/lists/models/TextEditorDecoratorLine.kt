package com.lessonmaker.app.shared.textkit.editor.core.transactions.lists.models

import com.lessonmaker.app.shared.textkit.editor.components.TextEditorListItem
import com.lessonmaker.app.shared.textkit.editor.core.piecetable.models.RichPiece

internal data class TextEditorDecoratorLine(
    val piece: RichPiece,
    val offsetInDocument: Int,
    val type: TextEditorListItem
)
