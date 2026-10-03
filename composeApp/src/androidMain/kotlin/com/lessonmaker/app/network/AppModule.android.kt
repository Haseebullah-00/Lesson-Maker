package com.lessonmaker.app.network

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual fun provideHttpClient(): HttpClient {
    return HttpClient(OkHttp.create {
        config {
            followRedirects(true)
            retryOnConnectionFailure(true)
        }
    }) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    NetworkLogs(tag = "httpCall", message = message)
                }
            }
            level = LogLevel.BODY
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 60_000
            connectTimeoutMillis = 60_000
            socketTimeoutMillis = 60_000
        }

        expectSuccess = true
    }
}

actual fun NetworkLogs(tag:String,message:String){
    Log.i(tag,message)
}