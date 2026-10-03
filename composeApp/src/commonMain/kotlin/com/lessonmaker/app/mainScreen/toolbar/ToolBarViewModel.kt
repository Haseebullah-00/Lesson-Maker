package com.lessonmaker.app.mainScreen.toolbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.lessonmaker.app.theme.White

class ToolBarViewModel : ViewModel() {
    var showToolbar by mutableStateOf(true)
    var showProfileImage by mutableStateOf(true)
    var showNotification by mutableStateOf(true)
    var showBackText by mutableStateOf(true)
    var showVehicleData by mutableStateOf(true)
    var showBackImage by mutableStateOf(true)

    var showBackImageWhite by mutableStateOf(true)

    var backHeading by mutableStateOf("")
    var showMenu by mutableStateOf(true)
    var showAppLogo by mutableStateOf(true)
    var showShare by mutableStateOf(true)
    var showSearch by mutableStateOf(true)
    var showThreeDot by mutableStateOf(true)
    var showLocation by mutableStateOf(true)
    var showClearAll by mutableStateOf(false)
    var backgroundColor by mutableStateOf(Color.Transparent)
    var backgroundVehicleDataColor by mutableStateOf(White)

    var currentLocation by mutableStateOf("")
    var isDefaultAddress by mutableStateOf(false)


    fun updateToolBar(
        showToolbar: Boolean = true,
        showProfileImage: Boolean = false,
        showNotification: Boolean = false,
        showBackText: Boolean = false,
        showVehicleData: Boolean = false,
        showBackImage: Boolean = false,
        showBackImageWhite: Boolean = false,

        backHeading: String = "",
        showMenu: Boolean = false,
        showAppLogo: Boolean = false,
        showShare: Boolean = false,
        showSearch: Boolean = false,
        showThreeDot: Boolean = false,
        showLocation: Boolean = false,
        showClearAll: Boolean = false,
        backgroundColor: Color = Color.Transparent,
        backgroundVehicleDataColor: Color = White,
    ) {
        this.showToolbar = showToolbar
        this.showProfileImage = showProfileImage
        this.showNotification = showNotification
        this.showBackText = showBackText
        this.showBackImage = showBackImage
        this.showBackImageWhite = showBackImageWhite

        this.backHeading = backHeading
        this.showMenu = showMenu
        this.showAppLogo = showAppLogo
        this.showShare = showShare
        this.showSearch = showSearch
        this.showThreeDot = showThreeDot
        this.showLocation = showLocation
        this.showClearAll= showClearAll
        this.backgroundColor= backgroundColor
        this.backgroundVehicleDataColor= backgroundVehicleDataColor
        this.showVehicleData= showVehicleData

    }

    var notificationCount by mutableStateOf(0)
    var backListener: (() -> Unit)? = null
    var clearAllClicked: (() -> Unit)? = null


}