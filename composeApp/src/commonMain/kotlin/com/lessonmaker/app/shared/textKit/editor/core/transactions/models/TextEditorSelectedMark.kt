package com.lessonmaker.app.shared.textkit.editor.core.transactions.models

import com.lessonmaker.app.shared.textkit.editor.components.TextEditorDecoratorItem
import com.lessonmaker.app.shared.textkit.editor.components.TextEditorListItem
import com.lessonmaker.app.shared.textkit.editor.core.parser.Mark

data class TextEditorSelectedMark(
    private val listItemSelectedValue: TextEditorDecoratorItem? = null,
    val marks: Set<Mark> = emptySet()
) {
    val listItemSelected get() = listItemSelectedValue ?: TextEditorListItem.None

    companion object {
        val NONE = TextEditorSelectedMark()
    }
}