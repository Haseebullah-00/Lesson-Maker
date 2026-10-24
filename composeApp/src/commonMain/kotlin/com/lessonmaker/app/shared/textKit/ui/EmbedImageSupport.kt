//package com.lessonmaker.app.shared.textKit.ui
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.heightIn
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.key
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.dp
//import coil3.compose.SubcomposeAsyncImage
//import com.lessonmaker.app.shared.textkit.editor.core.parser.embedUrlOf
//import com.lessonmaker.app.shared.textkit.editor.core.transactions.models.TextEditorItem
//import com.lessonmaker.app.shared.textkit.editor.core.transactions.models.TextEditorParagraph
//import com.lessonmaker.app.shared.textkit.theme.TextKitTheme
//import com.lessonmaker.app.shared.textkit.ui.state.TextKitState
//import kotlin.math.roundToInt
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.widthIn
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.rounded.Add
//import androidx.compose.material.icons.rounded.Remove
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Slider
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableFloatStateOf
//import androidx.compose.runtime.setValue
//import com.lessonmaker.app.shared.textkit.editor.core.parser.DEFAULT_IMAGE_HEIGHT_DP
//import com.lessonmaker.app.shared.textkit.editor.core.parser.DEFAULT_IMAGE_WIDTH_DP
//import com.lessonmaker.app.shared.textkit.editor.core.parser.MAX_IMAGE_WIDTH_DP
//import com.lessonmaker.app.shared.textkit.editor.core.parser.MIN_IMAGE_WIDTH_DP
//import com.lessonmaker.app.shared.textkit.editor.core.parser.embedImageHeightOf
//import com.lessonmaker.app.shared.textkit.editor.core.parser.embedImageWidthOf
//import com.lessonmaker.app.shared.textkit.editor.core.parser.embedWithImageSize
//
///**
// * Inline images in the editor.
// *
// * How it works:
// *  1. The paragraph that holds an image embed gets a tall line height (see ListItemEditorTransform),
// *     and its placeholder text is made transparent.
// *  2. [EmbedImageOverlay] draws the real image on top of that empty tall line.
// *  3. The viewer (students) uses [ViewerImageBlock].
// */
//
///** Height (in sp) reserved for an inline image line in the editor. Change this to make images taller/shorter. */
//internal const val EMBED_IMAGE_LINE_HEIGHT_SP = 140
//
///** True when this item is an embed whose JSON "type" is "image". Only the start of the payload is inspected (cheap even for big data URLs). */
//internal fun TextEditorItem.isImageEmbed(): Boolean {
//    val payload = embedPayload ?: return false
//    return payload.take(300).filterNot { it.isWhitespace() }.contains("\"type\":\"image\"")
//}
//
//internal fun TextEditorParagraph.imageEmbedChild(): TextEditorItem? =
//    children.firstOrNull { it.isImageEmbed() }
//
//internal class ImageEmbedSlot(val displayOffset: Int, val payload: String)
//
//internal fun TextKitState.imageEmbedSlots(): List<ImageEmbedSlot> {
//    val result = ArrayList<ImageEmbedSlot>()
//    for (paragraph in paragraphs) {
//        val child = paragraph.imageEmbedChild() ?: continue
//        val payload = child.embedPayload ?: continue
//        result += ImageEmbedSlot(fieldOffsetToDisplayOffset(child.start), payload)
//    }
//    return result
//}
//
///** Draws every image embed inside the editor, on the tall empty line reserved for it. */
//@Composable
//internal fun EmbedImageOverlay(state: TextKitState, modifier: Modifier = Modifier) {
//    val layout = state.textLayoutResult ?: return
//    val slots = state.imageEmbedSlots()
//    if (slots.isEmpty()) return
//    val density = LocalDensity.current
//    val textLength = layout.layoutInput.text.length
//
//    Box(modifier = modifier) {
//        slots.forEachIndexed { index, slot ->
//            if (slot.displayOffset >= textLength) return@forEachIndexed
//            val line = layout.getLineForOffset(slot.displayOffset)
//            val top = layout.getLineTop(line)
//            val bottom = layout.getLineBottom(line)
//            if (bottom <= top) return@forEachIndexed
//            val url = remember(slot.payload) { embedUrlOf(slot.payload) } ?: return@forEachIndexed
//            val maxWidthDp = remember(slot.payload) { embedImageWidthOf(slot.payload) }
//
//            key(index) {
//                Box(
//                    modifier = Modifier
//                        .offset { IntOffset(0, top.roundToInt()) }
//                        .then(if (maxWidthDp != null) Modifier.widthIn(max = maxWidthDp.dp) else Modifier)
//                        .fillMaxWidth()
//                        .height(with(density) { (bottom - top).toDp() })
//                        .padding(vertical = 6.dp)
//                        .clip(RoundedCornerShape(8.dp))
//                ) {
//                    SubcomposeAsyncImage(
//                        model = url,
//                        contentDescription = null,
//                        contentScale = ContentScale.Fit,
//                        alignment = Alignment.CenterStart,
//                        modifier = Modifier.fillMaxSize(),
//                        loading = {
//                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                                CircularProgressIndicator()
//                            }
//                        },
//                        error = {
//                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
//                                Text(
//                                    text = "Image could not be loaded",
//                                    color = TextKitTheme.colors.onSurfaceVariant
//                                )
//                            }
//                        },
//                    )
//                }
//            }
//        }
//    }
//}
//
///** Image block for the read-only viewer (what students see). */
////@Composable
////internal fun ViewerImageBlock(url: String) {
////    SubcomposeAsyncImage(
////        model = url,
////        contentDescription = null,
////        contentScale = ContentScale.Fit,
////        alignment = Alignment.CenterStart,
////        modifier = Modifier
////            .fillMaxWidth()
////            .heightIn(max = 360.dp)
////            .padding(vertical = 6.dp)
////            .clip(RoundedCornerShape(8.dp)),
////        loading = {
////            Box(Modifier.fillMaxWidth().height(96.dp), contentAlignment = Alignment.Center) {
////                CircularProgressIndicator()
////            }
////        },
////    )
////}
//
//
//@Composable
//internal fun ViewerImageBlock(url: String, widthDp: Int? = null, heightDp: Int? = null) {
//    SubcomposeAsyncImage(
//        model = url,
//        contentDescription = null,
//        contentScale = ContentScale.Fit,
//        alignment = Alignment.CenterStart,
//        modifier = Modifier
//            .then(if (widthDp != null) Modifier.widthIn(max = widthDp.dp) else Modifier)
//            .fillMaxWidth()
//            .heightIn(max = (heightDp ?: 360).dp)
//            .padding(vertical = 6.dp)
//            .clip(RoundedCornerShape(8.dp)),
//        loading = {
//            Box(Modifier.fillMaxWidth().height(96.dp), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
//            }
//        },
//    )
//}
//
//@Composable
//internal fun ImageSizeControls(rawJson: String, onSync: (String) -> Unit) {
//    val baseW = embedImageWidthOf(rawJson) ?: DEFAULT_IMAGE_WIDTH_DP
//    val baseH = embedImageHeightOf(rawJson) ?: DEFAULT_IMAGE_HEIGHT_DP
//    val ratio = baseH.toFloat() / baseW
//    var width by remember(rawJson) { mutableFloatStateOf(baseW.toFloat()) }
//
//    fun commit(w: Float) {
//        val nw = w.roundToInt().coerceIn(MIN_IMAGE_WIDTH_DP, MAX_IMAGE_WIDTH_DP)
//        runCatching { onSync(embedWithImageSize(rawJson, nw, (nw * ratio).roundToInt())) }
//    }
//
//    Row(verticalAlignment = Alignment.CenterVertically) {
//        IconButton(onClick = { commit(width - 20) }) {
//            Icon(Icons.Rounded.Remove, "Smaller", Modifier.size(20.dp))
//        }
//        Slider(
//            value = width,
//            onValueChange = { width = it },
//            onValueChangeFinished = { commit(width) },
//            valueRange = MIN_IMAGE_WIDTH_DP.toFloat()..MAX_IMAGE_WIDTH_DP.toFloat(),
//            modifier = Modifier.weight(1f),
//        )
//        IconButton(onClick = { commit(width + 20) }) {
//            Icon(Icons.Rounded.Add, "Larger", Modifier.size(20.dp))
//        }
//    }
//    Text("${width.roundToInt()} dp (shrinks on small screens)", color = TextKitTheme.colors.onSurfaceVariant)
//}

