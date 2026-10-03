package com.lessonmaker.app.network

import io.ktor.http.HttpStatusCode

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val statusCode: HttpStatusCode, val message: String) : ApiResult<Nothing>()
}
