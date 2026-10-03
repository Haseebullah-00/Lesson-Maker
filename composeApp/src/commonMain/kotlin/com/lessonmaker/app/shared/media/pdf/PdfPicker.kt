package com.lessonmaker.app.shared.media.pdf

expect class SharedPdf {
    fun toByteArray(): ByteArray?
    fun getFileName(): String?
    fun getUri(): Any?
}
