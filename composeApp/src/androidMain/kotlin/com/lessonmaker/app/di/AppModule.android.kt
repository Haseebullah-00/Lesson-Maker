package com.lessonmaker.app.di

import com.lessonmaker.app.base.MainApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module

actual fun nativeConfig() = koinConfiguration {
    androidLogger()
    androidContext(MainApplication.instance ?: error("No Android application context set"))
}

val androidModules = module {
}
