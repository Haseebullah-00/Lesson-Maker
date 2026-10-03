package com.lessonmaker.app.di


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.lessonmaker.app.common.dialogue.UiState
import com.lessonmaker.app.network.ApiException


class CommonLoading  {
    var apiException by mutableStateOf<ApiException?>(null)
    var alertMessage by mutableStateOf("")
    var apiCall by mutableStateOf<UiState<Any>>(UiState.Empty)
}