//package com.lessonmaker.app.shared.textKit.ui
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.heightIn
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.widthIn
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.size
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.rounded.Add
//import androidx.compose.material.icons.rounded.Remove
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Slider
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableFloatStateOf
//import androidx.compose.runtime.setValue
//import com.lessonmaker.app.shared.textkit.editor.core.parser.DEFAULT_IMAGE_WIDTH_DP
//import com.lessonmaker.app.shared.textkit.editor.core.parser.DEFAULT_IMAGE_HEIGHT_DP
//import com.lessonmaker.app.shared.textkit.editor.core.parser.MIN_IMAGE_WIDTH_DP
//import com.lessonmaker.app.shared.textkit.editor.core.parser.MAX_IMAGE_WIDTH_DP
//import com.lessonmaker.app.shared.textkit.editor.core.parser.embedImageWidthOf
//import com.lessonmaker.app.shared.textkit.editor.core.parser.embedImageHeightOf
//import com.lessonmaker.app.shared.textkit.editor.core.parser.embedWithImageSize
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.key
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.dp
//import coil3.compose.SubcomposeAsyncImage
//import com.lessonmaker.app.shared.textkit.editor.core.parser.embedUrlOf
//import com.lessonmaker.app.shared.textkit.editor.core.transactions.models.TextEditorItem
//import com.lessonmaker.app.shared.textkit.editor.core.transactions.models.TextEditorParagraph
//import com.lessonmaker.app.shared.textkit.theme.TextKitTheme
//import com.lessonmaker.app.shared.textkit.ui.state.TextKitState
//import kotlin.math.roundToInt
//
///**
// * Inline images in the editor.
// *
// * How it works:
// *  1. The paragraph that holds an image embed gets a tall line height (see ListItemEditorTransform),
// *     and its placeholder text is made transparent.
// *  2. [EmbedImageOverlay] draws the real image on top of that empty tall line.
// *  3. The viewer (students) uses [ViewerImageBlock].
// */
//
///** Height (in sp) reserved for an inline image line in the editor. Change this to make images taller/shorter. */
//internal const val EMBED_IMAGE_LINE_HEIGHT_SP = 140
//
///** True when this item is an embed whose JSON "type" is "image". Only the start of the payload is inspected (cheap even for big data URLs). */
//internal fun TextEditorItem.isImageEmbed(): Boolean {
//    val payload = embedPayload ?: return false
//    return payload.take(300).filterNot { it.isWhitespace() }.contains("\"type\":\"image\"")
//}
//
//internal fun TextEditorParagraph.imageEmbedChild(): TextEditorItem? =
//    children.firstOrNull { it.isImageEmbed() }
//
//internal class ImageEmbedSlot(val displayOffset: Int, val fieldOffset: Int, val payload: String)
//
//internal fun TextKitState.imageEmbedSlots(): List<ImageEmbedSlot> {
//    val result = ArrayList<ImageEmbedSlot>()
//    for (paragraph in paragraphs) {
//        val child = paragraph.imageEmbedChild() ?: continue
//        val payload = child.embedPayload ?: continue
//        result += ImageEmbedSlot(fieldOffsetToDisplayOffset(child.start), child.start, payload)
//    }
//    return result
//}
//
///** The image whose (tall) line contains [position] (text-field coordinates), or null. */
//internal fun TextKitState.imageSlotAt(position: androidx.compose.ui.geometry.Offset): ImageEmbedSlot? {
//    val layout = textLayoutResult ?: return null
//    val textLength = layout.layoutInput.text.length
//    return imageEmbedSlots().firstOrNull { slot ->
//        if (slot.displayOffset >= textLength) return@firstOrNull false
//        val line = layout.getLineForOffset(slot.displayOffset)
//        position.y >= layout.getLineTop(line) && position.y <= layout.getLineBottom(line)
//    }
//}
//
///** Draws every image embed inside the editor, on the tall empty line reserved for it. */
//@Composable
//internal fun EmbedImageOverlay(state: TextKitState, modifier: Modifier = Modifier) {
//    val layout = state.textLayoutResult ?: return
//    val slots = state.imageEmbedSlots()
//    if (slots.isEmpty()) return
//    val density = LocalDensity.current
//    val textLength = layout.layoutInput.text.length
//
//    Box(modifier = modifier) {
//        slots.forEachIndexed { index, slot ->
//            if (slot.displayOffset >= textLength) return@forEachIndexed
//            val line = layout.getLineForOffset(slot.displayOffset)
//            val top = layout.getLineTop(line)
//            val bottom = layout.getLineBottom(line)
//            if (bottom <= top) return@forEachIndexed
//            val url = remember(slot.payload) { embedUrlOf(slot.payload) } ?: return@forEachIndexed
//            val maxWidthDp = remember(slot.payload) { embedImageWidthOf(slot.payload) }
//
//            key(index) {
//                Box(
//                    modifier = Modifier
//                        .offset { IntOffset(0, top.roundToInt()) }
//                        // Saved width is a MAX: narrower screens just get the available width.
//                        .then(if (maxWidthDp != null) Modifier.widthIn(max = maxWidthDp.dp) else Modifier)
//                        .fillMaxWidth()
//                        .height(with(density) { (bottom - top).toDp() })
//                        .padding(vertical = 6.dp)
//                        .clip(RoundedCornerShape(8.dp))
//                        // Tap the picture -> open its popup (size slider + Remove).
//                        .clickable { state.openEmbedAtFieldOffset(slot.fieldOffset) }
//                ) {
//                    SubcomposeAsyncImage(
//                        model = url,
//                        contentDescription = null,
//                        contentScale = ContentScale.Fit,
//                        alignment = Alignment.CenterStart,
//                        modifier = Modifier.fillMaxSize(),
//                        loading = {
//                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                                CircularProgressIndicator()
//                            }
//                        },
//                        error = {
//                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
//                                Text(
//                                    text = "Image could not be loaded",
//                                    color = TextKitTheme.colors.onSurfaceVariant
//                                )
//                            }
//                        },
//                    )
//                }
//            }
//        }
//    }
//}
//
///** Image block for the read-only viewer (what students see). */
//@Composable
//internal fun ViewerImageBlock(url: String, widthDp: Int? = null, heightDp: Int? = null) {
//    SubcomposeAsyncImage(
//        model = url,
//        contentDescription = null,
//        contentScale = ContentScale.Fit,
//        alignment = Alignment.CenterStart,
//        modifier = Modifier
//            .then(if (widthDp != null) Modifier.widthIn(max = widthDp.dp) else Modifier)
//            .fillMaxWidth()
//            .heightIn(max = (heightDp ?: 360).dp)
//            .padding(vertical = 6.dp)
//            .clip(RoundedCornerShape(8.dp)),
//        loading = {
//            Box(Modifier.fillMaxWidth().height(96.dp), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
//            }
//        },
//    )
//}
//
///** Shrink / grow control shown in the image popup. Saves `attrs.width` + `attrs.height` (dp) through [onSync]. */
//@Composable
//internal fun ImageSizeControls(rawJson: String, onSync: (String) -> Unit) {
//    val baseW = embedImageWidthOf(rawJson) ?: DEFAULT_IMAGE_WIDTH_DP
//    val baseH = embedImageHeightOf(rawJson) ?: DEFAULT_IMAGE_HEIGHT_DP
//    val ratio = baseH.toFloat() / baseW
//    var width by remember(rawJson) { mutableFloatStateOf(baseW.toFloat()) }
//
//    fun commit(w: Float) {
//        val nw = w.roundToInt().coerceIn(MIN_IMAGE_WIDTH_DP, MAX_IMAGE_WIDTH_DP)
//        width = nw.toFloat()   // keep the slider / +/- in step (activeEmbed.rawJson is not refreshed)
//        runCatching { onSync(embedWithImageSize(rawJson, nw, (nw * ratio).roundToInt())) }
//    }
//
//    Row(verticalAlignment = Alignment.CenterVertically) {
//        IconButton(onClick = { commit(width - 20) }) { Icon(Icons.Rounded.Remove, "Smaller", Modifier.size(20.dp)) }
//        Slider(
//            value = width,
//            onValueChange = { width = it },          // live slider only; saved on release (payload can be big)
//            onValueChangeFinished = { commit(width) },
//            valueRange = MIN_IMAGE_WIDTH_DP.toFloat()..MAX_IMAGE_WIDTH_DP.toFloat(),
//            modifier = Modifier.weight(1f),
//        )
//        IconButton(onClick = { commit(width + 20) }) { Icon(Icons.Rounded.Add, "Larger", Modifier.size(20.dp)) }
//    }
//    Text("${width.roundToInt()} dp (shrinks on small screens)", color = TextKitTheme.colors.onSurfaceVariant)
//}

