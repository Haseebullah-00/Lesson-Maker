package com.lessonmaker.app

import com.lessonmaker.app.base.BaseViewModel
import com.lessonmaker.app.base.ViewInteractor


class MainViewModel : BaseViewModel<MainViewModel.ScreenInterface>() {

    fun checkForIntent(){
        viewInteractor?.checkForIntent()
    }

    fun askNotificationPermission(){
        viewInteractor?.askNotificationPermission()
    }
    interface ScreenInterface : ViewInteractor {
        fun askNotificationPermission()
        fun checkForIntent()
    }

}