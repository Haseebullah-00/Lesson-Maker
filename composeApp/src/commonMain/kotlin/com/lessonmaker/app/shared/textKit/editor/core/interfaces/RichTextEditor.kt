package com.lessonmaker.app.shared.textkit.editor.core.interfaces

import com.lessonmaker.app.shared.textkit.editor.core.models.MultiPieceParagraph
import com.lessonmaker.app.shared.textkit.editor.core.models.TextEditorDocumentModel
import com.lessonmaker.app.shared.textkit.editor.core.models.TextEditorModel
import com.lessonmaker.app.shared.textkit.editor.core.parser.Mark
import com.lessonmaker.app.shared.textkit.editor.core.piecetable.models.RichPieceTransaction

internal interface RichTextEditor<Document, Model> : TextEditor<Document, Model> {

    val annotatedText: List<Model>

    fun updateMarks(transaction: RichPieceTransaction): Boolean

    fun updateMarks(transactions: List<RichPieceTransaction>): Boolean

    fun getTransactionMarks(
        leftModel: Model?,
        centralModel: Model,
        rightModel: Model?,
        offset: Int,
        length: Int,
        marks: Set<Mark>
    ): RichPieceTransaction

    fun getTextAt(offset: Int): Model

    fun getLineContent(start: Int, end: Int): MultiPieceParagraph

    fun getLineContentWithNeighborListItems(start: Int, end: Int): MultiPieceParagraph
}
