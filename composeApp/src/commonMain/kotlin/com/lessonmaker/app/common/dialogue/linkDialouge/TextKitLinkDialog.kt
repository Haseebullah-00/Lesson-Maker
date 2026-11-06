//package com.lessonmaker.app.common.dialogue.linkDialouge
//
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.imePadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.widthIn
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.rounded.Close
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.key
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalUriHandler
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.window.Dialog
//import androidx.compose.ui.window.DialogProperties
//import com.lessonmaker.app.common.ActionButtonStyle
//import com.lessonmaker.app.common.FormErrorText
//import com.lessonmaker.app.common.MainButtonNew
//import com.lessonmaker.app.common.SimpleInputFieldWithErrorNew
//import com.lessonmaker.app.common.TeacherActionButton
//import com.lessonmaker.app.shared.textKit.ui.utils.textKitCaptionStyle
//import com.lessonmaker.app.shared.textKit.ui.utils.textKitTitleStyle
//import com.lessonmaker.app.shared.textkit.theme.TextKitTheme
//import com.lessonmaker.app.shared.textkit.ui.YouTubeLinkUtils
//import com.lessonmaker.app.shared.textkit.ui.state.TextKitState
//
///**
// * Teacher dialog to ADD or EDIT a link (shows while state.activeLink != null).
// * YouTube urls get two OPTIONAL time-stamp parts, embedded in the url when filled.
// */
//@Composable
//fun TextKitLinkDialog(state: TextKitState) {
//    val link = state.activeLink ?: return
//    val isEdit = link.url.isNotEmpty()
//    val onClose = { state.dismissLinkPopup() }
//    val uriHandler = LocalUriHandler.current
//
//    key(link.range.start, link.range.end, link.url) {
//        val (a0, b0) = remember(link.url) { YouTubeLinkUtils.readTimes(link.url) }
//        val (c0, d0) = remember(link.url) { YouTubeLinkUtils.readTimes2(link.url) }
//        var text by remember { mutableStateOf(link.text) }
//        var url by remember { mutableStateOf(link.url) }
//        var start1 by remember { mutableStateOf(YouTubeLinkUtils.formatTime(a0)) }
//        var end1 by remember { mutableStateOf(YouTubeLinkUtils.formatTime(b0)) }
//        var start2 by remember { mutableStateOf(YouTubeLinkUtils.formatTime(c0)) }
//        var end2 by remember { mutableStateOf(YouTubeLinkUtils.formatTime(d0)) }
//        var urlError by remember { mutableStateOf<String?>(null) }
//        var start1Error by remember { mutableStateOf<String?>(null) }
//        var end1Error by remember { mutableStateOf<String?>(null) }
//        var start2Error by remember { mutableStateOf<String?>(null) }
//        var end2Error by remember { mutableStateOf<String?>(null) }
//        val isYouTube = YouTubeLinkUtils.isYouTube(url)
//        val hasPart2 = start2.isNotBlank() || end2.isNotBlank()
//
//        fun save() {
//            val raw = url.trim()
//            urlError = if (raw.isEmpty()) "Please enter link" else null
//            start1Error = null; end1Error = null; start2Error = null; end2Error = null
//            var a: Int? = null; var b: Int? = null; var c: Int? = null; var d: Int? = null
//            if (isYouTube) {
//                a = YouTubeLinkUtils.parseTime(start1); b = YouTubeLinkUtils.parseTime(end1)
//                c = YouTubeLinkUtils.parseTime(start2); d = YouTubeLinkUtils.parseTime(end2)
//                val fmt = "Use 2:00 or 1:02:30"
//                if (start1.isNotBlank() && a == null) start1Error = fmt
//                if (end1.isNotBlank() && b == null) end1Error = fmt
//                if (start2.isNotBlank() && c == null) start2Error = fmt
//                if (end2.isNotBlank() && d == null) end2Error = fmt
//                val formatOk = start1Error == null && end1Error == null &&
//                        start2Error == null && end2Error == null
//                if (formatOk) {
//                    val part1Empty = a == null && b == null
//                    val part2Empty = c == null && d == null
//                    if (part1Empty && !part2Empty) {      // only part 2 filled -> becomes part 1
//                        a = c; b = d; c = null; d = null
//                    } else if (!part2Empty) {
//                        if (b == null) end1Error = "Set end of part 1"
//                        else if (c == null) start2Error = "Set start of part 2"
//                        else if (c < b) start2Error = "Start after part 1 ends"
//                    }
//                    if (a != null && b != null && b <= a) end1Error = "End must be after start"
//                    if (c != null && d != null && d <= c) end2Error = "End must be after start"
//                }
//            }
//            val hasError = urlError != null || start1Error != null || end1Error != null ||
//                    start2Error != null || end2Error != null
//            if (hasError) return
//            val finalUrl = if (isYouTube) YouTubeLinkUtils.buildUrl(raw, a, b, c, d) else raw
//            state.updateLinkText(newText = text.ifBlank { finalUrl }, url = finalUrl, range = link.range)
//            state.dismissLinkPopup()                      // Save closes the dialog
//        }
//
//        fun openPart(part: Int) {
//            val raw = url.trim()
//            if (raw.isEmpty()) { urlError = "Please enter link"; return }
//            val target = if (isYouTube) {
//                val s = YouTubeLinkUtils.parseTime(if (part == 1) start1 else start2)
//                val e = YouTubeLinkUtils.parseTime(if (part == 1) end1 else end2)
//                YouTubeLinkUtils.buildUrl(raw, s, e)
//            } else raw
//            runCatching { uriHandler.openUri(target) }
//        }
//
//        Dialog(
//            onDismissRequest = onClose,
//            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
//        ) {
//            Card(
//                modifier = Modifier.fillMaxWidth()
//                    .widthIn(max = 360.dp)
//                    .imePadding(),
//                shape = TextKitTheme.shapes.medium,
//                border = BorderStroke(1.dp, TextKitTheme.colors.outlineVariant),
//                colors = CardDefaults.cardColors(
//                    containerColor = TextKitTheme.colors.surface,
//                    contentColor = TextKitTheme.colors.onSurface,
//                ),
//                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
//            ) {
//                Column(
//                    modifier = Modifier.padding(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(10.dp),
//                ) {
//                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
//                        Text(
//                            text = if (isEdit) "Edit link" else "Add link",
//                            style = textKitTitleStyle(),
//                            modifier = Modifier.weight(1f),
//                        )
//                        IconButton(onClick = onClose, modifier = Modifier.size(28.dp)) {
//                            Icon(Icons.Rounded.Close, "Close", tint = TextKitTheme.colors.onSurfaceVariant)
//                        }
//                    }
//                    HorizontalDivider(color = TextKitTheme.colors.outlineVariant)
//
//                    SimpleInputFieldWithErrorNew(
//                        text = text,
//                        hint = "Link text",
//                        showClose = false,
//                        onValueChange = { text = it },
//                    )
//                    Column {
//                        SimpleInputFieldWithErrorNew(
//                            text = url,
//                            hint = "Link / YouTube URL",
//                            errorMessage = urlError,
//                            showClose = false,
//                            onValueChange = { url = it; urlError = null },
//                        )
//                        FormErrorText(urlError)
//                    }
//
//                    if (isYouTube) {
//                        TimeRow(
//                            title = "Part 1 time stamps (optional)",
//                            start = start1, end = end1,
//                            startError = start1Error, endError = end1Error,
//                            onStart = { start1 = it; start1Error = null },
//                            onEnd = { end1 = it; end1Error = null },
//                        )
//                        TimeRow(
//                            title = "Part 2 time stamps (optional)",
//                            start = start2, end = end2,
//                            startError = start2Error, endError = end2Error,
//                            onStart = { start2 = it; start2Error = null },
//                            onEnd = { end2 = it; end2Error = null },
//                            isLast = true,
//                        )
//                    }
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(8.dp),
//                    ) {
//                        TeacherActionButton(
//                            text = if (isYouTube && hasPart2) "Open part 1" else "Open",
//                            modifier = Modifier.weight(1f),
//                            style = ActionButtonStyle.Outline,
//                            height = 40.dp,
//                            onClick = { openPart(1) },
//                        )
//                        if (isYouTube && hasPart2) {
//                            TeacherActionButton(
//                                text = "Open part 2",
//                                modifier = Modifier.weight(1f),
//                                style = ActionButtonStyle.Outline,
//                                height = 40.dp,
//                                onClick = { openPart(2) },
//                            )
//                        }
//                        if (isEdit) {
//                            TeacherActionButton(
//                                text = "Remove",
//                                modifier = Modifier.weight(1f),
//                                style = ActionButtonStyle.Danger,
//                                height = 40.dp,
//                                onClick = { state.removeLink(link.range); state.dismissLinkPopup() },
//                            )
//                        }
//                    }
//
//                    MainButtonNew(heading = "Save") { save() }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun TimeRow(
//    title: String,
//    start: String,
//    end: String,
//    startError: String?,
//    endError: String?,
//    onStart: (String) -> Unit,
//    onEnd: (String) -> Unit,
//    isLast: Boolean = false,
//) {
//    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
//        Text(title, style = textKitCaptionStyle(), color = TextKitTheme.colors.onSurfaceVariant)
//        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//            Column(Modifier.weight(1f)) {
//                SimpleInputFieldWithErrorNew(
//                    text = start,
//                    hint = "Start (2:00)",
//                    errorMessage = startError,
//                    showClose = false,
//                    maxLength = 8,
//                    onValueChange = onStart,
//                )
//                FormErrorText(startError)
//            }
//            Column(Modifier.weight(1f)) {
//                SimpleInputFieldWithErrorNew(
//                    text = end,
//                    hint = "End (3:00)",
//                    errorMessage = endError,
//                    showClose = false,
//                    maxLength = 8,
//                    isLast = isLast,
//                    onValueChange = onEnd,
//                )
//                FormErrorText(endError)
//            }
//        }
//    }
//}

