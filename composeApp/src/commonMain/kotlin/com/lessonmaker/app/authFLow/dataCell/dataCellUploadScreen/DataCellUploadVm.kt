package com.lessonmaker.app.authFLow.dataCell.dataCellUploadScreen



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.lessonmaker.app.base.ShareViewModelInterface
import com.lessonmaker.app.base.SharedBaseViewModel
import com.lessonmaker.app.common.dataCellSheetSpecs.UploadBundle
// copy the ShareViewModelInterface import from DataCellLoginVm.kt
import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.network.ApiService
import kotlinx.coroutines.launch

class DataCellUploadVm(
    val sharedPreferenceManager: SharedPreferenceManager,
    private val apiService: ApiService,
    val commonLoading: CommonLoading
) : SharedBaseViewModel<DataCellUploadVm.ScreenInterface>() {

    var isUploading by mutableStateOf(false)
        private set
    var statusMessage by mutableStateOf<String?>(null)
        private set
    var isError by mutableStateOf(false)
        private set

    /** Called by the screen only after all three sheets passed the format check. */
    fun uploadRecords(bundle: UploadBundle) {
        if (isUploading) return
        viewModelScope.launch {
            isUploading = true
            statusMessage = null
            isError = false
            try {
                // TODO: replace with your real call, e.g.
                // when (val result = apiService.uploadRecords(bundle)) {
                //     is ApiResult.Success -> statusMessage = "Records committed successfully"
                //     is ApiResult.Error -> { isError = true; statusMessage = result.message }
                // }
                statusMessage = "Ready: ${bundle.courses.size} courses, " +
                        "${bundle.teachers.size} teachers, ${bundle.students.size} students"
            } catch (e: Exception) {
                isError = true
                statusMessage = e.message ?: "Upload failed"
            } finally {
                isUploading = false
            }
        }
    }

    fun clearStatus() {
        statusMessage = null
        isError = false
    }

    interface ScreenInterface : ShareViewModelInterface
}