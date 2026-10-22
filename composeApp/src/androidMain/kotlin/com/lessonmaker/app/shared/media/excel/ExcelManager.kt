package com.lessonmaker.app.shared.media.excel


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

private val EXCEL_MIME_TYPES = arrayOf(
    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xlsx
    "application/vnd.ms-excel",                                          // .xls
    "text/csv",                                                          // .csv
    "text/comma-separated-values",
    "application/csv"
)

@Composable
actual fun rememberGalleryManagerForExcel(onResult: (SharedExcel?) -> Unit): ExcelGalleryManager {
    val context = LocalContext.current
    var onLaunch by remember { mutableStateOf<() -> Unit>({}) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        onResult(uri?.let { SharedExcel(it, context) })
    }

    onLaunch = { launcher.launch(EXCEL_MIME_TYPES) }

    return remember {
        ExcelGalleryManager(
            onLaunch = { onLaunch() }
        )
    }
}

actual class ExcelGalleryManager actual constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launch() = onLaunch()
}