package com.lessonmaker.app.common.dialogue.linkDialouge


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lessonmaker.app.common.ActionButtonStyle
import com.lessonmaker.app.common.FormErrorText
import com.lessonmaker.app.common.MainButtonNew
import com.lessonmaker.app.common.SimpleInputFieldWithErrorNew
import com.lessonmaker.app.common.TeacherActionButton
import com.lessonmaker.app.shared.textKit.ui.utils.textKitCaptionStyle
import com.lessonmaker.app.shared.textKit.ui.utils.textKitTitleStyle
import com.lessonmaker.app.shared.textkit.theme.TextKitTheme
import com.lessonmaker.app.shared.textkit.ui.YouTubeLinkUtils
import com.lessonmaker.app.shared.textkit.ui.state.TextKitState

/**
 * Teacher dialog to ADD or EDIT a link (shows while state.activeLink != null).
 * YouTube urls get two OPTIONAL time-stamp parts, embedded in the url when filled.
 */
@Composable
fun TextKitLinkDialog(
    state: TextKitState,
    onPreview: ((videoId: String, parts: List<YouTubeLinkUtils.Segment>) -> Unit)? = null,
) {
    val link = state.activeLink ?: return
    val isEdit = link.url.isNotEmpty()
    val onClose = { state.dismissLinkPopup() }
    val uriHandler = LocalUriHandler.current

    key(link.range.start, link.range.end, link.url) {
        val (a0, b0) = remember(link.url) { YouTubeLinkUtils.readTimes(link.url) }
        val (c0, d0) = remember(link.url) { YouTubeLinkUtils.readTimes2(link.url) }
        var text by remember { mutableStateOf(link.text) }
        var url by remember { mutableStateOf(link.url) }
        var start1 by remember { mutableStateOf(YouTubeLinkUtils.formatTime(a0)) }
        var end1 by remember { mutableStateOf(YouTubeLinkUtils.formatTime(b0)) }
        var start2 by remember { mutableStateOf(YouTubeLinkUtils.formatTime(c0)) }
        var end2 by remember { mutableStateOf(YouTubeLinkUtils.formatTime(d0)) }
        var urlError by remember { mutableStateOf<String?>(null) }
        var start1Error by remember { mutableStateOf<String?>(null) }
        var end1Error by remember { mutableStateOf<String?>(null) }
        var start2Error by remember { mutableStateOf<String?>(null) }
        var end2Error by remember { mutableStateOf<String?>(null) }
        val isYouTube = YouTubeLinkUtils.isYouTube(url)

        fun save() {
            val raw = url.trim()
            urlError = if (raw.isEmpty()) "Please enter link" else null
            start1Error = null; end1Error = null; start2Error = null; end2Error = null
            var a: Int? = null; var b: Int? = null; var c: Int? = null; var d: Int? = null
            if (isYouTube) {
                a = YouTubeLinkUtils.parseTime(start1); b = YouTubeLinkUtils.parseTime(end1)
                c = YouTubeLinkUtils.parseTime(start2); d = YouTubeLinkUtils.parseTime(end2)
                val fmt = "Use 2:00 or 1:02:30"
                if (start1.isNotBlank() && a == null) start1Error = fmt
                if (end1.isNotBlank() && b == null) end1Error = fmt
                if (start2.isNotBlank() && c == null) start2Error = fmt
                if (end2.isNotBlank() && d == null) end2Error = fmt
                val formatOk = start1Error == null && end1Error == null &&
                        start2Error == null && end2Error == null
                if (formatOk) {
                    val part1Empty = a == null && b == null
                    val part2Empty = c == null && d == null
                    if (part1Empty && !part2Empty) {      // only part 2 filled -> becomes part 1
                        a = c; b = d; c = null; d = null
                    } else if (!part2Empty) {
                        if (b == null) end1Error = "Set end of part 1"
                        else if (c == null) start2Error = "Set start of part 2"
                        else if (c < b) start2Error = "Start after part 1 ends"
                    }
                    if (a != null && b != null && b <= a) end1Error = "End must be after start"
                    if (c != null && d != null && d <= c) end2Error = "End must be after start"
                }
            }
            val hasError = urlError != null || start1Error != null || end1Error != null ||
                    start2Error != null || end2Error != null
            if (hasError) return
            val finalUrl = if (isYouTube) YouTubeLinkUtils.buildUrl(raw, a, b, c, d) else raw
            state.updateLinkText(newText = text.ifBlank { finalUrl }, url = finalUrl, range = link.range)
            state.dismissLinkPopup()                      // Save closes the dialog
        }

        /** Plays the parts exactly like the student will see them (part 1, then part 2). */
        fun preview() {
            val raw = url.trim()
            if (raw.isEmpty()) { urlError = "Please enter link"; return }
            val id = YouTubeLinkUtils.videoId(raw)
            if (id == null) { runCatching { uriHandler.openUri(raw) }; return }
            val s1 = YouTubeLinkUtils.parseTime(start1); val e1 = YouTubeLinkUtils.parseTime(end1)
            val s2 = YouTubeLinkUtils.parseTime(start2); val e2 = YouTubeLinkUtils.parseTime(end2)
            val parts = listOf(YouTubeLinkUtils.Segment(s1, e1), YouTubeLinkUtils.Segment(s2, e2))
                .filter { !it.isEmpty }
            if (onPreview != null) onPreview(id, parts)
            else runCatching { uriHandler.openUri(YouTubeLinkUtils.buildUrl(raw, s1, e1)) }
        }

        Dialog(
            onDismissRequest = onClose,
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
                    .widthIn(max = 360.dp)
                    .imePadding(),
                shape = TextKitTheme.shapes.medium,
                border = BorderStroke(1.dp, TextKitTheme.colors.outlineVariant),
                colors = CardDefaults.cardColors(
                    containerColor = TextKitTheme.colors.surface,
                    contentColor = TextKitTheme.colors.onSurface,
                ),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = if (isEdit) "Edit link" else "Add link",
                            style = textKitTitleStyle(),
                            modifier = Modifier.weight(1f),
                        )
                        IconButton(onClick = onClose, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Rounded.Close, "Close", tint = TextKitTheme.colors.onSurfaceVariant)
                        }
                    }
                    HorizontalDivider(color = TextKitTheme.colors.outlineVariant)

                    SimpleInputFieldWithErrorNew(
                        text = text,
                        hint = "Link text",
                        showClose = false,
                        onValueChange = { text = it },
                    )
                    Column {
                        SimpleInputFieldWithErrorNew(
                            text = url,
                            hint = "Link / YouTube URL",
                            errorMessage = urlError,
                            showClose = false,
                            onValueChange = { url = it; urlError = null },
                        )
                        FormErrorText(urlError)
                    }

                    if (isYouTube) {
                        TimeRow(
                            title = "Part 1 time stamps (optional)",
                            start = start1, end = end1,
                            startError = start1Error, endError = end1Error,
                            onStart = { start1 = it; start1Error = null },
                            onEnd = { end1 = it; end1Error = null },
                        )
                        TimeRow(
                            title = "Part 2 time stamps (optional)",
                            start = start2, end = end2,
                            startError = start2Error, endError = end2Error,
                            onStart = { start2 = it; start2Error = null },
                            onEnd = { end2 = it; end2Error = null },
                            isLast = true,
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        TeacherActionButton(
                            text = if (isYouTube) "Play preview" else "Open",
                            modifier = Modifier.weight(1f),
                            style = ActionButtonStyle.Outline,
                            height = 40.dp,
                            onClick = { preview() },
                        )
                        if (isEdit) {
                            TeacherActionButton(
                                text = "Remove",
                                modifier = Modifier.weight(1f),
                                style = ActionButtonStyle.Danger,
                                height = 40.dp,
                                onClick = { state.removeLink(link.range); state.dismissLinkPopup() },
                            )
                        }
                    }

                    MainButtonNew(heading = "Save") { save() }
                }
            }
        }
    }
}

@Composable
private fun TimeRow(
    title: String,
    start: String,
    end: String,
    startError: String?,
    endError: String?,
    onStart: (String) -> Unit,
    onEnd: (String) -> Unit,
    isLast: Boolean = false,
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, style = textKitCaptionStyle(), color = TextKitTheme.colors.onSurfaceVariant)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(Modifier.weight(1f)) {
                SimpleInputFieldWithErrorNew(
                    text = start,
                    hint = "Start (2:00)",
                    errorMessage = startError,
                    showClose = false,
                    maxLength = 8,
                    onValueChange = onStart,
                )
                FormErrorText(startError)
            }
            Column(Modifier.weight(1f)) {
                SimpleInputFieldWithErrorNew(
                    text = end,
                    hint = "End (3:00)",
                    errorMessage = endError,
                    showClose = false,
                    maxLength = 8,
                    isLast = isLast,
                    onValueChange = onEnd,
                )
                FormErrorText(endError)
            }
        }
    }
}