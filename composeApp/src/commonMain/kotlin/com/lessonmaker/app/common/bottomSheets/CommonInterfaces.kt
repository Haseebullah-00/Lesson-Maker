package com.lessonmaker.app.common.bottomSheets


interface ResetFlowCommonInterface{
    fun moveBackScreen()
    fun closeSheet()
    fun moveNextScreen()
}
interface BottomSheetCommonInterface{
    fun moveBackScreen(){}
    fun closeSheet(){}
    fun moveNextScreen(){}
    fun bookingCancel(){}
    fun bookingReschedule(){}
    fun bookingSupport(){}
}
interface SideBarCommonInterface{
    fun closeSheet(){}
    fun moveNextScreen(route: String){}
}


