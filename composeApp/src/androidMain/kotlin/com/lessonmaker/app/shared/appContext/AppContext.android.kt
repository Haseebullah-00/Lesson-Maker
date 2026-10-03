package com.lessonmaker.app.shared.appContext


import android.app.Application
import android.content.Context
import java.lang.Exception
import kotlin.isInitialized

actual object AppContext {
    private lateinit var application: Application

    fun setUp(context: Context) {
        application = context as Application
    }

    fun get(): Context {
        if (::application.isInitialized.not()) throw Exception("Application context isn't initialized")
        return application.applicationContext
    }
}