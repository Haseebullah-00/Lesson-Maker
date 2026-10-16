package com.lessonmaker.app.shared.media.excel


/** Reads the first sheet of an .xlsx file. Each inner list is one row. */
expect fun parseXlsx(bytes: ByteArray): List<List<String>>

/** Call this one from your screen. It picks the right parser by file extension. */
fun readSpreadsheet(fileName: String?, bytes: ByteArray): List<List<String>> {
    val name = fileName?.lowercase().orEmpty()
    return when {
        name.endsWith(".csv") -> parseCsv(bytes.decodeToString())
        name.endsWith(".xlsx") -> parseXlsx(bytes)
        else -> emptyList() // .xls (old format) is not supported
    }
}

fun parseCsv(text: String): List<List<String>> {
    val rows = mutableListOf<List<String>>()
    var row = mutableListOf<String>()
    val cell = StringBuilder()
    var inQuotes = false
    var i = 0
    while (i < text.length) {
        val c = text[i]
        when {
            inQuotes && c == '"' && i + 1 < text.length && text[i + 1] == '"' -> { cell.append('"'); i++ }
            c == '"' -> inQuotes = !inQuotes
            c == ',' && !inQuotes -> { row.add(cell.toString()); cell.clear() }
            (c == '\n' || c == '\r') && !inQuotes -> {
                if (c == '\r' && i + 1 < text.length && text[i + 1] == '\n') i++
                row.add(cell.toString()); cell.clear()
                rows.add(row); row = mutableListOf()
            }
            else -> cell.append(c)
        }
        i++
    }
    if (cell.isNotEmpty() || row.isNotEmpty()) { row.add(cell.toString()); rows.add(row) }
    return rows
}