//
//package com.lessonmaker.app.shared.textKit.ui
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.heightIn
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.aspectRatio
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.widthIn
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.size
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.rounded.Add
//import androidx.compose.material.icons.rounded.Remove
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Slider
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableFloatStateOf
//import androidx.compose.runtime.setValue
//import com.lessonmaker.app.shared.textkit.editor.core.parser.DEFAULT_IMAGE_WIDTH_DP
//import com.lessonmaker.app.shared.textkit.editor.core.parser.DEFAULT_IMAGE_HEIGHT_DP
//import com.lessonmaker.app.shared.textkit.editor.core.parser.MIN_IMAGE_WIDTH_DP
//import com.lessonmaker.app.shared.textkit.editor.core.parser.MAX_IMAGE_WIDTH_DP
//import com.lessonmaker.app.shared.textkit.editor.core.parser.embedImageWidthOf
//import com.lessonmaker.app.shared.textkit.editor.core.parser.embedImageHeightOf
//import com.lessonmaker.app.shared.textkit.editor.core.parser.embedWithImageSize
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.key
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.dp
//import coil3.compose.SubcomposeAsyncImage
//import com.lessonmaker.app.shared.textkit.editor.core.parser.embedUrlOf
//import com.lessonmaker.app.shared.textkit.editor.core.transactions.models.TextEditorItem
//import com.lessonmaker.app.shared.textkit.editor.core.transactions.models.TextEditorParagraph
//import com.lessonmaker.app.shared.textkit.theme.TextKitTheme
//import com.lessonmaker.app.shared.textkit.ui.state.TextKitState
//import kotlin.math.roundToInt
//
///**
// * Inline images in the editor.
// *
// * How it works:
// *  1. The paragraph that holds an image embed gets a tall line height (see ListItemEditorTransform),
// *     and its placeholder text is made transparent.
// *  2. [EmbedImageOverlay] draws the real image on top of that empty tall line.
// *  3. The viewer (students) uses [ViewerImageBlock].
// */
//
///** Height (in sp) reserved for an inline image line in the editor. Change this to make images taller/shorter. */
//internal const val EMBED_IMAGE_LINE_HEIGHT_SP = 140
//
///** True when this item is an embed whose JSON "type" is "image". Only the start of the payload is inspected (cheap even for big data URLs). */
//internal fun TextEditorItem.isImageEmbed(): Boolean {
//    val payload = embedPayload ?: return false
//    return payload.take(300).filterNot { it.isWhitespace() }.contains("\"type\":\"image\"")
//}
//
//internal fun TextEditorParagraph.imageEmbedChild(): TextEditorItem? =
//    children.firstOrNull { it.isImageEmbed() }
//
//internal class ImageEmbedSlot(val displayOffset: Int, val fieldOffset: Int, val payload: String)
//
//internal fun TextKitState.imageEmbedSlots(): List<ImageEmbedSlot> {
//    val result = ArrayList<ImageEmbedSlot>()
//    for (paragraph in paragraphs) {
//        val child = paragraph.imageEmbedChild() ?: continue
//        val payload = child.embedPayload ?: continue
//        result += ImageEmbedSlot(fieldOffsetToDisplayOffset(child.start), child.start, payload)
//    }
//    return result
//}
//
///** The image whose (tall) line contains [position] (text-field coordinates), or null. */
//internal fun TextKitState.imageSlotAt(position: androidx.compose.ui.geometry.Offset): ImageEmbedSlot? {
//    val layout = textLayoutResult ?: return null
//    val textLength = layout.layoutInput.text.length
//    return imageEmbedSlots().firstOrNull { slot ->
//        if (slot.displayOffset >= textLength) return@firstOrNull false
//        val line = layout.getLineForOffset(slot.displayOffset)
//        position.y >= layout.getLineTop(line) && position.y <= layout.getLineBottom(line)
//    }
//}
//
///** Draws every image embed inside the editor, on the tall empty line reserved for it. */
//@Composable
//internal fun EmbedImageOverlay(state: TextKitState, modifier: Modifier = Modifier) {
//    val layout = state.textLayoutResult ?: return
//    val slots = state.imageEmbedSlots()
//    if (slots.isEmpty()) return
//    val density = LocalDensity.current
//    val textLength = layout.layoutInput.text.length
//
//    Box(modifier = modifier) {
//        slots.forEachIndexed { index, slot ->
//            if (slot.displayOffset >= textLength) return@forEachIndexed
//            val line = layout.getLineForOffset(slot.displayOffset)
//            val top = layout.getLineTop(line)
//            val bottom = layout.getLineBottom(line)
//            if (bottom <= top) return@forEachIndexed
//            val url = remember(slot.payload) { embedUrlOf(slot.payload) } ?: return@forEachIndexed
//            val maxWidthDp = remember(slot.payload) { embedImageWidthOf(slot.payload) }
//
//            key(index) {
//                Box(
//                    modifier = Modifier
//                        .offset { IntOffset(0, top.roundToInt()) }
//                        // Saved width is a MAX: narrower screens just get the available width.
//                        .then(if (maxWidthDp != null) Modifier.widthIn(max = maxWidthDp.dp) else Modifier)
//                        .fillMaxWidth()
//                        .height(with(density) { (bottom - top).toDp() })
//                        .padding(vertical = 6.dp)
//                        .clip(RoundedCornerShape(8.dp))
//                        // Tap the picture -> open its popup (size slider + Remove).
//                        .clickable { state.openEmbedAtFieldOffset(slot.fieldOffset) }
//                ) {
//                    SubcomposeAsyncImage(
//                        model = url,
//                        contentDescription = null,
//                        contentScale = ContentScale.FillBounds,   // fill the width x height the author chose
//                        alignment = Alignment.CenterStart,
//                        modifier = Modifier.fillMaxSize(),
//                        loading = {
//                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                                CircularProgressIndicator()
//                            }
//                        },
//                        error = {
//                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
//                                Text(
//                                    text = "Image could not be loaded",
//                                    color = TextKitTheme.colors.onSurfaceVariant
//                                )
//                            }
//                        },
//                    )
//                }
//            }
//        }
//    }
//}
//
///** Image block for the read-only viewer (what students see). */
//@Composable
//internal fun ViewerImageBlock(url: String, widthDp: Int? = null, heightDp: Int? = null) {
//    SubcomposeAsyncImage(
//        model = url,
//        contentDescription = null,
//        contentScale = if (widthDp != null && heightDp != null) ContentScale.FillBounds else ContentScale.Fit,
//        alignment = Alignment.CenterStart,
//        modifier = Modifier
//            .then(if (widthDp != null) Modifier.widthIn(max = widthDp.dp) else Modifier)
//            .fillMaxWidth()
//            // keep the author's width:height shape when the screen is narrower than the chosen width
//            .then(
//                if (widthDp != null && heightDp != null) Modifier.aspectRatio(widthDp.toFloat() / heightDp)
//                else Modifier.heightIn(max = 360.dp)
//            )
//            .padding(vertical = 6.dp)
//            .clip(RoundedCornerShape(8.dp)),
//        loading = {
//            Box(Modifier.fillMaxWidth().height(96.dp), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
//            }
//        },
//    )
//}
//
//private const val MIN_IMAGE_HEIGHT_DP = 60
//private const val MAX_IMAGE_HEIGHT_DP = 800
//
///** Width + height controls shown in the image popup. Saves `attrs.width` / `attrs.height` (dp) through [onSync]. */
//@Composable
//internal fun ImageSizeControls(rawJson: String, onSync: (String) -> Unit) {
//    var width by remember(rawJson) {
//        mutableFloatStateOf((embedImageWidthOf(rawJson) ?: DEFAULT_IMAGE_WIDTH_DP).toFloat())
//    }
//    var height by remember(rawJson) {
//        mutableFloatStateOf((embedImageHeightOf(rawJson) ?: DEFAULT_IMAGE_HEIGHT_DP).toFloat())
//    }
//
//    fun commit(w: Float = width, h: Float = height) {
//        val nw = w.roundToInt().coerceIn(MIN_IMAGE_WIDTH_DP, MAX_IMAGE_WIDTH_DP)
//        val nh = h.roundToInt().coerceIn(MIN_IMAGE_HEIGHT_DP, MAX_IMAGE_HEIGHT_DP)
//        width = nw.toFloat()
//        height = nh.toFloat()
//        runCatching { onSync(embedWithImageSize(rawJson, nw, nh)) }
//    }
//
//    @Composable
//    fun SizeRow(label: String, value: Float, min: Int, max: Int, onChange: (Float) -> Unit, onStep: (Float) -> Unit) {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text(label, Modifier.width(56.dp), color = TextKitTheme.colors.onSurfaceVariant)
//            IconButton(onClick = { onStep(value - 20) }) { Icon(Icons.Rounded.Remove, "Decrease $label", Modifier.size(20.dp)) }
//            Slider(
//                value = value,
//                onValueChange = onChange,            // live only; saved on release (payload can be big)
//                onValueChangeFinished = { commit() },
//                valueRange = min.toFloat()..max.toFloat(),
//                modifier = Modifier.weight(1f),
//            )
//            IconButton(onClick = { onStep(value + 20) }) { Icon(Icons.Rounded.Add, "Increase $label", Modifier.size(20.dp)) }
//        }
//    }
//
//    SizeRow("Width", width, MIN_IMAGE_WIDTH_DP, MAX_IMAGE_WIDTH_DP, { width = it }, { commit(w = it) })
//    SizeRow("Height", height, MIN_IMAGE_HEIGHT_DP, MAX_IMAGE_HEIGHT_DP, { height = it }, { commit(h = it) })
//    Text(
//        "${width.roundToInt()} × ${height.roundToInt()} dp (width shrinks on small screens)",
//        color = TextKitTheme.colors.onSurfaceVariant,
//    )
//}


