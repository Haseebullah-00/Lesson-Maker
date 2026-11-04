package com.lessonmaker.app.mainScreen.teacherFlow.myCourses

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

import com.lessonmaker.app.models.teacher.myCourses.TeacherCourseData
import com.lessonmaker.app.network.ApiService
import com.lessonmaker.app.network.getOnlySuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.emptyList


class TeacherMyCoursesVM(
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val apiService: ApiService,
    val commonLoading: CommonLoading
) : SharedBaseViewModel<TeacherMyCoursesVM.ScreenInterface>() {

    var isLoading by mutableStateOf(false)
        private set

    var showEmptyScreen by mutableStateOf(false)

    private val _courses = MutableStateFlow<List<TeacherCourseData>>(emptyList())

    val courses: StateFlow<List<TeacherCourseData>> = _courses.asStateFlow()

    fun getAllCourses(teacherId:Int) {
        if (isLoading) return

        viewModelScope.launch {
            isLoading = true
            showEmptyScreen = false
            commonLoading.apiCall = UiState.Loading
            try {
                val response = apiService
                    .getTeacherMyCourses(teacherId = teacherId)
                    .getOnlySuccess()

                commonLoading.apiCall = UiState.Success(response!!)

                if (response.status.checkForSuccess()) {
                    val courses = response.data?:emptyList()

                    _courses.value = courses
                    showEmptyScreen = courses.isEmpty()
                } else {
                    _courses.value = emptyList()
                    showEmptyScreen = true
                }
            } catch (e: Exception) {
                _courses.value = emptyList()
                showEmptyScreen = true
            } finally {
                isLoading = false
            }
        }
    }

    interface ScreenInterface : ShareViewModelInterface
}