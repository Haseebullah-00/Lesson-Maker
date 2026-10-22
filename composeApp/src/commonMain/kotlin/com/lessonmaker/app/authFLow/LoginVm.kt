package com.picket.app.authFlow.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.lessonmaker.app.authFLow.AuthAccountType
import com.lessonmaker.app.base.ShareViewModelInterface
import com.lessonmaker.app.base.SharedBaseViewModel
import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.network.ApiService



class LoginVm(
    val sharedPreferenceManager: SharedPreferenceManager,
    private val apiService: ApiService,
    val commonLoading: CommonLoading
) : SharedBaseViewModel<LoginVm.ScreenInterface>() {

    var accountType by mutableStateOf(AuthAccountType.Teacher)



    var email by mutableStateOf("")
    var emailError by mutableStateOf<String?>(null)
    var otp by mutableStateOf("")


    var password by mutableStateOf("")

    var passwordError by mutableStateOf<String?>(null)




    // Add error state
    var phoneNumberError by mutableStateOf<String?>(null)
    var apiError by mutableStateOf<String?>(null)

    fun switchAccountType(type: AuthAccountType) {
        if (accountType == type) return
        accountType = type
        clearLoginForm()
    }

    private fun clearLoginForm() {

        phoneNumberError = null
        email = ""
        password = ""
        emailError = null
        passwordError = null
        apiError = null

    }





//
//    fun validateEmailAndPassword(
//        enter_email_plz: String,
//        enter_valid_email: String,
//        plz_enter_password: String,
//        password_not_valid: String
//    ): Boolean {
//        if (email.isBlank()) {
//            emailError = enter_email_plz
//            passwordError = null
//            return false
//        }
//
//        if (!email.isValidEmail()) {
//            emailError = enter_valid_email
//            passwordError = null
//            return false
//        }
//
//        emailError = null
//
//        if (password.isBlank()) {
//            passwordError = plz_enter_password
//            return false
//        }
//
//        if (!password.isPasswordCorrect()) {
//            passwordError = password_not_valid
//            return false
//        }
//
//        passwordError = null
//        return true
//    }






//    fun callLogInWithEmailAPI(callBack: () -> Unit) {
//        apiError = null
//
//        viewModelScope.launch {
//            try {
//                commonLoading.apiCall = UiState.Loading
//
//                val response = apiService.loginWithEmailApi(
//                    email = email,
//                    password = password
//
//                ).getOnlySuccess() ?: return@launch
//
//                commonLoading.apiCall = UiState.Success(response)
//
//                if (response.status.checkForSuccess()) {
//                    if (response.status == "1") {
//
//                        sharedPreferenceManager.accessToken = response.oData?.token ?: ""
//                        response.oData?.user?.let { user ->
//                            sharedPreferenceManager.saveCurrentUser(user)
//                        }
//                        sharedPreferenceManager.isLoggedIn = true
//                        callBack()
//                    } else if (response.status == "0") {
//                        apiError = response.message ?: ""
//                    }
//
//                } else {
//                    apiError = response.message ?: ""
//                }
//            } catch (e: Exception) {
//                println("Login Catch Error ${e.message}")
//                e.printStackTrace()
//            }
//        }
//    }



    interface ScreenInterface : ShareViewModelInterface {

    }
}