package com.lessonmaker.app.shared.textKit.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import com.lessonmaker.app.shared.textkit.editor.core.parser.DEFAULT_IMAGE_WIDTH_DP
import com.lessonmaker.app.shared.textkit.editor.core.parser.DEFAULT_IMAGE_HEIGHT_DP
import com.lessonmaker.app.shared.textkit.editor.core.parser.MIN_IMAGE_WIDTH_DP
import com.lessonmaker.app.shared.textkit.editor.core.parser.MAX_IMAGE_WIDTH_DP
import com.lessonmaker.app.shared.textkit.editor.core.parser.embedImageWidthOf
import com.lessonmaker.app.shared.textkit.editor.core.parser.embedImageHeightOf
import com.lessonmaker.app.shared.textkit.editor.core.parser.embedWithImageSize
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

internal class ImageEmbedSlot(val displayOffset: Int, val fieldOffset: Int, val payload: String)

internal fun TextKitState.imageEmbedSlots(): List<ImageEmbedSlot> {
    val result = ArrayList<ImageEmbedSlot>()
    for (paragraph in paragraphs) {
        val child = paragraph.imageEmbedChild() ?: continue
        val payload = child.embedPayload ?: continue
        result += ImageEmbedSlot(fieldOffsetToDisplayOffset(child.start), child.start, payload)
    }
    return result
}

