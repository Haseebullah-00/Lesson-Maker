package com.lessonmaker.app.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

abstract class BaseViewModel<VI: ViewInteractor>: ViewModel() {


    var viewInteractor: VI? = null
        set

    var isDebouseInitated=false

    open fun setUp(){
        viewInteractor?.setUpNavigation()
        viewInteractor?.setupViews()
        viewInteractor?.setUpListeners()
        viewInteractor?.handleAPIResponses()
        if (isDebouseInitated.not()){
            isDebouseInitated=true
            debounceAPICaller()
        }
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

    override fun onCleared() {
        super.onCleared()
    }
}