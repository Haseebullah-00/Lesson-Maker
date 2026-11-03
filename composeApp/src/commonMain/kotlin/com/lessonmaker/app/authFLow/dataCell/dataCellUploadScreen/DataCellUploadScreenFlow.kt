package com.lessonmaker.app.authFLow.dataCell.dataCellUploadScreen

// ---------- imports (unused WindowInsets / asPaddingValues / navigationBars removed) ----------
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lessonmaker.app.common.dataCellSheetSpecs.ParsedSheet
import com.lessonmaker.app.common.dataCellSheetSpecs.SheetKind
import com.lessonmaker.app.common.dataCellSheetSpecs.SheetReader
import com.lessonmaker.app.common.dataCellSheetSpecs.SheetValidator
import com.lessonmaker.app.common.dataCellSheetSpecs.UploadBundle
import com.lessonmaker.app.common.dialogue.MediaPickerInterface
import com.lessonmaker.app.common.dialogue.MediaSourcePicker
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.navigationControler.Screen
import com.lessonmaker.app.shared.media.excel.SharedExcel
import com.lessonmaker.app.shared.media.excel.readSpreadsheet
import com.lessonmaker.app.shared.media.excel.rememberGalleryManagerForExcel
import com.lessonmaker.app.theme.BackgorundColor
import com.lessonmaker.app.theme.REdF14336
import com.lessonmaker.app.theme.Regular
import com.lessonmaker.app.theme.TextSize11
import com.lessonmaker.app.theme.TextSize12
import com.lessonmaker.app.theme.TextSize13
import com.lessonmaker.app.theme.TextSize14
import com.lessonmaker.app.theme.TextSize18
import com.lessonmaker.app.theme.White
import com.lessonmaker.app.theme.approvedGreen
import com.lessonmaker.app.theme.bgColor
import com.lessonmaker.app.theme.bold
import com.lessonmaker.app.theme.boldExtraBold
import com.lessonmaker.app.theme.dashBorderClr
import com.lessonmaker.app.theme.fieldBorder
import com.lessonmaker.app.theme.fieldCursor
import com.lessonmaker.app.theme.fieldHint
import com.lessonmaker.app.theme.fieldText
import com.lessonmaker.app.theme.greyE3
import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
import com.lessonmaker.app.theme.semiBold
import com.lessonmaker.app.theme.statusApprovedText
import com.lessonmaker.app.theme.statusRejectedBg
import com.lessonmaker.app.theme.statusRejectedText
import com.lessonmaker.app.theme.successBoxBg
import com.lessonmaker.app.theme.successBoxBorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lessonmaker.composeapp.generated.resources.Res
import lessonmaker.composeapp.generated.resources.check_mark
import lessonmaker.composeapp.generated.resources.ic_logout
import lessonmaker.composeapp.generated.resources.lesson_ic
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

// All state of this screen in one place. mutableState* makes the UI redraw when a value changes.
private class UploadUi {
    var sessionTitle by mutableStateOf("")                          // text of the "Session Title" field
    val sheets = mutableStateMapOf<SheetKind, ParsedSheet>()        // the 3 files as read from Excel
    val errors = mutableStateMapOf<SheetKind, List<String>>()       // error lines per file, filled on submit
}