/** The image whose (tall) line contains [position] (text-field coordinates), or null. */
internal fun TextKitState.imageSlotAt(position: androidx.compose.ui.geometry.Offset): ImageEmbedSlot? {
    val layout = textLayoutResult ?: return null
    val textLength = layout.layoutInput.text.length
    return imageEmbedSlots().firstOrNull { slot ->
        if (slot.displayOffset >= textLength) return@firstOrNull false
        val line = layout.getLineForOffset(slot.displayOffset)
        position.y >= layout.getLineTop(line) && position.y <= layout.getLineBottom(line)
    }
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
            val maxWidthDp = remember(slot.payload) { embedImageWidthOf(slot.payload) }

            key(index) {
                Box(
                    modifier = Modifier
                        .offset { IntOffset(0, top.roundToInt()) }
                        // Saved width is a MAX: narrower screens just get the available width.
                        .then(if (maxWidthDp != null) Modifier.widthIn(max = maxWidthDp.dp) else Modifier)
                        .fillMaxWidth()
                        .height(with(density) { (bottom - top).toDp() })
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(8.dp))
                        // Tap the picture -> open its popup (size slider + Remove).
                        .clickable { state.openEmbedAtFieldOffset(slot.fieldOffset) }
                ) {
                    SubcomposeAsyncImage(
                        model = url,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,   // fill the width x height the author chose
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
internal fun ViewerImageBlock(url: String, widthDp: Int? = null, heightDp: Int? = null) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = null,
        contentScale = if (widthDp != null && heightDp != null) ContentScale.FillBounds else ContentScale.Fit,
        alignment = Alignment.CenterStart,
        modifier = Modifier
            .then(if (widthDp != null) Modifier.widthIn(max = widthDp.dp) else Modifier)
            .fillMaxWidth()
            // keep the author's width:height shape when the screen is narrower than the chosen width
            .then(
                if (widthDp != null && heightDp != null) Modifier.aspectRatio(widthDp.toFloat() / heightDp)
                else Modifier.heightIn(max = 360.dp)
            )
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(8.dp)),
        loading = {
            Box(Modifier.fillMaxWidth().height(96.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        },
    )
}

private const val MIN_IMAGE_HEIGHT_DP = 60
private const val MAX_IMAGE_HEIGHT_DP = 800

/** Width + height controls shown in the image popup. Saves `attrs.width` / `attrs.height` (dp) through [onSync]. */
@Composable
internal fun ImageSizeControls(rawJson: String, onSync: (String) -> Unit, onChangeImage: (() -> Unit)? = null) {
    var width by remember(rawJson) {
        mutableFloatStateOf((embedImageWidthOf(rawJson) ?: DEFAULT_IMAGE_WIDTH_DP).toFloat())
    }
    var height by remember(rawJson) {
        mutableFloatStateOf((embedImageHeightOf(rawJson) ?: DEFAULT_IMAGE_HEIGHT_DP).toFloat())
    }

    fun commit(w: Float = width, h: Float = height) {
        val nw = w.roundToInt().coerceIn(MIN_IMAGE_WIDTH_DP, MAX_IMAGE_WIDTH_DP)
        val nh = h.roundToInt().coerceIn(MIN_IMAGE_HEIGHT_DP, MAX_IMAGE_HEIGHT_DP)
        width = nw.toFloat()
        height = nh.toFloat()
        runCatching { onSync(embedWithImageSize(rawJson, nw, nh)) }
    }

    @Composable
    fun SizeRow(label: String, value: Float, min: Int, max: Int, onChange: (Float) -> Unit, onStep: (Float) -> Unit) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(label, Modifier.width(56.dp), color = TextKitTheme.colors.onSurfaceVariant)
            IconButton(onClick = { onStep(value - 20) }) { Icon(Icons.Rounded.Remove, "Decrease $label", Modifier.size(20.dp)) }
            Slider(
                value = value,
                onValueChange = onChange,            // live only; saved on release (payload can be big)
                onValueChangeFinished = { commit() },
                valueRange = min.toFloat()..max.toFloat(),
                modifier = Modifier.weight(1f),
            )
            IconButton(onClick = { onStep(value + 20) }) { Icon(Icons.Rounded.Add, "Increase $label", Modifier.size(20.dp)) }
        }
    }

    if (onChangeImage != null) {
        androidx.compose.material3.OutlinedButton(onClick = onChangeImage) { Text("Change image") }
    }
    SizeRow("Width", width, MIN_IMAGE_WIDTH_DP, MAX_IMAGE_WIDTH_DP, { width = it }, { commit(w = it) })
    SizeRow("Height", height, MIN_IMAGE_HEIGHT_DP, MAX_IMAGE_HEIGHT_DP, { height = it }, { commit(h = it) })
    Text(
        "${width.roundToInt()} × ${height.roundToInt()} dp (width shrinks on small screens)",
        color = TextKitTheme.colors.onSurfaceVariant,
    )
}