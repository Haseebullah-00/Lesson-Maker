package com.lessonmaker.app.shared.media.excel

expect class SharedExcel {
    fun toByteArray(): ByteArray?
    fun getFileName(): String?
    fun getUri(): Any?
}