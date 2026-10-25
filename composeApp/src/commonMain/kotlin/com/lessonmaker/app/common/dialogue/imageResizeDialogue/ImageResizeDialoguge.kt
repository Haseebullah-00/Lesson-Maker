package com.lessonmaker.app.common.dialogue.imageResizeDialogue


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lessonmaker.app.shared.textKit.ui.utils.textKitTitleStyle
import com.lessonmaker.app.shared.textkit.editor.core.TextKitEditorManager
import com.lessonmaker.app.shared.textkit.editor.core.parser.DEFAULT_IMAGE_HEIGHT_DP
import com.lessonmaker.app.shared.textkit.editor.core.parser.DEFAULT_IMAGE_WIDTH_DP

import com.lessonmaker.app.shared.textkit.editor.core.parser.embedImageHeightOf
import com.lessonmaker.app.shared.textkit.editor.core.parser.embedImageWidthOf
import com.lessonmaker.app.shared.textkit.editor.core.parser.embedWithImageSize
import com.lessonmaker.app.shared.textkit.theme.TextKitTheme
import kotlin.math.roundToInt

@Composable
fun TextKitImageResizeDialog(
    embed: TextKitEditorManager.EmbedInfo,
    onClose: () -> Unit,
    onSync: (String) -> Unit,
) {
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().widthIn(max = 360.dp),
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
                    Text(text = "Resize image", style = textKitTitleStyle(), modifier = Modifier.weight(1f))
                    IconButton(onClick = onClose, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Rounded.Close, "Close", tint = TextKitTheme.colors.onSurfaceVariant)
                    }
                }
                HorizontalDivider(color = TextKitTheme.colors.outlineVariant)
                ImageSizeControls(embed.rawJson, onSync)
            }
        }
    }
}

private const val MIN_IMAGE_HEIGHT_DP = 60
private const val MAX_IMAGE_HEIGHT_DP = 800

private const val MIN_IMAGE_WIDTH_DP = 80
private const val MAX_IMAGE_WIDTH_DP = 800

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
                onValueChange = onChange,
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