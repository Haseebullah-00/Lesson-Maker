package com.lessonmaker.app.authFLow.dataCell.dataCellUploadScreen



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.lessonmaker.app.base.ShareViewModelInterface
import com.lessonmaker.app.base.SharedBaseViewModel
import com.lessonmaker.app.common.dataCellSheetSpecs.UploadBundle
import com.lessonmaker.app.common.dialogue.UiState
// copy the ShareViewModelInterface import from DataCellLoginVm.kt
import com.lessonmaker.app.di.CommonLoading
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.extensionFunctions.checkForSuccess
import com.lessonmaker.app.extensionFunctions.handleNull
import com.lessonmaker.app.network.ApiService
import com.lessonmaker.app.network.getOnlySuccess
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

    fun uploadRecords(bundle: UploadBundle, callBack: () -> Unit) {

        if (isUploading) return

        viewModelScope.launch {

            commonLoading.apiCall = UiState.Loading
            isUploading = true

            try {
                println("STEP 1: Calling upload API")

                val result = apiService.uploadRecords(data = bundle)

                println("STEP 2: API function returned")
                println("RESULT = $result")

                val response = result.getOnlySuccess()

                println("STEP 3: Response = $response")

                if (response == null) {
                    throw Exception("Upload response is null")
                }

                commonLoading.apiCall = UiState.Success(response)

                println("STEP 4: Checking response status")

                if (response.status.checkForSuccess()) {

                    commonLoading.alertMessage = response.message ?: "Upload successful"
                    callBack()

                } else {
                    commonLoading.alertMessage = response.message ?: "Upload failed"
                }

            } catch (e: Exception) {

                isError = true
                statusMessage = e.message ?: "Upload failed"

                println("UPLOAD ERROR: ${e.message}")
                e.printStackTrace()

            } finally {
                isUploading = false
            }
        }
    }

//    fun uploadRecords(bundle: UploadBundle,callBack: () -> Unit) {
//        if (isUploading) return
//        viewModelScope.launch {
//            commonLoading.apiCall = UiState.Loading
//            isUploading = true
//            statusMessage = null
//            isError = false
//            try {
//                val response =
//                    apiService.uploadRecords(
//                        data = bundle
//                    ).getOnlySuccess()
//
//                commonLoading.apiCall = UiState.Success(response!!)
//                if (response?.status.checkForSuccess()) {
//                    commonLoading.alertMessage=response.message?:""
//                    callBack()
//
//                }
//                else{
//                    commonLoading.alertMessage=response.message?:""
//                }
//            } catch (e: Exception) {
//                isError = true
//                statusMessage = "${e::class.simpleName}: ${e.message}"
//                println("UPLOAD ERROR: ${e.stackTraceToString()}")
//            } finally {
//                isUploading = false
//            }
//        }
//    }

    fun clearStatus() {
        statusMessage = null
        isError = false
    }

    interface ScreenInterface : ShareViewModelInterface
}