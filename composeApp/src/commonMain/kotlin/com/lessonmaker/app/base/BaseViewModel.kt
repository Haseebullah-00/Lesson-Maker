package com.lessonmaker.app.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lessonmaker.app.network.NetworkLogs
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

open class SharedBaseViewModel<VI: ShareViewModelInterface>: ViewModel(){
    var isRefreshing by  mutableStateOf(false)
    var viewInteractor: VI? = null
        set

    override fun onCleared() {
        NetworkLogs("ViewModelState","cleared : ${this::class.simpleName}")
    }
    val callAPIChannel = MutableSharedFlow<String>(replay = 1)
    fun debounceAPICaller(){
        viewModelScope.launch {
            callAPIChannel.debounce(200).collectLatest {
                viewInteractor?.refreshResult(page = it)
            }
        }
    }
    fun loadPage(page: String) {
        viewModelScope.launch {
            callAPIChannel.emit(page)
        }
    }
}
interface   ShareViewModelInterface{
    fun refreshResult(page: String){}
}