
package com.lessonmaker.app.shared.media.excel

import android.util.Xml
import java.io.ByteArrayInputStream
import java.util.zip.ZipInputStream
import org.xmlpull.v1.XmlPullParser

actual fun parseXlsx(bytes: ByteArray): List<List<String>> {
    var sharedStrings = emptyList<String>()
    val sheets = sortedMapOf<String, ByteArray>()

    ZipInputStream(ByteArrayInputStream(bytes)).use { zip ->
        var entry = zip.nextEntry
        while (entry != null) {
            val name = entry.name
            when {
                name == "xl/sharedStrings.xml" -> sharedStrings = parseSharedStrings(zip.readBytes())
                name.startsWith("xl/worksheets/sheet") && name.endsWith(".xml") ->
                    sheets[name] = zip.readBytes()
            }
            entry = zip.nextEntry
        }
    }
    val firstSheet = sheets["xl/worksheets/sheet1.xml"] ?: sheets.values.firstOrNull() ?: return emptyList()
    return parseSheet(firstSheet, sharedStrings)
}

private fun parseSharedStrings(xml: ByteArray): List<String> {
    val parser = Xml.newPullParser()
    parser.setInput(ByteArrayInputStream(xml), null)
    val result = mutableListOf<String>()
    var sb = StringBuilder()
    var event = parser.eventType
    while (event != XmlPullParser.END_DOCUMENT) {
        when (event) {
            XmlPullParser.START_TAG -> when (parser.name) {
                "si" -> sb = StringBuilder()
                "t" -> sb.append(parser.nextText())
            }
            XmlPullParser.END_TAG -> if (parser.name == "si") result.add(sb.toString())
        }
        event = parser.next()
    }
    return result
}

private fun parseSheet(xml: ByteArray, sharedStrings: List<String>): List<List<String>> {
    val parser = Xml.newPullParser()
    parser.setInput(ByteArrayInputStream(xml), null)

    val rows = mutableListOf<List<String>>()
    var current = mutableListOf<String>()
    var cellType: String? = null
    var colIndex = 0
    var value = ""

    var event = parser.eventType
    while (event != XmlPullParser.END_DOCUMENT) {
        when (event) {
            XmlPullParser.START_TAG -> when (parser.name) {
                "row" -> current = mutableListOf()
                "c" -> {
                    cellType = parser.getAttributeValue(null, "t")
                    colIndex = columnIndex(parser.getAttributeValue(null, "r"))
                    value = ""
                }
                "v", "t" -> value += parser.nextText() // "t" = inline string
            }
            XmlPullParser.END_TAG -> when (parser.name) {
                "c" -> {
                    while (current.size < colIndex) current.add("") // keep empty cells aligned
                    current.add(
                        when (cellType) {
                            "s" -> sharedStrings.getOrNull(value.toIntOrNull() ?: -1) ?: ""
                            "b" -> if (value == "1") "TRUE" else "FALSE"
                            else -> value
                        }
                    )
                }
                "row" -> rows.add(current)
            }
        }
        event = parser.next()
    }
    return rows
}

/** "C5" -> 2 (zero-based column) */
private fun columnIndex(ref: String?): Int {
    if (ref == null) return 0
    return ref.takeWhile { it.isLetter() }
        .fold(0) { acc, ch -> acc * 26 + (ch.uppercaseChar() - 'A' + 1) } - 1
}