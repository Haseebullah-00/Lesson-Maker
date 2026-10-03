// shared/src/androidMain/kotlin/com/moovzy/user/shared/DatePicker.kt
package com.lessonmaker.app.shared

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.lessonmaker.app.utility.toCalendar
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date


actual class DatePicker {
    private lateinit var context: Context  // Store context internally

    fun initialize(context: Context) {
        this.context = context
    }

    actual fun show(
        initialDate: LocalDate?,
        minDate: LocalDate,
        onDateSelected: (LocalDate) -> Unit,
        onDismiss: () -> Unit
    ) {
        val calendar = Calendar.getInstance().apply {
            initialDate?.let {
                set(it.year, it.monthNumber - 1, it.dayOfMonth)
            }
        }

        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, day ->
                onDateSelected(LocalDate(year, month + 1, day))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Block past dates
        datePickerDialog.datePicker.minDate = minDate.toCalendar().timeInMillis

        datePickerDialog.setOnCancelListener { onDismiss() }
        datePickerDialog.show()
    }
    actual fun showDateOfBirth(
        initialDate: LocalDate?,
        onDateSelected: (LocalDate) -> Unit,
        onDismiss: () -> Unit
    ) {
        val calendar = Calendar.getInstance().apply {
            initialDate?.let {
                set(it.year, it.monthNumber - 1, it.dayOfMonth)
            }
        }

        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, day ->
                onDateSelected(LocalDate(year, month + 1, day))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Block past dates
        datePickerDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis

        datePickerDialog.setOnCancelListener { onDismiss() }
        datePickerDialog.show()
    }
}

@Composable
actual fun rememberDatePicker(): DatePicker {
    val context = LocalContext.current
    return remember {
        DatePicker().apply {
            initialize(context)
        }
    }
}


actual fun defaultMinimDate(): LocalDate {
      return Calendar.getInstance().time.toLocalDate()
}
fun Date.toLocalDate(): LocalDate {
    return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toKotlinLocalDate()
}