package com.lessonmaker.app.shared.media.pdf

import kotlinx.cinterop.*
import kotlinx.cinterop.get
import platform.Foundation.*

actual class SharedPdf(private val url: NSURL) {

    actual fun toByteArray(): ByteArray? {
        val data = NSData.dataWithContentsOfURL(url) ?: return null
        return data.toByteArray()
    }

    actual fun getFileName(): String? {
        return url.lastPathComponent
    }

    actual fun getUri(): Any? = url
}

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
    val bytes = this.bytes?.reinterpret<ByteVar>() ?: return ByteArray(0)
    return ByteArray(this.length.toInt()) { i -> bytes[i] }
}
