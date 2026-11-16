package com.lessonmaker.app.mainScreen.teacherFlow.myCourses.lessons.addLesson



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.lessonmaker.app.authFLow.AuthAccountType
import com.lessonmaker.app.base.ShareViewModelInterface
import com.lessonmaker.app.base.SharedBaseViewModel
import com.lessonmaker.app.common.dialogue.UiState
import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.extensionFunctions.checkForSuccess
import com.lessonmaker.app.mainScreen.teacherFlow.myCourses.lessons.TeacherLessonsVM
import com.lessonmaker.app.models.auth.StudentModel
import com.lessonmaker.app.models.auth.TeacherModel
import com.lessonmaker.app.network.ApiService
import com.lessonmaker.app.network.getOnlySuccess
import kotlinx.coroutines.launch

class TeacherAddLessonVM(
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val apiService: ApiService,
    val commonLoading: CommonLoading
) : SharedBaseViewModel<TeacherAddLessonVM.ScreenInterface>() {

    var title by mutableStateOf("")
        private set
    var titleError by mutableStateOf<String?>(null)
        private set
    var contentError by mutableStateOf<String?>(null)
        private set

    fun setInitialTitle(value: String) { title = value }

    fun onTitleChange(value: String) {
        title = value
        if (titleError != null) titleError = null
    }

    fun onContentChange(plainText: String) {
        if (contentError != null && plainText.isNotBlank()) contentError = null
    }

    fun validate(plainText: String): Boolean {
        titleError = when {
            title.isBlank() -> "Please enter lesson title"
            title.trim().length < 3 -> "Title must be at least 3 characters"
            title.trim().length > 100 -> "Title must be at most 100 characters"
            else -> null
        }
        contentError = if (plainText.isBlank()) "Please write lesson content" else null
        return titleError == null && contentError == null
    }
    fun callAddLessonApi(teacherId: Int, courseId: Int, title:String,content:String,callBack: () -> Unit) {


        viewModelScope.launch {
            try {
                commonLoading.apiCall = UiState.Loading

                val response = apiService.addLessonApi(
                   teacherId = teacherId,
                    courseId = courseId,
                    title = title,
                    content = content
                ).getOnlySuccess()

                commonLoading.apiCall = UiState.Success(response!!)

                if (response.status.checkForSuccess()) {
                    callBack()
                } else {
                    commonLoading.alertMessage= response.message ?: ""
                }
            } catch (e: Exception) {
                println("Login Catch Error ${e.message}")
                e.printStackTrace()
            }
        }
    }


    fun callEditLessonApi(lessonId: Int, title:String,content:String,callBack: () -> Unit) {


        viewModelScope.launch {
            try {
                commonLoading.apiCall = UiState.Loading

                val response = apiService.editLessonApi(
                   lessonId = lessonId,
                    title = title,
                    content = content
                ).getOnlySuccess()

                commonLoading.apiCall = UiState.Success(response!!)

                if (response.status.checkForSuccess()) {
                    commonLoading.alertMessage= response.message ?: ""
                    callBack()
                } else {
                    commonLoading.alertMessage= response.message ?: ""
                }
            } catch (e: Exception) {
                println("Login Catch Error ${e.message}")
                e.printStackTrace()
            }
        }
    }




    interface ScreenInterface : ShareViewModelInterface
}