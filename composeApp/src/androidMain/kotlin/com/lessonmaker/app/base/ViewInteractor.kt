package com.lessonmaker.app.base

interface   ViewInteractor{
    fun setupViews(){}
    fun setUpNavigation(){}
    fun setUpListeners(){}
    fun handleAPIResponses(){}
    fun refreshResult(page: String){}
}