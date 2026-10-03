package com.lessonmaker.app.base

import android.app.Application
import android.provider.Settings
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.di.androidModules
import com.lessonmaker.app.di.appModule
import com.lessonmaker.app.di.authModule
import com.lessonmaker.app.di.authSellerModule
import com.lessonmaker.app.di.commonNetworkModule
import com.lessonmaker.app.shared.appContext.AppContext
import com.lessonmaker.app.utility.AppContextProvider

import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {

    private val sharedPreferenceManager by inject<SharedPreferenceManager>()

    override fun onCreate() {
        super.onCreate()

        // manually keeping context for shared KMP conf
        // see nativeConfig()
        AppContextProvider.init(this)
        instance = this

        AppContext.setUp(applicationContext)

        startKoin {
            androidContext(this@MainApplication)
            modules(
                androidModules,
                appModule,
                authModule,
                commonNetworkModule,
                authSellerModule
            )
        }
        try {
            sharedPreferenceManager.deviceCartID = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    companion object {
        var instance : Application? = null
    }
}