package com.lessonmaker.app.permissions

import org.jetbrains.annotations.Contract
import java.text.SimpleDateFormat
import java.util.*

object Utilities {
    @Contract(" -> new")
    fun GetFileNameFormat(): SimpleDateFormat {
        return SimpleDateFormat("dd_MM_yyyy_hh_mm_ss_SSS", Locale.ENGLISH)
    }

    @Contract(" -> new")
    fun AudioFileName(): SimpleDateFormat {
        return SimpleDateFormat("yyyy_MM_dd_hh_mm_ss_SSS", Locale.ENGLISH)
    }

    fun GetName(): String {
        return GetFileNameFormat().format(Calendar.getInstance().time)
    }

    @Contract(" -> new")
    fun ReviewDateFormat(): SimpleDateFormat {
        return SimpleDateFormat("dd MMM ", Locale.ENGLISH)
    }

    @Contract(" -> new")
    fun NotificationDate(): SimpleDateFormat {
        return SimpleDateFormat("hh mm a , MMM dd yyyy ", Locale.ENGLISH)
    }

    fun NotificationsTime(date: Date): String {
        return NotificationDate().format(date)
    }

    @get:Contract(" -> new")
    val dayNameFormat: SimpleDateFormat
        get() {
            return SimpleDateFormat("EEE", Locale.ENGLISH)
        }

    @get:Contract(" -> new")
    val dayNumberFormat: SimpleDateFormat
        get() {
            return SimpleDateFormat("dd", Locale.ENGLISH)
        }

    @get:Contract(" -> new")
    val yearFormat: SimpleDateFormat
        get() {
            return SimpleDateFormat("dd MMM ", Locale.ENGLISH)
        }

    private fun getYear(calendar: Calendar): String {
        return yearFormat.format(calendar.time)
    }

    fun GetProcessingDatePayment(date: Date, number_of_days: Int): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + number_of_days)
        return "Estimate processing date \n" + getYear(calendar)
    }

    fun GetReviewTimeString(date: Date, number_of_days: Int): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + number_of_days)
        return "Review By: " + ReviewDateFormat().format(calendar.time)
    }


}