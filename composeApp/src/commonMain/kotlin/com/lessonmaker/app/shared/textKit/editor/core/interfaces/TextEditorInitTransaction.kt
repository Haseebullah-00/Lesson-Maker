package com.lessonmaker.app.shared.textkit.editor.core.interfaces

import com.lessonmaker.app.shared.textkit.editor.core.models.TextEditorDocumentModel
import com.lessonmaker.app.shared.textkit.editor.core.models.TextEditorModel
import com.lessonmaker.app.shared.textkit.editor.models.TextKitConfiguration

internal interface TextEditorInitTransaction : TextEditorGetInfoTransaction<TextEditorModel>,
    TextEditorEditInfoTransaction<TextEditorModel> {

    val text: String

    val json: String

    fun loadWith(initialJson: String, isViewer: Boolean)

    fun fromDocument(document: TextEditorDocumentModel)
}
