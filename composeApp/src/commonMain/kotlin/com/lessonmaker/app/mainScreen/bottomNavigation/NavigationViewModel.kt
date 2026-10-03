package com.lessonmaker.app.mainScreen.bottomNavigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class NavigationViewModel :ViewModel(){
    var selectedIndex by mutableStateOf(0)
}