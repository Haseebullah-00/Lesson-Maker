package com.lessonmaker.app.shared.textkit.editor.core.converters.models

import com.lessonmaker.app.shared.textkit.editor.core.piecetable.models.RichPiece


internal data class ListMultiPieceParagraph(val paragraphs: List<ListPieceParagraph>) {
    internal data class ListPieceParagraph(
        val richPiece: RichPiece,
        val offsetInDocument: Int,
        val newRichPiece: RichPiece = richPiece,
    )
}
