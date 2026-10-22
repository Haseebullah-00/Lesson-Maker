package com.lessonmaker.app.shared.textkit.editor.core.converters

import com.lessonmaker.app.shared.textkit.editor.core.converters.inCanonicalOrder
import com.lessonmaker.app.shared.textkit.editor.core.models.TextEditorModel
import com.lessonmaker.app.shared.textkit.editor.core.parser.BaseText
import com.lessonmaker.app.shared.textkit.editor.core.parser.Hashtag
import com.lessonmaker.app.shared.textkit.editor.core.parser.HashtagType
import com.lessonmaker.app.shared.textkit.editor.core.parser.Mention
import com.lessonmaker.app.shared.textkit.editor.core.parser.MentionType
import com.lessonmaker.app.shared.textkit.editor.core.parser.Text
import com.lessonmaker.app.shared.textkit.editor.core.parser.TextStyleMark
import com.lessonmaker.app.shared.textkit.editor.core.piecetable.models.RichToken
import com.lessonmaker.app.shared.textkit.editor.utils.removeLineBreakSuffix

/**
 * Converts a single flat [TextEditorModel] back into its inline [BaseText] node when serializing the
 * piece table to a document. A piece carrying a [RichToken] becomes the matching atomic node (its
 * id/label come from the token metadata, never from the visible text); everything else becomes a
 * [Text].
 *
 * Callers are responsible for filtering out decorator and line-break pieces beforehand.
 */
internal fun TextEditorModel.toInlineNode(): BaseText {
    val token = piece.token
    return token?.toInlineNode(piece.marks.inCanonicalOrder())
        ?: Text(text = text.removeLineBreakSuffix(), marks = piece.marks.inCanonicalOrder())
}

/**
 * Serializes marks in the order a load produces them: `recreateMarks` rebuilds every set as
 * "other marks, then textStyle", while live formatting appends whichever mark was toggled most
 * recently — so a set that carries textStyle mid-way would change order across a reload.
 */
private fun Set<com.lessonmaker.app.shared.textkit.editor.core.parser.Mark>.inCanonicalOrder(): Set<com.lessonmaker.app.shared.textkit.editor.core.parser.Mark> {
    val (styleMarks, otherMarks) = partition { it is TextStyleMark }
    return (otherMarks + styleMarks).toSet()
}

/** Rebuilds the concrete inline node for [RichToken.type]; unknown types fall back to a [Mention]. */
private fun RichToken.toInlineNode(marks: Set<com.lessonmaker.app.shared.textkit.editor.core.parser.Mark>): BaseText =
    when (type) {
        HashtagType.Hashtag -> Hashtag(attrs = attrs, marks = marks)
        MentionType.Mention -> Mention(attrs = attrs, marks = marks)
        else -> Mention(attrs = attrs, marks = marks)
    }
