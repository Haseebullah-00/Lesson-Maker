package com.picket.app.authFlow.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.lessonmaker.app.authFLow.AuthAccountType
import com.lessonmaker.app.base.ShareViewModelInterface
import com.lessonmaker.app.base.SharedBaseViewModel
import com.lessonmaker.app.common.dialogue.UiState
import com.lessonmaker.app.common.isValidEmail
import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.extensionFunctions.checkForSuccess
import com.lessonmaker.app.models.auth.StudentModel
import com.lessonmaker.app.models.auth.TeacherModel
import com.lessonmaker.app.network.ApiService
import com.lessonmaker.app.network.getOnlySuccess
import kotlinx.coroutines.launch


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

    fun isFormValid(): Boolean {
        var valid = true

        // email: not blank + regex (works for teacher and student accounts)
        if (email.isBlank()) {
            emailError = "Please enter email"
            valid = false
        } else if (!email.isValidEmail()) {
            emailError = "Please enter valid email"
            valid = false
        } else {
            emailError = null
        }


        if (password.isBlank()) {
            passwordError = "Please enter password"
            valid = false
        } else {
            passwordError = null
        }
        return valid
    }





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

    fun callLogInPI(callBack: () -> Unit) {
        apiError = null

        viewModelScope.launch {
            try {
                commonLoading.apiCall = UiState.Loading

                val response = apiService.login(
                    email = email,
                    password = password,
                    role =  if(accountType== AuthAccountType.Teacher) "Teacher" else "Student"

                ).getOnlySuccess()

                commonLoading.apiCall = UiState.Success(response!!)

                if (response.status.checkForSuccess()) {
                    sharedPreferenceManager.teacherUser = null
                    sharedPreferenceManager.studentUser = null
                    when (response.type?.trim()?.lowercase()) {

                        "teacher" -> {
                            val teacher = TeacherModel(
                                id = response.data?.id ?: 0,
                                name = response.data?.name ?: "",
                                email = response.data?.email ?: ""
                            )

                            sharedPreferenceManager.saveTeacher(teacher)

                            sharedPreferenceManager.isTeacherLoggedIn = true
                            sharedPreferenceManager.isStudentLoggedIn = false
                            sharedPreferenceManager.isLoggedIn = true

                            callBack()
                        }

                        "student" -> {
                            val student = StudentModel(
                                id = response.data?.id ?: 0,
                                name = response.data?.name ?: "",
                                email = response.data?.email ?: "",
                                aridNo = response.data?.aridNo ?: ""
                            )

                            sharedPreferenceManager.saveStudent(student)

                            sharedPreferenceManager.isStudentLoggedIn = true
                            sharedPreferenceManager.isTeacherLoggedIn = false
                            sharedPreferenceManager.isLoggedIn = true

                            callBack()
                        }

                        else -> {
                            commonLoading.alertMessage = "Invalid account type: ${response.type}"
                        }
                    }


                } else {
                   commonLoading.alertMessage= response.message ?: ""
                }
            } catch (e: Exception) {
                println("Login Catch Error ${e.message}")
                e.printStackTrace()
            }
        }
    }



    interface ScreenInterface : ShareViewModelInterface {

    }
}