package com.lessonmaker.app.mainScreen.teacherFlow.myCourses.lessons.addLesson



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.lessonmaker.app.base.ShareViewModelInterface
import com.lessonmaker.app.base.SharedBaseViewModel
import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.mainScreen.teacherFlow.myCourses.lessons.TeacherLessonsVM
import com.lessonmaker.app.network.ApiService

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

    interface ScreenInterface : ShareViewModelInterface
}