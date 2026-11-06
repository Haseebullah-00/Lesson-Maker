//package com.lessonmaker.app.common.player
//
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import com.lessonmaker.app.shared.textkit.ui.YouTubeLinkUtils
//import com.multiplatform.webview.web.WebView
//import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData
//
///** Plays a YouTube video part by part (e.g. 2:00-3:00 then 4:00-7:00), automatically. */
//@Composable
//fun YouTubeSegmentPlayer(
//    videoId: String,
//    parts: List<YouTubeLinkUtils.Segment>,
//    modifier: Modifier = Modifier,
//) {
//    val html = remember(videoId, parts) { buildPlayerHtml(videoId, parts) }
//    // baseUrl must be an https origin, otherwise YouTube may refuse to embed (error 153)
//    val state = rememberWebViewStateWithHTMLData(data = html, baseUrl = "https://www.youtube.com")
//    LaunchedEffect(state) { state.webSettings.isJavaScriptEnabled = true }
//    WebView(state = state, modifier = modifier)
//}
//
//private fun buildPlayerHtml(videoId: String, parts: List<YouTubeLinkUtils.Segment>): String {
//    // [[120,180],[240,420]]  (null = from start / to end). No parts = whole video.
//    val partsJs = if (parts.isEmpty()) "[[null,null]]" else
//        parts.joinToString(prefix = "[", postfix = "]") { "[${it.start ?: "null"},${it.end ?: "null"}]" }
//
//    return """
//<!DOCTYPE html>
//<html><head>
//<meta name="viewport" content="width=device-width, initial-scale=1">
//<style>html,body{margin:0;height:100%;background:#000}#p{width:100%;height:100%}</style>
//</head><body>
//<div id="p"></div>
//<script src="https://www.youtube.com/iframe_api"></script>
//<script>
//var VIDEO = "$videoId";
//var PARTS = $partsJs;
//var i = 0, player;
//
//function load(n) {
//  var s = PARTS[n], o = { videoId: VIDEO };
//  if (s[0] !== null) o.startSeconds = s[0];
//  if (s[1] !== null) o.endSeconds = s[1];
//  player.loadVideoById(o);
//}
//
//function onYouTubeIframeAPIReady() {
//  player = new YT.Player('p', {
//    width: '100%', height: '100%', videoId: VIDEO,
//    playerVars: { playsinline: 1, rel: 0, modestbranding: 1, autoplay: 1 },
//    events: {
//      onReady: function () { load(0); },
//      onStateChange: function (e) {
//        // ENDED fires when a part reaches its endSeconds -> go to the next part
//        if (e.data === YT.PlayerState.ENDED && i < PARTS.length - 1) { i++; load(i); }
//      }
//    }
//  });
//}
//</script>
//</body></html>
//""".trimIndent()
//}


package com.lessonmaker.app.common.player


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.lessonmaker.app.shared.textkit.ui.YouTubeLinkUtils
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData

