// shared/src/iosMain/kotlin/com/moovzy/user/shared/DatePicker.kt
package com.lessonmaker.app.shared

import platform.UIKit.*
import platform.Foundation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

actual class DatePicker {
    actual fun show(
        initialDate: LocalDate?,
        minDate: LocalDate,
        onDateSelected: (LocalDate) -> Unit,
        onDismiss: () -> Unit
    ) {
        val datePicker = UIDatePicker().apply {
            datePickerMode = UIDatePickerMode.UIDatePickerModeDate
            preferredDatePickerStyle = UIDatePickerStyle.UIDatePickerStyleWheels

            // Set minimum date to current date
            val localMinmumDate = minDate.toNSDate()
            minimumDate = localMinmumDate

            // Set initial date with validation
            initialDate?.let { localDate ->
                val nsDate = localDate.toNSDate()
                when (nsDate.compare(localMinmumDate)) {
                    NSOrderedAscending -> date = localMinmumDate
                    NSOrderedSame -> date = localMinmumDate
                    NSOrderedDescending -> date = nsDate
                }
            }

            // Blue color (RGB: 33, 150, 243)
            tintColor = UIColor.colorWithRed(
                red = 0.129,
                green = 0.588,
                blue = 0.953,
                alpha = 1.0
            )
        }

        val pickerViewController = UIViewController().apply {
            view.backgroundColor = UIColor.whiteColor
            view.addSubview(datePicker)

            datePicker.translatesAutoresizingMaskIntoConstraints = false
            NSLayoutConstraint.activateConstraints(listOf(
                datePicker.topAnchor.constraintEqualToAnchor(view.topAnchor, constant = 10.0),
                datePicker.bottomAnchor.constraintEqualToAnchor(view.bottomAnchor, constant = -10.0),
                datePicker.leadingAnchor.constraintEqualToAnchor(view.leadingAnchor, constant = 10.0),
                datePicker.trailingAnchor.constraintEqualToAnchor(view.trailingAnchor, constant = -10.0)
            ))
        }

        val alert = UIAlertController.alertControllerWithTitle(
            title = "Select Date",
            message = null,
            preferredStyle = UIAlertControllerStyleActionSheet
        )!!

        alert.setValue(pickerViewController, forKey = "contentViewController")

        val doneAction = UIAlertAction.actionWithTitle(
            title = "Done",
            style = UIAlertActionStyleDefault
        ) { _ ->
            val components = NSCalendar.currentCalendar.components(
                NSCalendarUnitYear or
                        NSCalendarUnitMonth or
                        NSCalendarUnitDay,
                fromDate = datePicker.date
            )
            onDateSelected(
                LocalDate(
                    year = components.year.toInt(),
                    monthNumber = components.month.toInt(),
                    dayOfMonth = components.day.toInt()
                )
            )
        }!!

        val cancelAction = UIAlertAction.actionWithTitle(
            title = "Cancel",
            style = UIAlertActionStyleCancel
        ) { _ -> onDismiss() }!!

        alert.addAction(doneAction)
        alert.addAction(cancelAction)

        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
            viewControllerToPresent = alert,
            animated = true,
            completion = null
        )
    }
    actual fun showDateOfBirth(
        initialDate: LocalDate?,
        onDateSelected: (LocalDate) -> Unit,
        onDismiss: () -> Unit
    ) {
        val datePicker = UIDatePicker().apply {
            datePickerMode = UIDatePickerMode.UIDatePickerModeDate
            preferredDatePickerStyle = UIDatePickerStyle.UIDatePickerStyleWheels

            // Correct: Set maximum date to today minus 18 years
            val localMaximumDate = miniMum18Years().toNSDate()
            maximumDate = localMaximumDate

            // Optional: Set minimum date if you want to restrict oldest age (e.g. 100 years old)
            val localMinimumDate = LocalDate(1900, 1, 1).toNSDate()
            minimumDate = localMinimumDate

            // Set initial date with validation
            initialDate?.let { localDate ->
                val nsDate = localDate.toNSDate()
                when {
                    nsDate.compare(localMinimumDate) == NSOrderedAscending -> date = localMinimumDate
                    nsDate.compare(localMaximumDate) == NSOrderedDescending -> date = localMaximumDate
                    else -> date = nsDate
                }
            } ?: run {
                // If no initial date provided, set to maximum date (i.e. 18 years ago)
                date = localMaximumDate
            }

            // Blue color (RGB: 33, 150, 243)
            tintColor = UIColor.colorWithRed(
                red = 0.129,
                green = 0.588,
                blue = 0.953,
                alpha = 1.0
            )
        }

        val pickerViewController = UIViewController().apply {
            view.backgroundColor = UIColor.whiteColor
            view.addSubview(datePicker)

            datePicker.translatesAutoresizingMaskIntoConstraints = false
            NSLayoutConstraint.activateConstraints(listOf(
                datePicker.topAnchor.constraintEqualToAnchor(view.topAnchor, constant = 10.0),
                datePicker.bottomAnchor.constraintEqualToAnchor(view.bottomAnchor, constant = -10.0),
                datePicker.leadingAnchor.constraintEqualToAnchor(view.leadingAnchor, constant = 10.0),
                datePicker.trailingAnchor.constraintEqualToAnchor(view.trailingAnchor, constant = -10.0)
            ))
        }

        val alert = UIAlertController.alertControllerWithTitle(
            title = "Select Date",
            message = null,
            preferredStyle = UIAlertControllerStyleActionSheet
        )

        alert.setValue(pickerViewController, forKey = "contentViewController")

        val doneAction = UIAlertAction.actionWithTitle(
            title = "Done",
            style = UIAlertActionStyleDefault
        ) { _ ->
            val components = NSCalendar.currentCalendar.components(
                NSCalendarUnitYear or
                        NSCalendarUnitMonth or
                        NSCalendarUnitDay,
                fromDate = datePicker.date
            )
            onDateSelected(
                LocalDate(
                    year = components.year.toInt(),
                    monthNumber = components.month.toInt(),
                    dayOfMonth = components.day.toInt()
                )
            )
        }!!

        val cancelAction = UIAlertAction.actionWithTitle(
            title = "Cancel",
            style = UIAlertActionStyleCancel
        ) { _ -> onDismiss() }

        alert.addAction(doneAction)
        alert.addAction(cancelAction)

        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
            viewControllerToPresent = alert,
            animated = true,
            completion = null
        )
    }

    fun miniMum18Years(): LocalDate {
        val today = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        return today.minus(18, DateTimeUnit.YEAR)
    }
}

private fun LocalDate.toNSDate(): NSDate {
    val calendar = NSCalendar.currentCalendar
    val components = NSDateComponents().apply {
        year = this@toNSDate.year.toLong()
        month = this@toNSDate.monthNumber.toLong()
        day = this@toNSDate.dayOfMonth.toLong()
        hour = 0
        minute = 0
        second = 0
    }
    return calendar.dateFromComponents(components)!!
}

@Composable
actual fun rememberDatePicker(): DatePicker = remember { DatePicker() }

actual fun defaultMinimDate(): LocalDate {
    return NSDate.date().toLocalDate()
}
private fun NSDate.toLocalDate(): LocalDate {
    val calendar = NSCalendar.currentCalendar
    calendar.timeZone = NSTimeZone.timeZoneWithAbbreviation("UTC")!!

    val components = calendar.components(
        NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
        this
    )

    return LocalDate(
        year = components.year.toInt(),
        monthNumber = components.month.toInt(),
        dayOfMonth = components.day.toInt()
    )
}