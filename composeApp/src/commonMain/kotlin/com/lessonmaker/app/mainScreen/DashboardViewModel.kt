package com.lessonmaker.app.mainScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import com.lessonmaker.app.mainScreen.bottomNavigation.NavigationViewModel
import com.lessonmaker.app.mainScreen.toolbar.ToolBarViewModel
import com.lessonmaker.app.base.ShareViewModelInterface
import com.lessonmaker.app.base.SharedBaseViewModel
import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.network.ApiService


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel(
    val sharedPreferenceManager: SharedPreferenceManager,
    private val apiService: ApiService,
    /*private val notificationRepository: NotificationRepository,*/
    val commonLoading: CommonLoading
) : SharedBaseViewModel<DashboardViewModel.ScreenInterface>() {
    var toolBarViewModel = ToolBarViewModel()
    var navigationViewModel = NavigationViewModel()
    var showBottomNavigation by mutableStateOf(true)
    var showAddressForm by mutableStateOf(false)
    fun updateBottomNavigation(showBottomNavigation: Boolean = true) {
        this.showBottomNavigation = showBottomNavigation
    }


    var showMainBackground by   mutableStateOf("main")


    interface ScreenInterface : ShareViewModelInterface {
    }

    private val _currentVideoIndex = MutableStateFlow(0)
    val currentVideoIndex = _currentVideoIndex.asStateFlow()
    fun setCurrentVideoIndex(index: Int) {
        _currentVideoIndex.value = index
    }

 /*   private val _profileResponse = MutableStateFlow<ProfileAPIResponse?>(null)
    val profileResponse: StateFlow<ProfileAPIResponse?> get() = _profileResponse
    fun refreshProfile() {
        viewModelScope.launch {
            try {
                val response = apiService.getProfileResponse().getOnlySuccess()
                _profileResponse.value = response
            } catch (e: Exception) {
                _profileResponse.value = null
            }
        }
    }*/


    /*private val _allMessagesResponse = MutableStateFlow<AddAddressResponse?>(null)
    val allMessagesResponse: StateFlow<AddAddressResponse?> get() = _allMessagesResponse
    fun fetchAllMessages(type: String? = null) {
        viewModelScope.launch {
            try {
                val response = apiService.getAddress().getOnlySuccess()
                isRefreshing = false
                _allMessagesResponse.value = response
            } catch (e: Exception) {
                _allMessagesResponse.value = null
            }
        }
    }*/


    /*var job: Job?=null
    fun listenForNotifications(key:String=""){
        job?.cancel()
        if (key.isEmpty()){
            navigationViewModel.notificationCount=0
            toolBarViewModel.notificationCount=0
        }
        job = viewModelScope.launch {
            notificationRepository.listenForAllNotificationsPendingCount().collectLatest {
                navigationViewModel.notificationCount=it
                toolBarViewModel.notificationCount=it
            }
        }
    }*/

    var currentStoreName by mutableStateOf("")
    var currentStoreLocation by mutableStateOf("")
    var currentStoreLogo by mutableStateOf("")

    var categoryId by mutableStateOf("")
    var categoryName by mutableStateOf("")
    var isEditAddress by mutableStateOf(false)
    var isDefaultAddress by mutableStateOf(false)

    //var addressData by mutableStateOf<AddAddressResponse.OData?>(null)

    /*fun fillUserDetails(singleUserModel: SingleUserModel) {
       // toolBarViewModel.fillUserDetails(singleUserModel = singleUserModel)
    }*/

}