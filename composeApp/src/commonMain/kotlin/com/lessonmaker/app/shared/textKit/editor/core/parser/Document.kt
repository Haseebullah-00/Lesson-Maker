package com.lessonmaker.app.shared.textkit.editor.core.parser

import kotlinx.serialization.Serializable

@Serializable
internal data class TextEditorDocument(val content: List<BaseParagraph> = emptyList()) {
    val type: String = RootType
}

internal val EmptyDocument = TextEditorDocument()
internal const val RootType = "doc"
