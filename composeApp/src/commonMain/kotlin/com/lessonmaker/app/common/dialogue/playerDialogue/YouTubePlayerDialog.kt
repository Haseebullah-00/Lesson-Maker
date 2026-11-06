////package com.lessonmaker.app.common.dialogue.playerDialogue
////
////
////import androidx.compose.foundation.background
////import androidx.compose.foundation.layout.Column
////import androidx.compose.foundation.layout.aspectRatio
////import androidx.compose.foundation.layout.fillMaxWidth
////import androidx.compose.foundation.layout.padding
////import androidx.compose.runtime.Composable
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.unit.dp
////import androidx.compose.ui.window.Dialog
////import androidx.compose.ui.window.DialogProperties
////import com.lessonmaker.app.common.ActionButtonStyle
////import com.lessonmaker.app.common.TeacherActionButton
////import com.lessonmaker.app.common.player.YouTubeSegmentPlayer
////import com.lessonmaker.app.shared.textkit.ui.YouTubeLinkUtils
////
////data class YouTubePlayRequest(
////    val videoId: String,
////    val parts: List<YouTubeLinkUtils.Segment>,
////)
////
/////** Full-width player dialog. Shows while [request] != null; Close / back calls [onClose]. */
////@Composable
////fun YouTubePlayerDialog(request: YouTubePlayRequest?, onClose: () -> Unit) {
////    if (request == null) return
////    Dialog(
////        onDismissRequest = onClose,
////        properties = DialogProperties(usePlatformDefaultWidth = false),
////    ) {
////        Column(Modifier.fillMaxWidth().background(Color.Black)) {
////            YouTubeSegmentPlayer(
////                videoId = request.videoId,
////                parts = request.parts,
////                modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f),
////            )
////            TeacherActionButton(
////                text = "Close",
////                modifier = Modifier.fillMaxWidth().padding(12.dp),
////                style = ActionButtonStyle.Outline,
////                onClick = onClose,
////            )
////        }
////    }
////}
//
//package com.lessonmaker.app.common.dialogue.playerDialogue
//
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.aspectRatio
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.window.Dialog
//import androidx.compose.ui.window.DialogProperties
//import com.lessonmaker.app.common.ActionButtonStyle
//import com.lessonmaker.app.common.TeacherActionButton
//import com.lessonmaker.app.common.player.YouTubeSegmentPlayer
//import com.lessonmaker.app.shared.textkit.ui.YouTubeLinkUtils
//
//data class YouTubePlayRequest(
//    val videoId: String,
//    val parts: List<YouTubeLinkUtils.Segment>,
//)
//
///** Full-width player dialog. Shows while [request] != null; Close / back calls [onClose]. */
//@Composable
//fun YouTubePlayerDialog(request: YouTubePlayRequest?, onClose: () -> Unit) {
//    if (request == null) return
//    Dialog(
//        onDismissRequest = onClose,
//        properties = DialogProperties(usePlatformDefaultWidth = false),
//    ) {
//        Column(Modifier.fillMaxWidth().background(Color.Black)) {
//            YouTubeSegmentPlayer(
//                videoId = request.videoId,
//                parts = request.parts,
//                modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f),
//            )
//            TeacherActionButton(
//                text = "Close",
//                modifier = Modifier.fillMaxWidth().padding(12.dp),
//                style = ActionButtonStyle.Outline,
//                onClick = onClose,
//            )
//        }
//    }
//}

package com.lessonmaker.app.common.dialogue.playerDialogue


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lessonmaker.app.common.ActionButtonStyle
import com.lessonmaker.app.common.TeacherActionButton
import com.lessonmaker.app.common.player.YouTubeSegmentPlayer
import com.lessonmaker.app.shared.textkit.ui.YouTubeLinkUtils

data class YouTubePlayRequest(
    val videoId: String,
    val parts: List<YouTubeLinkUtils.Segment>,
)

/** Full-width player dialog. Shows while [request] != null; Close / back calls [onClose]. */
@Composable
fun YouTubePlayerDialog(request: YouTubePlayRequest?, onClose: () -> Unit) {
    if (request == null) return
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Column(Modifier.fillMaxWidth()
            .background(Color.Black)) {
            YouTubeSegmentPlayer(
                videoId = request.videoId,
                parts = request.parts,
                modifier = Modifier.fillMaxWidth().weight(1f),
            )
            TeacherActionButton(
                text = "Close",
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                style = ActionButtonStyle.Outline,
                onClick = onClose,
            )
        }
    }
}