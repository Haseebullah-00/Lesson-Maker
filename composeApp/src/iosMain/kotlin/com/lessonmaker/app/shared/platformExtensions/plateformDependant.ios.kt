package com.lessonmaker.app.shared.platformExtensions

import io.ktor.utils.io.ByteReadChannel
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.dataWithContentsOfFile
import platform.posix.memcpy
import platform.Foundation.*

actual fun deviceType(): String {
    return "ios"
}


actual fun checkHttpCall(url: String,params: Map<String, String>){}


@OptIn(ExperimentalForeignApi::class)
actual fun getFileStream(path: String): ByteReadChannel {
    val data = NSData.dataWithContentsOfFile(path) ?: error("File not found at $path")
    val size = data.length.toInt()
    val byteArray = ByteArray(size)

    byteArray.usePinned { pinned ->
        memcpy(pinned.addressOf(0), data.bytes, data.length)
    }

    return ByteReadChannel(byteArray)
}





actual fun formatPrice(value: String): String {
    return try {
        val number = value.toLong()
        val formatter = NSNumberFormatter().apply {
            numberStyle = NSNumberFormatterDecimalStyle
            locale = NSLocale.localeWithLocaleIdentifier("en_US")
        }
        formatter.stringFromNumber(NSNumber.numberWithLong(number)) ?: value
    } catch (e: Exception) {
        value
    }
}