@Composable
fun DataCellUploadScreenFlow(
    newRouts: (String) -> Unit                                      // navigation callback (route string)
) {
    val vm = koinViewModel<DataCellUploadVm>()                      // the ViewModel that calls the API
    val prefs: SharedPreferenceManager = koinInject()               // saved login flags (used by Logout)
    val scope = rememberCoroutineScope()                            // scope for reading files off the UI thread
    val ui = remember { UploadUi() }                                // screen state, survives recomposition
    var pickerFor by remember { mutableStateOf<SheetKind?>(null) }  // which card was tapped (null = none)

    // Called after a file is picked. It ONLY reads the file; checking happens later in submit().
    fun onExcel(kind: SheetKind, excel: SharedExcel?) {
        if (excel == null) return                                   // user cancelled the picker
        scope.launch {
            val name = excel.getFileName().orEmpty()
            val parsed = withContext(Dispatchers.Default) {         // heavy work off the main thread
                val bytes = excel.toByteArray()
                if (bytes == null) null
                else runCatching { SheetReader.parse(name, readSpreadsheet(name, bytes)) }.getOrNull()
            }
            // if reading failed, store an empty sheet so checkFormat can show "File is empty or not supported"
            ui.sheets[kind] = parsed ?: ParsedSheet(name, emptyList(), emptyList())
            ui.errors.remove(kind)                                  // clear old errors of this card
            vm.clearStatus()                                        // clear old server message
        }
    }

    // one file picker per card; each sends its result to onExcel with its own kind
    val courseManager = rememberGalleryManagerForExcel { onExcel(SheetKind.Course, it) }
    val studentManager = rememberGalleryManagerForExcel { onExcel(SheetKind.Student, it) }
    val teacherManager = rememberGalleryManagerForExcel { onExcel(SheetKind.Teacher, it) }

    // Runs BOTH checks, and only if everything is fine sends the data to the server.
    fun submit(callBack: () -> Unit) {
        ui.errors.clear()                                           // start from a clean slate
        vm.clearStatus()

        // CHECK 1: every file has the required headers and at least one data row
        SheetKind.entries.forEach { kind ->
            val problems = SheetValidator.checkFormat(kind, ui.sheets.getValue(kind))
            if (problems.isNotEmpty()) ui.errors[kind] = problems
        }
        if (ui.errors.isNotEmpty()) return                          // stop: red boxes are shown on the cards

        // CHECK 2: ids, emails, passwords, teacher-teaches-course (only safe after CHECK 1 passed)
        val refErrors = SheetValidator.checkReferences(ui.sheets.toMap())
        if (refErrors.isNotEmpty()) {
            ui.errors.putAll(refErrors)                             // show the problems on the matching cards
            return
        }

        // all good: build the JSON bundle and upload
        val bundle = SheetReader.buildBundle(ui.sessionTitle.trim(), ui.sheets.toMap())
        vm.uploadRecords(bundle, callBack)
    }

    // button is enabled only when a title is typed and all 3 files are picked
    val canSubmit = ui.sessionTitle.isNotBlank() && SheetKind.entries.all { ui.sheets[it] != null }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgorundColor)
            .imePadding(),                                          // keep the keyboard from covering fields
    ) {
        // ---------- header (no toolbar) ----------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .statusBarsPadding()                                // keep below the phone status bar
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {                // title takes all space left of Logout
                Text(text = "Upload Records", color = bgColor, style = TextSize18().boldExtraBold())
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "Data Cell Management System",
                    color = loginTabUnselectedTxtClr,
                    style = TextSize12().Regular()
                )
            }
            Row(                                                    // the Logout button
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, fieldBorder, RoundedCornerShape(8.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        prefs.isLoggedIn = false                    // forget all login flags
                        prefs.isStudentLoggedIn = false
                        prefs.isTeacherLoggedIn = false
                        newRouts.invoke(Screen.LoginScreenFlow.route) // go to login screen
                    }
                    .padding(horizontal = 10.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_logout),
                    contentDescription = "Logout",
                    modifier = Modifier.size(16.dp),
                    colorFilter = ColorFilter.tint(REdF14336)
                )
                Spacer(Modifier.width(6.dp))
                Text(text = "Logout", color = REdF14336, style = TextSize12().semiBold())
            }
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(greyE3))   // thin divider line

        // ---------- body (scrollable) ----------
        Column(
            modifier = Modifier
                .weight(1f)                                         // takes the space between header and button
                .verticalScroll(rememberScrollState())              // (duplicate imePadding removed: parent has it)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column {                                                // Session Title field
                Text(text = "Session Title", color = bgColor, style = TextSize12().semiBold())
                Spacer(Modifier.height(6.dp))
                SessionField(value = ui.sessionTitle, hint = "Fall 2026") {
                    ui.sessionTitle = it
                    vm.clearStatus()
                }
            }

            SheetKind.entries.forEach { kind ->                     // one card each: Course, Student, Teacher
                UploadCard(
                    kind = kind,
                    sheet = ui.sheets[kind],
                    errors = ui.errors[kind],
                    onPick = { pickerFor = kind },                  // tapping opens the picker for this kind
                    onClear = {                                     // Clear chip removes file + its errors
                        ui.sheets.remove(kind)
                        ui.errors.remove(kind)
                        vm.clearStatus()
                    }
                )
            }

            // once all 3 files are loaded, show what will be sent
            if (SheetKind.entries.all { ui.sheets[it] != null }) {
                BundlePreview(remember(ui.sheets.toMap()) { SheetReader.buildBundle("", ui.sheets.toMap()) })
            }

            vm.statusMessage?.let {                                 // server / network message from the ViewModel
                Text(
                    text = it,
                    color = if (vm.isError) statusRejectedText else statusApprovedText,
                    style = TextSize13().semiBold()
                )
            }
            Spacer(Modifier.height(8.dp))
        }

        // ---------- bottom button ----------
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
        ) {
            Box(Modifier.fillMaxWidth().height(1.dp).background(greyE3))
            val enabled = canSubmit && !vm.isUploading              // also disabled while an upload is running
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        if (enabled) fieldCursor else fieldCursor.copy(alpha = 0.4f), // looks greyed out when disabled
                        RoundedCornerShape(10.dp)
                    )
                    .clickable(
                        enabled = enabled,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        // FIX: go through submit() so CHECK 1 and CHECK 2 always run before uploading.
                        // (Before, this called vm.uploadRecords directly and skipped every check.)
                        submit { newRouts.invoke(Screen.POPBackStack.route) }   // on success: go back
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text =  "Process & Commit Records",
                    color = White,
                    style = TextSize14().bold()
                )
            }
        }
    }

    // Excel source picker - shown while a card has been tapped
    if (pickerFor != null) {
        MediaSourcePicker(
            type = "excel",
            mediaPickerInterface = object : MediaPickerInterface {
                override fun pickExcel() {
                    val kind = pickerFor                            // remember which card, then close the picker
                    pickerFor = null
                    when (kind) {                                   // launch the picker that belongs to this card
                        SheetKind.Course -> courseManager.launch()
                        SheetKind.Student -> studentManager.launch()
                        SheetKind.Teacher -> teacherManager.launch()
                        null -> Unit
                    }
                }
                override fun pickPdf() {}                           // other sources are not used here
                override fun pickerImageCamera() {}
                override fun pickVideoGallery() {}
                override fun pickVideoCamera() {}
                override fun pickImageGallery() {}
                override fun dismissed() { pickerFor = null }       // user closed the picker
            }
        )
    }
}

