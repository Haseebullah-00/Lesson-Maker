package com.lessonmaker.app.shared.platformExtensions

import io.ktor.utils.io.ByteReadChannel


expect fun deviceType():String

expect fun checkHttpCall(url: String,params: Map<String, String>)


expect fun getFileStream(path: String): ByteReadChannel

expect fun formatPrice(value: String): String