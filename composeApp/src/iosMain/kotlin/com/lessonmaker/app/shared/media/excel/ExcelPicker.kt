package com.lessonmaker.app.shared.media.excel

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.reinterpret
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import kotlinx.cinterop.get


actual class SharedExcel(private val url: NSURL) {

    actual fun toByteArray(): ByteArray? {
        val data = NSData.dataWithContentsOfURL(url) ?: return null
        return data.excelToByteArray()
    }

    actual fun getFileName(): String? {
        return url.lastPathComponent
    }

    actual fun getUri(): Any? = url
}

// Named differently from the PDF helper (NSData.toByteArray) to avoid a
// "conflicting overloads" error, since both live in the same module.
@OptIn(ExperimentalForeignApi::class)
fun NSData.excelToByteArray(): ByteArray {
    val bytes = this.bytes?.reinterpret<ByteVar>() ?: return ByteArray(0)
    return ByteArray(this.length.toInt()) { i -> bytes[i] }
}