// Shows the courses / teachers / students that are about to be sent.
@Composable
private fun BundlePreview(b: UploadBundle) {
    val shape = RoundedCornerShape(12.dp)
    Column(
        modifier = Modifier.fillMaxWidth().background(White, shape)
            .border(1.dp, fieldBorder, shape).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text("Courses (${b.courses.size})", color = bgColor, style = TextSize14().bold())
        b.courses.forEach {
            Text("${it.courseCode} · ${it.courseName}", color = fieldText, style = TextSize12().Regular())
        }
        Spacer(Modifier.height(8.dp))
        Text("Teachers (${b.teachers.size})", color = bgColor, style = TextSize14().bold())
        b.teachers.forEach { t ->
            Text("${t.teacherId} · ${t.teacherName}", color = fieldText, style = TextSize12().semiBold())
            Text("   • ${t.courseCode}", color = loginTabUnselectedTxtClr, style = TextSize11().Regular())
        }
        Spacer(Modifier.height(8.dp))
        Text("Students (${b.students.size})", color = bgColor, style = TextSize14().bold())
        b.students.forEach { s ->
            Text("${s.studentId} · ${s.studentName}", color = fieldText, style = TextSize12().semiBold())
            // CGPA is shown as "-" when blank so you can see it will be sent as null
            Text(
                "   • ${s.courseCode}  →  ${s.teacherId}   (CGPA: ${s.cgpa ?: "-"})",
                color = loginTabUnselectedTxtClr,
                style = TextSize11().Regular()
            )
        }
    }
}

// A custom single-line text field with a hint, border and cursor colour.
@Composable
private fun SessionField(value: String, hint: String, onChange: (String) -> Unit) {
    val shape = RoundedCornerShape(10.dp)
    BasicTextField(
        value = value,
        onValueChange = onChange,
        singleLine = true,
        textStyle = TextSize14().Regular().copy(color = fieldText),
        cursorBrush = SolidColor(fieldCursor),
        modifier = Modifier.fillMaxWidth(),
        decorationBox = { inner ->                                  // draws the box around the real text field
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(White, shape)
                    .border(1.dp, fieldBorder, shape)
                    .padding(horizontal = 14.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.isEmpty()) Text(text = hint, color = fieldHint, style = TextSize14().Regular())
                inner()                                             // the actual editable text
            }
        }
    )
}

