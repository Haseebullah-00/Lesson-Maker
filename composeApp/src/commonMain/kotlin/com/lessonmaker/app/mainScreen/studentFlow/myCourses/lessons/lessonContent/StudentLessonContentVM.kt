
package com.lessonmaker.app.mainScreen.studentFlow.myCourses.lessons.lessonContent

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
import com.lessonmaker.app.models.student.lesson.lessonContent.LessonContentData
import com.lessonmaker.app.models.student.lesson.lessonContent.TableOfContents
import com.lessonmaker.app.network.ApiService
import com.lessonmaker.app.network.getOnlySuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentLessonContentVM(
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val apiService: ApiService,
    val commonLoading: CommonLoading
) : SharedBaseViewModel<StudentLessonContentVM.ScreenInterface>() {

    var isLoading by mutableStateOf(false)
        private set

    var showEmptyScreen by mutableStateOf(false)

    private val _tableOfContents =
        MutableStateFlow<List<TableOfContents>>(emptyList())

    val tableOfContents: StateFlow<List<TableOfContents>> =
        _tableOfContents.asStateFlow()

    private val _lessonContent =
        MutableStateFlow<LessonContentData?>(null)

    val lessonContent: StateFlow<LessonContentData?> =
        _lessonContent.asStateFlow()

    fun getLessonContent(lessonId: Int) {

        if (isLoading) return

        viewModelScope.launch {

            isLoading = true
            showEmptyScreen = false
            commonLoading.apiCall = UiState.Loading

            try {

                val response = apiService
                    .getStudentMyCoursesLessonsContentApi(lessonId = lessonId)
                    .getOnlySuccess()

                commonLoading.apiCall = UiState.Success(response!!)

                if (response.status.checkForSuccess()) {

                    val data = response.data

                    _lessonContent.value = data

                    _tableOfContents.value =
                        data?.tableOfContents ?: emptyList()

                    showEmptyScreen = data == null

                } else {

                    _lessonContent.value = null
                    _tableOfContents.value = emptyList()
                    showEmptyScreen = true
                }

            } catch (e: Exception) {

                _lessonContent.value = null
                _tableOfContents.value = emptyList()
                showEmptyScreen = true

            } finally {

                isLoading = false
            }
        }
    }

    interface ScreenInterface : ShareViewModelInterface
}
