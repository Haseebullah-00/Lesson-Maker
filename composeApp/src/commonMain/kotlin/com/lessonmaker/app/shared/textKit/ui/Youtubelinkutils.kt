package com.lessonmaker.app.shared.textkit.ui

/** Helpers for YouTube links with optional start / end timestamps (seconds). */
object YouTubeLinkUtils {

    private val idRegex = Regex(
        """(?:youtu\.be/|youtube\.com/(?:watch\?(?:.*&)?v=|embed/|shorts/|live/))([A-Za-z0-9_-]{11})"""
    )

    fun videoId(url: String): String? = idRegex.find(url.trim())?.groupValues?.get(1)

    fun isYouTube(url: String): Boolean = videoId(url) != null

    /** "120", "2:00", "1:02:30", "2m", "1h2m30s" -> seconds; blank/invalid -> null. */
    fun parseTime(input: String): Int? {
        val s = input.trim().lowercase()
        if (s.isEmpty()) return null
        s.toIntOrNull()?.let { return it.takeIf { v -> v >= 0 } }
        if (':' in s) {
            val parts = s.split(':').map { it.toIntOrNull() ?: return null }
            if (parts.size !in 2..3 || parts.any { it < 0 }) return null
            return parts.fold(0) { acc, p -> acc * 60 + p }
        }
        val m = Regex("""^(?:(\d+)h)?(?:(\d+)m)?(?:(\d+)s)?$""").matchEntire(s) ?: return null
        val (h, mi, se) = m.destructured
        return (h.toIntOrNull() ?: 0) * 3600 + (mi.toIntOrNull() ?: 0) * 60 + (se.toIntOrNull() ?: 0)
    }

    /** Seconds -> "m:ss" (or "h:mm:ss"); null -> "". */
    fun formatTime(seconds: Int?): String {
        if (seconds == null) return ""
        val h = seconds / 3600
        val m = (seconds % 3600) / 60
        val s = seconds % 60
        val ss = s.toString().padStart(2, '0')
        return if (h > 0) "$h:${m.toString().padStart(2, '0')}:$ss" else "$m:$ss"
    }

    /** Reads start / end (seconds) back out of a stored YouTube URL. */
    fun readTimes(url: String): Pair<Int?, Int?> {
        fun param(vararg names: String): Int? = names.firstNotNullOfOrNull { n ->
            Regex("""[?&]$n=([0-9hms]+)""").find(url)?.groupValues?.get(1)?.let(::parseTime)
        }
        return param("start", "t") to param("end")
    }

    /**
     * Builds the final URL.
     * - no timestamps  -> https://www.youtube.com/watch?v=ID
     * - start only     -> https://www.youtube.com/watch?v=ID&t=120s
     * - start and/or end -> https://www.youtube.com/embed/ID?start=120&end=240
     *   (only the embed form honours an end time).
     * An end that is not after the start is ignored.
     */
    fun buildUrl(url: String, start: Int?, end: Int?): String {
        val id = videoId(url) ?: return url
        val safeEnd = end?.takeIf { it > (start ?: 0) }
        return when {
            safeEnd != null -> buildString {
                append("https://www.youtube.com/embed/$id?")
                append(listOfNotNull(start?.let { "start=$it" }, "end=$safeEnd").joinToString("&"))
            }
            start != null && start > 0 -> "https://www.youtube.com/watch?v=$id&t=${start}s"
            else -> "https://www.youtube.com/watch?v=$id"
        }
    }
}