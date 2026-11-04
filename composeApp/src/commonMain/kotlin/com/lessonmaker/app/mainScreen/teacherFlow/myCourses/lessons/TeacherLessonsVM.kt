package com.lessonmaker.app.mainScreen.teacherFlow.myCourses.lessons

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
import com.lessonmaker.app.models.teacher.TeacherMyCourseLessonData
import com.lessonmaker.app.network.ApiService
import com.lessonmaker.app.network.getOnlySuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeacherLessonsVM(
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val apiService: ApiService,
    val commonLoading: CommonLoading
) : SharedBaseViewModel<TeacherLessonsVM.ScreenInterface>() {

    var isLoading by mutableStateOf(false)
        private set

    var showEmptyScreen by mutableStateOf(false)

    private val _lessons = MutableStateFlow<List<TeacherMyCourseLessonData>>(emptyList())

    val lessons: StateFlow<List<TeacherMyCourseLessonData>> = _lessons.asStateFlow()

    fun getAllLessons(teacherId:Int,courseId: Int) {
        if (isLoading) return

        viewModelScope.launch {
            isLoading = true
            showEmptyScreen = false
            commonLoading.apiCall = UiState.Loading
            try {
                val response = apiService
                    .getTeacherMyCoursesLessons(teacherId = teacherId,
                        courseId = courseId)
                    .getOnlySuccess()

                commonLoading.apiCall = UiState.Success(response!!)

                if (response.status.checkForSuccess()) {
                    val lessons = response.data?:emptyList()

                    _lessons.value = lessons
                    showEmptyScreen = lessons.isEmpty()
                } else {
                    _lessons.value = emptyList()
                    showEmptyScreen = true
                }
            } catch (e: Exception) {
                _lessons.value = emptyList()
                showEmptyScreen = true
            } finally {
                isLoading = false
            }
        }
    }

    interface ScreenInterface : ShareViewModelInterface
}