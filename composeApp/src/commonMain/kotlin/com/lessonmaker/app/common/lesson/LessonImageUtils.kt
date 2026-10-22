package com.lessonmaker.app.common.lesson


import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Turns a picked image into the URL that TextKit stores inside the lesson JSON.
 *
 * ⚠️ TODAY (testing): the image is embedded as a "data:" URL, so it works offline with no server.
 *    Problem: a normal phone photo makes the lesson JSON many MB. Fine for testing only.
 *
 * ✅ FOR REAL USE: replace the body with your API upload and return the https URL of the
 *    uploaded image, for example:
 *
 *        suspend fun uploadLessonImage(fileName: String, bytes: ByteArray): String {
 *            val response = apiService.uploadImage(fileName, bytes)   // your API call
 *            return response.imageUrl                                  // "https://..."
 *        }
 *
 *    Nothing else in the editor needs to change.
 */
suspend fun uploadLessonImage(fileName: String, bytes: ByteArray): String =
    withContext(Dispatchers.Default) { bytesToDataUrl(bytes) }

@OptIn(ExperimentalEncodingApi::class)
fun bytesToDataUrl(bytes: ByteArray): String {
    val isPng = bytes.size > 4 &&
            bytes[0] == 0x89.toByte() && bytes[1] == 'P'.code.toByte() &&
            bytes[2] == 'N'.code.toByte() && bytes[3] == 'G'.code.toByte()
    val mime = if (isPng) "image/png" else "image/jpeg"
    return "data:$mime;base64," + Base64.Default.encode(bytes)
}

/** JSON that TextKit expects for an image embed: {"type":"image","attrs":{"url":"..."}} */
fun imageEmbedJson(url: String): String {
    val safe = url.replace("\\", "\\\\").replace("\"", "\\\"")
    return """{"type":"image","attrs":{"url":"$safe"}}"""
}
