package com.lessonmaker.app.shared.textkit.editor.core.transactions.lists.models

internal data class TextEditorListItemTransaction(
    val offsetInDocument: Int,
    val type: TextEditorDecoratorTransactionType
)