// One white card per sheet kind. Shows empty / error / selected state.
@Composable
private fun UploadCard(
    kind: SheetKind,
    sheet: ParsedSheet?,
    errors: List<String>?,
    onPick: () -> Unit,
    onClear: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape)
            .border(1.dp, fieldBorder, shape)
            .padding(16.dp)
    ) {
        Text(text = kind.cardTitle, color = bgColor, style = TextSize14().bold())
        Spacer(Modifier.height(12.dp))
        when {
            sheet == null -> EmptyBox(kind, onPick)                          // no file yet
            !errors.isNullOrEmpty() -> ErrorBox(sheet, errors, onPick, onClear) // file has problems
            else -> SelectedBox(sheet, onClear)                              // file looks fine
        }
    }
}

// Dashed "tap to upload" box. Shows required columns, and optional ones in brackets.
@Composable
private fun EmptyBox(kind: SheetKind, onPick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(BackgorundColor)
            .dashedBorder(dashBorderClr)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onPick
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.lesson_ic),
            contentDescription = "",
            modifier = Modifier.size(28.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Text(text = "No ${kind.noun} file selected", color = bgColor, style = TextSize13().semiBold())
            Spacer(Modifier.height(2.dp))
            Text(
                text = "Tap to upload ${kind.sampleFile}",
                color = loginTabUnselectedTxtClr,
                style = TextSize11().Regular()
            )
            Text(
                text = "Columns: ${kind.columns.joinToString()}" +
                        if (kind.optional.isNotEmpty()) "  (optional: ${kind.optional.joinToString()})" else "",
                color = fieldHint,
                style = TextSize11().Regular()
            )
        }
    }
}

// Green box: file picked and no errors.
@Composable
private fun SelectedBox(sheet: ParsedSheet, onClear: () -> Unit) {
    val shape = RoundedCornerShape(10.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(successBoxBg, shape)
            .border(1.dp, successBoxBorder, shape)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(32.dp).background(approvedGreen, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.check_mark),
                contentDescription = "",
                modifier = Modifier.size(14.dp),
                colorFilter = ColorFilter.tint(White)
            )
        }
        Spacer(Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = sheet.fileName, color = statusApprovedText, style = TextSize13().bold())
            Text(
                text = "${sheet.rows.size} rows read",
                color = statusApprovedText,
                style = TextSize11().Regular()
            )
        }
        ClearChip(onClear)
    }
}

// Red box: lists the problems found by CHECK 1 or CHECK 2.
@Composable
private fun ErrorBox(sheet: ParsedSheet, errors: List<String>, onPick: () -> Unit, onClear: () -> Unit) {
    val shape = RoundedCornerShape(10.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(statusRejectedBg, shape)
            .border(1.dp, statusRejectedText.copy(alpha = 0.4f), shape)
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                // wording changed: errors can now also be wrong ids/emails, not only a wrong format
                text = "${sheet.fileName.ifBlank { "File" }} – needs fixing",
                color = statusRejectedText,
                style = TextSize13().bold(),
                modifier = Modifier.weight(1f)
            )
            ClearChip(onClear)
        }
        Spacer(Modifier.height(6.dp))
        errors.forEach {                                            // one bullet per error line
            Text(text = "• $it", color = statusRejectedText, style = TextSize11().Regular())
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = "Choose another file",
            color = fieldCursor,
            style = TextSize11().semiBold(),
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onPick
            )
        )
    }
}

