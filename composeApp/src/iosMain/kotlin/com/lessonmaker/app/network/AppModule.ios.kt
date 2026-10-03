package com.lessonmaker.app.network

import io.ktor.client.*
import io.ktor.client.engine.darwin.*

actual fun provideHttpClient(): HttpClient {
    return createHttpClient(Darwin.create())
}
actual fun NetworkLogs(tag: String, message: String) {
    println("$tag: $message")
}