/** Plays a YouTube video part by part (e.g. 2:00-3:00 then 4:00-7:00), automatically. */
//@Composable
//fun YouTubeSegmentPlayer(
//    videoId: String,
//    parts: List<YouTubeLinkUtils.Segment>,
//    modifier: Modifier = Modifier,
//) {
//    val html = remember(videoId, parts) { buildPlayerHtml(videoId, parts) }
//    // baseUrl must be an https origin, otherwise YouTube may refuse to embed (error 153)
//    val state = rememberWebViewStateWithHTMLData(data = html, baseUrl = "https://www.youtube.com")
//    LaunchedEffect(state) { state.webSettings.isJavaScriptEnabled = true }
//    WebView(state = state, modifier = modifier)
//}
//
//private fun buildPlayerHtml(videoId: String, parts: List<YouTubeLinkUtils.Segment>): String {
//    // [[120,180],[240,420]]  (null = from start / to end). No parts = whole video.
//    val partsJs = if (parts.isEmpty()) "[[null,null]]" else
//        parts.joinToString(prefix = "[", postfix = "]") { "[${it.start ?: "null"},${it.end ?: "null"}]" }
//
//    return """
//<!DOCTYPE html>
//<html><head>
//<meta name="viewport" content="width=device-width, initial-scale=1">
//<style>html,body{margin:0;height:100%;background:#000}#p{width:100%;height:100%}</style>
//</head><body>
//<div id="p"></div>
//<script src="https://www.youtube.com/iframe_api"></script>
//<script>
//var VIDEO = "$videoId";
//var PARTS = $partsJs;
//var i = 0, player;
//
//function load(n) {
//  var s = PARTS[n], o = { videoId: VIDEO };
//  if (s[0] !== null) o.startSeconds = s[0];
//  if (s[1] !== null) o.endSeconds = s[1];
//  player.loadVideoById(o);
//}
//
//function onYouTubeIframeAPIReady() {
//  player = new YT.Player('p', {
//    width: '100%', height: '100%', videoId: VIDEO,
//    playerVars: { playsinline: 1, rel: 0, modestbranding: 1, autoplay: 1 },
//    events: {
//      onReady: function () { load(0); },
//      onStateChange: function (e) {
//        // ENDED fires when a part reaches its endSeconds -> go to the next part
//        if (e.data === YT.PlayerState.ENDED && i < PARTS.length - 1) { i++; load(i); }
//      },
//      onError: function () {
//        document.body.innerHTML =
//          '<p style="color:#fff;font:14px sans-serif;padding:16px">This video cannot be played here. ' +
//          '<a style="color:#8ab4f8" href="https://www.youtube.com/watch?v=' + VIDEO + '">Open in YouTube</a></p>';
//      }
//    }
//  });
//}
//</script>
//</body></html>
//""".trimIndent()
//}
@Composable
fun YouTubeSegmentPlayer(
    videoId: String,
    parts: List<YouTubeLinkUtils.Segment>,
    modifier: Modifier = Modifier,
) {
    val html = remember(videoId, parts) { buildPlayerHtml(videoId, parts) }
    // ORIGIN must be a real https origin that is NOT youtube.com (youtube.com -> error 152, no origin -> 153).
    val state = rememberWebViewStateWithHTMLData(data = html, baseUrl = PLAYER_ORIGIN)
    LaunchedEffect(state) { state.webSettings.isJavaScriptEnabled = true }
    WebView(state = state, modifier = modifier)
}

/** Replace with your own domain. Must be https and must not be youtube.com. */
private const val PLAYER_ORIGIN = "https://lessonmaker.app"

private fun buildPlayerHtml(videoId: String, parts: List<YouTubeLinkUtils.Segment>): String {
    // [[120,180],[240,420]]  (null = from start / to end). No parts = whole video.
    val partsJs = if (parts.isEmpty()) "[[null,null]]" else
        parts.joinToString(prefix = "[", postfix = "]") { "[${it.start ?: "null"},${it.end ?: "null"}]" }

    return """
<!DOCTYPE html>
<html><head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>html,body{margin:0;height:100%;background:#000}#p{width:100%;height:100%}</style>
</head><body>
<div id="p"></div>
<script src="https://www.youtube.com/iframe_api"></script>
<script>
var VIDEO = "$videoId";
var PARTS = $partsJs;
var i = 0, player;
 
function load(n) {
  var s = PARTS[n], o = { videoId: VIDEO };
  if (s[0] !== null) o.startSeconds = s[0];
  if (s[1] !== null) o.endSeconds = s[1];
  player.loadVideoById(o);
}
 
function onYouTubeIframeAPIReady() {
  player = new YT.Player('p', {
    width: '100%', height: '100%', videoId: VIDEO,
    playerVars: { playsinline: 1, rel: 0, modestbranding: 1, autoplay: 1, origin: "$PLAYER_ORIGIN" },
    events: {
      onReady: function () { load(0); },
      onStateChange: function (e) {
        // ENDED fires when a part reaches its endSeconds -> go to the next part
        if (e.data === YT.PlayerState.ENDED && i < PARTS.length - 1) { i++; load(i); }
      },
      onError: function (e) {
        document.body.innerHTML =
          '<p style="color:#fff;font:14px sans-serif;padding:16px">This video cannot be played here (error ' + e.data + '). ' +
          '<a style="color:#8ab4f8" href="https://www.youtube.com/watch?v=' + VIDEO + '">Open in YouTube</a></p>';
      }
    }
  });
}
</script>
</body></html>
""".trimIndent()
}