// Small "Clear" button used inside the green and red boxes.
@Composable
private fun ClearChip(onClear: () -> Unit) {
    Box(
        modifier = Modifier
            .background(White, RoundedCornerShape(6.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClear
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) { Text(text = "Clear", color = REdF14336, style = TextSize11().semiBold()) }
}

// Draws a dashed rounded rectangle around a composable.
private fun Modifier.dashedBorder(color: Color, radius: Dp = 10.dp) = this.drawBehind {
    drawRoundRect(
        color = color,
        cornerRadius = CornerRadius(radius.toPx()),
        style = Stroke(
            width = 1.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f), 0f)   // 10px dash, 8px gap
        )
    )
}
//package com.lessonmaker.app.authFLow.dataCell.dataCellUploadScreen
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.asPaddingValues
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.imePadding
//import androidx.compose.foundation.layout.navigationBars
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateMapOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.drawBehind
//import androidx.compose.ui.geometry.CornerRadius
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.ColorFilter
//import androidx.compose.ui.graphics.PathEffect
//import androidx.compose.ui.graphics.SolidColor
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import com.lessonmaker.app.common.dataCellSheetSpecs.ParsedSheet
//import com.lessonmaker.app.common.dataCellSheetSpecs.SheetKind
//import com.lessonmaker.app.common.dataCellSheetSpecs.SheetReader
//import com.lessonmaker.app.common.dataCellSheetSpecs.SheetValidator
//import com.lessonmaker.app.common.dataCellSheetSpecs.UploadBundle
//import com.lessonmaker.app.common.dialogue.MediaPickerInterface
//import com.lessonmaker.app.common.dialogue.MediaSourcePicker
//import com.lessonmaker.app.di.SharedPreferenceManager
//import com.lessonmaker.app.navigationControler.Screen
//import com.lessonmaker.app.shared.media.excel.SharedExcel
//import com.lessonmaker.app.shared.media.excel.readSpreadsheet
//import com.lessonmaker.app.shared.media.excel.rememberGalleryManagerForExcel
//import com.lessonmaker.app.theme.BackgorundColor
//import com.lessonmaker.app.theme.REdF14336
//import com.lessonmaker.app.theme.Regular
//import com.lessonmaker.app.theme.TextSize11
//import com.lessonmaker.app.theme.TextSize12
//import com.lessonmaker.app.theme.TextSize13
//import com.lessonmaker.app.theme.TextSize14
//import com.lessonmaker.app.theme.TextSize18
//import com.lessonmaker.app.theme.White
//import com.lessonmaker.app.theme.approvedGreen
//import com.lessonmaker.app.theme.bgColor
//import com.lessonmaker.app.theme.bold
//import com.lessonmaker.app.theme.boldExtraBold
//import com.lessonmaker.app.theme.dashBorderClr
//
//import com.lessonmaker.app.theme.fieldBorder
//import com.lessonmaker.app.theme.fieldCursor
//import com.lessonmaker.app.theme.fieldHint
//import com.lessonmaker.app.theme.fieldText
//import com.lessonmaker.app.theme.greyE3
//import com.lessonmaker.app.theme.loginTabUnselectedTxtClr
//import com.lessonmaker.app.theme.semiBold
//import com.lessonmaker.app.theme.statusApprovedText
//import com.lessonmaker.app.theme.statusRejectedBg
//import com.lessonmaker.app.theme.statusRejectedText
//import com.lessonmaker.app.theme.successBoxBg
//import com.lessonmaker.app.theme.successBoxBorder
//
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import lessonmaker.composeapp.generated.resources.Res
//import lessonmaker.composeapp.generated.resources.check_mark
//import lessonmaker.composeapp.generated.resources.ic_logout
//import lessonmaker.composeapp.generated.resources.lesson_ic
//import org.jetbrains.compose.resources.painterResource
//import org.koin.compose.koinInject
//import org.koin.compose.viewmodel.koinViewModel
//
//private class UploadUi {
//    var sessionTitle by mutableStateOf("")
//    val sheets = mutableStateMapOf<SheetKind, ParsedSheet>()      // raw data as read
//    val errors = mutableStateMapOf<SheetKind, List<String>>()      // filled on submit
//    var message by mutableStateOf<String?>(null)
//}
//
//@Composable
//fun DataCellUploadScreenFlow(
//    newRouts: (String) -> Unit
//) {
//    val vm = koinViewModel<DataCellUploadVm>()
//    val prefs: SharedPreferenceManager = koinInject()
//    val scope = rememberCoroutineScope()
//    val ui = remember { UploadUi() }
//    var pickerFor by remember { mutableStateOf<SheetKind?>(null) }
//
//    // picking only READS the file; format is verified on submit
//    fun onExcel(kind: SheetKind, excel: SharedExcel?) {
//        if (excel == null) return
//        scope.launch {
//            val name = excel.getFileName().orEmpty()
//            val parsed = withContext(Dispatchers.Default) {
//                val bytes = excel.toByteArray()
//                if (bytes == null) null
//                else runCatching { SheetReader.parse(name, readSpreadsheet(name, bytes)) }.getOrNull()
//            }
//            ui.sheets[kind] = parsed ?: ParsedSheet(name, emptyList(), emptyList())
//            ui.errors.remove(kind)
//            ui.message = null
//        }
//    }
//
//    val courseManager = rememberGalleryManagerForExcel { onExcel(SheetKind.Course, it) }
//    val studentManager = rememberGalleryManagerForExcel { onExcel(SheetKind.Student, it) }
//    val teacherManager = rememberGalleryManagerForExcel { onExcel(SheetKind.Teacher, it) }
//
//    fun submit(callBack: () -> Unit) {
//        ui.errors.clear()
//        SheetKind.entries.forEach { kind ->
//            val problems = SheetValidator.checkFormat(kind, ui.sheets.getValue(kind))
//            if (problems.isNotEmpty()) ui.errors[kind] = problems
//        }
//        if (ui.errors.isNotEmpty()) {
//            ui.message = null
//            return
//        }
//        val bundle = SheetReader.buildBundle(ui.sessionTitle, ui.sheets)
//        ui.message = null
//        vm.uploadRecords(bundle, callBack = {callBack()})
//    }
//
//    val canSubmit = ui.sessionTitle.isNotBlank() && SheetKind.entries.all { ui.sheets[it] != null }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(BackgorundColor)
//            .imePadding(),
//    ) {
//        // ---------- header (no toolbar) ----------
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(White)
//                .statusBarsPadding()
//                .padding(horizontal = 16.dp, vertical = 14.dp),
//
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column(modifier = Modifier.weight(1f)) {
//                Text(text = "Upload Records", color = bgColor, style = TextSize18().boldExtraBold())
//                Spacer(Modifier.height(2.dp))
//                Text(
//                    text = "Data Cell Management System",
//                    color = loginTabUnselectedTxtClr,
//                    style = TextSize12().Regular()
//                )
//            }
//            Row(
//                modifier = Modifier
//                    .clip(RoundedCornerShape(8.dp))
//                    .border(1.dp, fieldBorder, RoundedCornerShape(8.dp))
//                    .clickable(
//                        interactionSource = remember { MutableInteractionSource() },
//                        indication = null
//                    ) {
//                        prefs.isLoggedIn = false
//                        prefs.isStudentLoggedIn = false
//                        prefs.isTeacherLoggedIn = false
//                        newRouts.invoke(Screen.LoginScreenFlow.route)
//                    }
//                    .padding(horizontal = 10.dp, vertical = 7.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    painter = painterResource(Res.drawable.ic_logout),
//                    contentDescription = "Logout",
//                    modifier = Modifier.size(16.dp),
//                    colorFilter = ColorFilter.tint(REdF14336)
//                )
//                Spacer(Modifier.width(6.dp))
//                Text(text = "Logout", color = REdF14336, style = TextSize12().semiBold())
//            }
//        }
//        Box(Modifier.fillMaxWidth().height(1.dp).background(greyE3))
//
//        // ---------- body ----------
//        Column(
//            modifier = Modifier
//                .weight(1f)
//                .imePadding()
//                .verticalScroll(rememberScrollState())
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(14.dp)
//        ) {
//            Column {
//                Text(text = "Session Title", color = bgColor, style = TextSize12().semiBold())
//                Spacer(Modifier.height(6.dp))
//                SessionField(value = ui.sessionTitle, hint = "Fall 2026") {
//                    ui.sessionTitle = it
//                    ui.message = null
//                }
//            }
//
//            SheetKind.entries.forEach { kind ->
//                UploadCard(
//                    kind = kind,
//                    sheet = ui.sheets[kind],
//                    errors = ui.errors[kind],
//                    onPick = { pickerFor = kind },
//                    onClear = {
//                        ui.sheets.remove(kind)
//                        ui.errors.remove(kind)
//                        ui.message = null
//                        vm.clearStatus()
//                    }
//                )
//            }
//            if (SheetKind.entries.all { ui.sheets[it] != null }) {
//                BundlePreview(remember(ui.sheets.toMap()) { SheetReader.buildBundle("", ui.sheets) })
//            }
//
//            vm.statusMessage?.let {
//                Text(
//                    text = it,
//                    color = if (vm.isError) statusRejectedText else statusApprovedText,
//                    style = TextSize13().semiBold()
//                )
//            }
//            Spacer(Modifier.height(8.dp))
//        }
//
//        // ---------- bottom button ----------
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(White)
//
//        ) {
//            Box(Modifier.fillMaxWidth().height(1.dp).background(greyE3))
//            Box(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth()
//                    .height(48.dp)
//                    .background(
//                         fieldCursor ,
//                        RoundedCornerShape(10.dp)
//                    )
//                    .clickable(
//                        enabled = canSubmit,
//                        interactionSource = remember { MutableInteractionSource() },
//                        indication = null
//                    ) {    val bundle = SheetReader.buildBundle(ui.sessionTitle, ui.sheets)
//                        println("SESSION: ${bundle.sessionTitle}")
//                        println("COURSES: ${bundle.courses.size}")
//                        println("TEACHERS: ${bundle.teachers.size}")
//                        println("STUDENTS: ${bundle.students.size}")
//
//                        vm.uploadRecords(bundle) {
//                            newRouts.invoke(Screen.POPBackStack.route)
//                        }
//                    },
//                contentAlignment = Alignment.Center
//            ) {
//                Text(text = "Process & Commit Records", color = White, style = TextSize14().bold())
//            }
//        }
//    }
//
//    // Excel picker – opened by tapping any upload card
//    if (pickerFor != null) {
//        MediaSourcePicker(
//            type = "excel",
//            mediaPickerInterface = object : MediaPickerInterface {
//                override fun pickExcel() {
//                    val kind = pickerFor
//                    pickerFor = null
//                    when (kind) {
//                        SheetKind.Course -> courseManager.launch()
//                        SheetKind.Student -> studentManager.launch()
//                        SheetKind.Teacher -> teacherManager.launch()
//                        null -> Unit
//                    }
//                }
//                override fun pickPdf() {}
//                override fun pickerImageCamera() {}
//                override fun pickVideoGallery() {}
//                override fun pickVideoCamera() {}
//                override fun pickImageGallery() {}
//                override fun dismissed() { pickerFor = null }
//            }
//        )
//    }
//}
//
//@Composable
//private fun BundlePreview(b: UploadBundle) {
//    val shape = RoundedCornerShape(12.dp)
//    Column(
//        modifier = Modifier.fillMaxWidth().background(White, shape)
//            .border(1.dp, fieldBorder, shape).padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(3.dp)
//    ) {
//        Text("Courses (${b.courses.size})", color = bgColor, style = TextSize14().bold())
//        b.courses.forEach { Text("${it.courseCode} · ${it.courseName}", color = fieldText, style = TextSize12().Regular()) }
//        Spacer(Modifier.height(8.dp))
//        Text("Teachers (${b.teachers.size})", color = bgColor, style = TextSize14().bold())
//        b.teachers.forEach { t ->
//            Text("${t.teacherId} · ${t.teacherName}", color = fieldText, style = TextSize12().semiBold())
//                Text("   • ${t.courseCode}", color = loginTabUnselectedTxtClr, style = TextSize11().Regular())
//
//        }
//        Spacer(Modifier.height(8.dp))
//        Text("Students (${b.students.size})", color = bgColor, style = TextSize14().bold())
//        b.students.forEach { s ->
//            Text("${s.studentId} · ${s.studentName}", color = fieldText, style = TextSize12().semiBold())
//                Text("   • ${s.courseCode}  →  ${s.teacherId}", color = loginTabUnselectedTxtClr, style = TextSize11().Regular())
//
//        }
//    }
//}
//@Composable
//private fun SessionField(value: String, hint: String, onChange: (String) -> Unit) {
//    val shape = RoundedCornerShape(10.dp)
//    BasicTextField(
//        value = value,
//        onValueChange = onChange,
//        singleLine = true,
//        textStyle = TextSize14().Regular().copy(color = fieldText),
//        cursorBrush = SolidColor(fieldCursor),
//        modifier = Modifier.fillMaxWidth(),
//        decorationBox = { inner ->
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(48.dp)
//                    .background(White, shape)
//                    .border(1.dp, fieldBorder, shape)
//                    .padding(horizontal = 14.dp),
//                contentAlignment = Alignment.CenterStart
//            ) {
//                if (value.isEmpty()) Text(text = hint, color = fieldHint, style = TextSize14().Regular())
//                inner()
//            }
//        }
//    )
//}
//
//@Composable
//private fun UploadCard(
//    kind: SheetKind,
//    sheet: ParsedSheet?,
//    errors: List<String>?,
//    onPick: () -> Unit,
//    onClear: () -> Unit
//) {
//    val shape = RoundedCornerShape(12.dp)
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(White, shape)
//            .border(1.dp, fieldBorder, shape)
//            .padding(16.dp)
//    ) {
//        Text(text = kind.cardTitle, color = bgColor, style = TextSize14().bold())
//        Spacer(Modifier.height(12.dp))
//        when {
//            sheet == null -> EmptyBox(kind, onPick)
//            !errors.isNullOrEmpty() -> ErrorBox(sheet, errors, onPick, onClear)
//            else -> SelectedBox(sheet, onClear)
//        }
//    }
//}
//
//@Composable
//private fun EmptyBox(kind: SheetKind, onPick: () -> Unit) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(10.dp))
//            .background(BackgorundColor)
//            .dashedBorder(dashBorderClr)
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null,
//                onClick = onPick
//            )
//            .padding(14.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Image(
//            painter = painterResource(Res.drawable.lesson_ic),
//            contentDescription = "",
//            modifier = Modifier.size(28.dp)
//        )
//        Spacer(Modifier.width(12.dp))
//        Column {
//            Text(text = "No ${kind.noun} file selected", color = bgColor, style = TextSize13().semiBold())
//            Spacer(Modifier.height(2.dp))
//            Text(
//                text = "Tap to upload ${kind.sampleFile}",
//                color = loginTabUnselectedTxtClr,
//                style = TextSize11().Regular()
//            )
//            Text(
//                text = "Columns: ${kind.columns.joinToString()}",
//                color = fieldHint,
//                style = TextSize11().Regular()
//            )
//        }
//    }
//}
//
//@Composable
//private fun SelectedBox(sheet: ParsedSheet, onClear: () -> Unit) {
//    val shape = RoundedCornerShape(10.dp)
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(successBoxBg, shape)
//            .border(1.dp, successBoxBorder, shape)
//            .padding(12.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Box(
//            modifier = Modifier.size(32.dp).background(approvedGreen, CircleShape),
//            contentAlignment = Alignment.Center
//        ) {
//            Image(
//                painter = painterResource(Res.drawable.check_mark),
//                contentDescription = "",
//                modifier = Modifier.size(14.dp),
//                colorFilter = ColorFilter.tint(White)
//            )
//        }
//        Spacer(Modifier.width(10.dp))
//        Column(modifier = Modifier.weight(1f)) {
//            Text(text = sheet.fileName, color = statusApprovedText, style = TextSize13().bold())
//            Text(
//                text = "${sheet.rows.size} rows read",
//                color = statusApprovedText,
//                style = TextSize11().Regular()
//            )
//        }
//        ClearChip(onClear)
//    }
//}
//
//@Composable
//private fun ErrorBox(sheet: ParsedSheet, errors: List<String>, onPick: () -> Unit, onClear: () -> Unit) {
//    val shape = RoundedCornerShape(10.dp)
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(statusRejectedBg, shape)
//            .border(1.dp, statusRejectedText.copy(alpha = 0.4f), shape)
//            .padding(12.dp)
//    ) {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text(
//                text = "${sheet.fileName.ifBlank { "File" }} – wrong format",
//                color = statusRejectedText,
//                style = TextSize13().bold(),
//                modifier = Modifier.weight(1f)
//            )
//            ClearChip(onClear)
//        }
//        Spacer(Modifier.height(6.dp))
//        errors.forEach {
//            Text(text = "• $it", color = statusRejectedText, style = TextSize11().Regular())
//        }
//        Spacer(Modifier.height(6.dp))
//        Text(
//            text = "Choose another file",
//            color = fieldCursor,
//            style = TextSize11().semiBold(),
//            modifier = Modifier.clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null,
//                onClick = onPick
//            )
//        )
//    }
//}
//
//@Composable
//private fun ClearChip(onClear: () -> Unit) {
//    Box(
//        modifier = Modifier
//            .background(White, RoundedCornerShape(6.dp))
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null,
//                onClick = onClear
//            )
//            .padding(horizontal = 10.dp, vertical = 4.dp)
//    ) { Text(text = "Clear", color = REdF14336, style = TextSize11().semiBold()) }
//}
//
//private fun Modifier.dashedBorder(color: Color, radius: Dp = 10.dp) = this.drawBehind {
//    drawRoundRect(
//        color = color,
//        cornerRadius = CornerRadius(radius.toPx()),
//        style = Stroke(
//            width = 1.dp.toPx(),
//            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f), 0f)
//        )
//    )
//}