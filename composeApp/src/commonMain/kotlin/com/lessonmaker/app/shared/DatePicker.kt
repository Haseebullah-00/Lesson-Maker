package com.lessonmaker.app.shared

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate

expect class DatePicker {
    fun show(
        initialDate: LocalDate?,
        minDate: LocalDate,
        onDateSelected: (LocalDate) -> Unit,
        onDismiss: () -> Unit = {}
    )
    fun showDateOfBirth(
        initialDate: LocalDate?,
        onDateSelected: (LocalDate) -> Unit,
        onDismiss: () -> Unit = {}
    )
}

@Composable
expect fun rememberDatePicker(): DatePicker

expect fun defaultMinimDate(): LocalDate