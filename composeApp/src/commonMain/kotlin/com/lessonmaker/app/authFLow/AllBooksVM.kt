package com.lessonmaker.app.authFLow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.lessonmaker.app.base.ShareViewModelInterface
import com.lessonmaker.app.base.SharedBaseViewModel
import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.extensionFunctions.checkForSuccess
import com.lessonmaker.app.models.book.Book
import com.lessonmaker.app.network.ApiService
import com.lessonmaker.app.network.getOnlySuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AllBooksVM(
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val apiService: ApiService,
    val commonLoading: CommonLoading
) : SharedBaseViewModel<AllBooksVM.ScreenInterface>() {

    var isLoading by mutableStateOf(false)
        private set

    var showEmptyScreen by mutableStateOf(false)

    private val _books =
        MutableStateFlow<List<Book>>(emptyList())

    val books: StateFlow<List<Book>> =
        _books.asStateFlow()

    fun getAllBooks() {
        if (isLoading) return

        viewModelScope.launch {
            isLoading = true
            showEmptyScreen = false

            try {
                val response = apiService
                    .getAllBooks()
                    .getOnlySuccess()
                    ?: return@launch

                if (response.status.checkForSuccess()) {
                    val books = response.data ?: emptyList()

                    _books.value = books
                    showEmptyScreen = books.isEmpty()
                } else {
                    _books.value = emptyList()
                    showEmptyScreen = true
                }
            } catch (e: Exception) {
                _books.value = emptyList()
                showEmptyScreen = true
            } finally {
                isLoading = false
            }
        }
    }

    interface ScreenInterface : ShareViewModelInterface
}