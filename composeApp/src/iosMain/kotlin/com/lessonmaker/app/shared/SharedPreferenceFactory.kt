package com.lessonmaker.app.shared

import com.lessonmaker.app.di.SharedPreferenceManager
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
//import com.russhwolf.settings.AppleSettings
import platform.Foundation.NSUserDefaults
class SharedPreferenceFactory {
    fun provideSharedPreferenceManager(): SharedPreferenceManager {
        val settings: Settings = NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
        return SharedPreferenceManager(settings)
    }
}