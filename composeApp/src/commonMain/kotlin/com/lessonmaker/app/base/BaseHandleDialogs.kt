package com.lessonmaker.app.base

import androidx.compose.runtime.Composable
import com.lessonmaker.app.common.dialogue.APIDialogInterface
import com.lessonmaker.app.common.dialogue.APIExceptionHandleDialog
import com.lessonmaker.app.common.dialogue.AlertMessageDialog
import com.lessonmaker.app.common.dialogue.LoadingDialog
import com.lessonmaker.app.common.dialogue.UiState
import com.lessonmaker.app.di.CommonLoading


@Composable
fun HandleDialogs(commonLoading: CommonLoading, baseDialogInterface: BaseDialogInterface){
    if (commonLoading.alertMessage.isNotEmpty()) {
        AlertMessageDialog(
            message = commonLoading.alertMessage,
            positiveButtonText = "OK"
        ) {
            commonLoading.alertMessage = ""
            commonLoading.apiException=null
            commonLoading.apiCall= UiState.Failure("")
        }
    }
    if (commonLoading.apiException!=null) {
        APIExceptionHandleDialog(commonLoading = commonLoading, apiDialogInterface = object :
            APIDialogInterface {
            override fun showLogin() {
                commonLoading.apiException=null
                commonLoading.apiCall=UiState.Failure("")
                baseDialogInterface.moveToLogin()
            }

            override fun simpleDismiss() {
                commonLoading.apiException=null
                commonLoading.apiCall=UiState.Failure("")
            }
        })
    }
    when (commonLoading.apiCall) {
        is UiState.Loading -> {
            LoadingDialog()
        }
        else -> {}
    }
}

interface BaseDialogInterface{
    fun moveToLogin()
}