package com.lessonmaker.app.mainScreen.teacherFlow.sideBar.allowedAccess

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.lessonmaker.app.base.ShareViewModelInterface
import com.lessonmaker.app.base.SharedBaseViewModel
import com.lessonmaker.app.common.dialogue.UiState
import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.extensionFunctions.checkForSuccess
import com.lessonmaker.app.models.course.AllowCourseAccessData
import com.lessonmaker.app.models.course.AllowedCourseAccessData
import com.lessonmaker.app.models.teacher.myCourses.TeacherCourseData
import com.lessonmaker.app.network.ApiService
import com.lessonmaker.app.network.getOnlySuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch



class AllowedAccessVM(
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val apiService: ApiService,
    val commonLoading: CommonLoading
) : SharedBaseViewModel<AllowedAccessVM.ScreenInterface>() {

    var isLoading by mutableStateOf(false)
        private set

    var showEmptyScreen by mutableStateOf(false)

    var selectedStudent by mutableStateOf<AllowCourseAccessData?>(null)
    var selectedCourse by mutableStateOf<TeacherCourseData?>(null)


    private val _students = MutableStateFlow<List<AllowedCourseAccessData>>(emptyList())

    val students: StateFlow<List<AllowedCourseAccessData>> = _students.asStateFlow()

    fun getAllStudents(teacherId:Int,courseId: Int?=null) {
        if (isLoading) return

        viewModelScope.launch {
            isLoading = true
            showEmptyScreen = false
            commonLoading.apiCall = UiState.Loading
            try {
                val response = apiService
                    .getAllowedStudentApi(teacherId = teacherId, courseId = courseId)
                    .getOnlySuccess()

                commonLoading.apiCall = UiState.Success(response!!)

                if (response.status.checkForSuccess()) {
                    val courses = response.data?:emptyList()

                    _students.value = courses
                    showEmptyScreen = courses.isEmpty()
                } else {
                    _students.value = emptyList()
                    showEmptyScreen = true
                }
            } catch (e: Exception) {
                _students.value = emptyList()
                showEmptyScreen = true
            } finally {
                isLoading = false
            }
        }
    }

    fun revokeAccess(
        accessId: Int,
        callBack: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                commonLoading.apiCall = UiState.Loading

                val response = apiService.revokeAccessApi(
                    accessId = accessId
                ).getOnlySuccess()

                commonLoading.apiCall = UiState.Success(response!!)

                if (response.status.checkForSuccess()) {
                    commonLoading.alertMessage=response.message?:""
                    callBack()
                } else {
                    commonLoading.alertMessage = response.message ?: ""
                }
            } catch (e: Exception) {
                println("Add Address Catch Error ${e.message}")
                e.printStackTrace()
                commonLoading.alertMessage = e.message ?: "Network error occurred"
            }
        }
    }

    interface ScreenInterface : ShareViewModelInterface
}