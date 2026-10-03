package com.lessonmaker.app.shared.platformExtensions

import android.util.Log
import io.ktor.util.cio.readChannel
import io.ktor.utils.io.ByteReadChannel
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.text.NumberFormat
import java.util.Locale




actual fun deviceType(): String {
    return "android"
}
actual fun checkHttpCall(url: String, params: Map<String, String>) {
    Log.d("checkHttpCall", "checkHttpCall: ${params.toString()}")
    val client = OkHttpClient.Builder()
        .followRedirects(true)
        .retryOnConnectionFailure(true)
        .build()

    val formBodyBuilder = FormBody.Builder()
    params.forEach { (k, v) -> formBodyBuilder.add(k, v) }

    val request = Request.Builder()
        .url(url)
        .post(formBodyBuilder.build())
        .build()

    val start = System.nanoTime()
    val response = client.newCall(request).execute()
    val body = response.body?.string()
    val durationMs = (System.nanoTime() - start) / 1_000_000
    println("OkHttp raw POST took: $durationMs ms, body size: ${body?.length}")
}
actual fun getFileStream(path: String): ByteReadChannel {
    val file = File(path)
    return file.readChannel()
}


actual fun formatPrice(value: String): String {
    return try {
        val number = value.toLong()
        NumberFormat.getNumberInstance(Locale.US).format(number)
    } catch (e: Exception) {
        value
    }
}
