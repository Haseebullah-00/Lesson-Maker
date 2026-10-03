package com.lessonmaker.app.common.dialogue

sealed class UiState<out T> {
    object Empty: UiState<Nothing>()
    object Loading: UiState<Nothing>()
    data class Success<out T>(val data: T): UiState<T>()
    data class Failure(val error: String?): UiState<Nothing>()
}