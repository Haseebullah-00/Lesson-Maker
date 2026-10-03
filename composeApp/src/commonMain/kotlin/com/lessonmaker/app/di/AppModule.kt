package com.lessonmaker.app.di


import com.russhwolf.settings.Settings
import com.lessonmaker.app.mainScreen.DashboardViewModel
import com.lessonmaker.app.network.ApiService
import com.lessonmaker.app.network.provideHttpClient
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.includes
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin

expect fun nativeConfig(): KoinConfiguration

val koinConfig = koinConfiguration {
    includes(nativeConfig())
    modules(appModule)
    modules(commonNetworkModule)
    modules(authModule)
    modules(authSellerModule)
}

val appModule = module {
//    viewModelOf(::LogInViewModel)
    viewModelOf(::DashboardViewModel)
//    viewModelOf(::FAQListViewModel)
//    viewModelOf(::CMSViewModel)
//    viewModelOf(::OTPVerifyViewModelSingUp)
  //  viewModelOf(::RegisterViewModel)

}
val authModule = module {
}
val authSellerModule = module {

}

val commonNetworkModule = module {
    singleOf(::provideHttpClient)
    singleOf(::ApiService)
    singleOf(::CommonLoading)
    singleOf(::Settings)
    singleOf(::SharedPreferenceManager)
}

fun KoinApplication.Companion.isInitialized(): Boolean {
    return try {
        getKoin()
        true
    } catch (e: Exception) {
        false
    }
}