package com.lessonmaker.app.shared.textKit.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.lessonmaker.app.shared.textkit.editor.core.parser.embedUrlOf
import com.lessonmaker.app.shared.textkit.editor.core.transactions.models.TextEditorItem
import com.lessonmaker.app.shared.textkit.editor.core.transactions.models.TextEditorParagraph
import com.lessonmaker.app.shared.textkit.theme.TextKitTheme
import com.lessonmaker.app.shared.textkit.ui.state.TextKitState
import kotlin.math.roundToInt

/**
 * Inline images in the editor.
 *
 * How it works:
 *  1. The paragraph that holds an image embed gets a tall line height (see ListItemEditorTransform),
 *     and its placeholder text is made transparent.
 *  2. [EmbedImageOverlay] draws the real image on top of that empty tall line.
 *  3. The viewer (students) uses [ViewerImageBlock].
 */

/** Height (in sp) reserved for an inline image line in the editor. Change this to make images taller/shorter. */
internal const val EMBED_IMAGE_LINE_HEIGHT_SP = 140

/** True when this item is an embed whose JSON "type" is "image". Only the start of the payload is inspected (cheap even for big data URLs). */
internal fun TextEditorItem.isImageEmbed(): Boolean {
    val payload = embedPayload ?: return false
    return payload.take(300).filterNot { it.isWhitespace() }.contains("\"type\":\"image\"")
}

internal fun TextEditorParagraph.imageEmbedChild(): TextEditorItem? =
    children.firstOrNull { it.isImageEmbed() }

internal class ImageEmbedSlot(val displayOffset: Int, val payload: String)

internal fun TextKitState.imageEmbedSlots(): List<ImageEmbedSlot> {
    val result = ArrayList<ImageEmbedSlot>()
    for (paragraph in paragraphs) {
        val child = paragraph.imageEmbedChild() ?: continue
        val payload = child.embedPayload ?: continue
        result += ImageEmbedSlot(fieldOffsetToDisplayOffset(child.start), payload)
    }
    return result
}

/** Draws every image embed inside the editor, on the tall empty line reserved for it. */
@Composable
internal fun EmbedImageOverlay(state: TextKitState, modifier: Modifier = Modifier) {
    val layout = state.textLayoutResult ?: return
    val slots = state.imageEmbedSlots()
    if (slots.isEmpty()) return
    val density = LocalDensity.current
    val textLength = layout.layoutInput.text.length

    Box(modifier = modifier) {
        slots.forEachIndexed { index, slot ->
            if (slot.displayOffset >= textLength) return@forEachIndexed
            val line = layout.getLineForOffset(slot.displayOffset)
            val top = layout.getLineTop(line)
            val bottom = layout.getLineBottom(line)
            if (bottom <= top) return@forEachIndexed
            val url = remember(slot.payload) { embedUrlOf(slot.payload) } ?: return@forEachIndexed

            key(index) {
                Box(
                    modifier = Modifier
                        .offset { IntOffset(0, top.roundToInt()) }
                        .fillMaxWidth()
                        .height(with(density) { (bottom - top).toDp() })
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    SubcomposeAsyncImage(
                        model = url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.CenterStart,
                        modifier = Modifier.fillMaxSize(),
                        loading = {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        },
                        error = {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                                Text(
                                    text = "Image could not be loaded",
                                    color = TextKitTheme.colors.onSurfaceVariant
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}

/** Image block for the read-only viewer (what students see). */
@Composable
internal fun ViewerImageBlock(url: String) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        alignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 360.dp)
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(8.dp)),
        loading = {
            Box(Modifier.fillMaxWidth().height(96.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        },
    )
}