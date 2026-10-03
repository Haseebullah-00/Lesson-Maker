package com.lessonmaker.app.extensionFunctions

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

fun String.handleStartingZero(): String {
    if (this.startsWith("0")) {
        this.subSequence(IntRange(1, this.length - 1)).toString().handleStartingZero()
    }
    return this
}

fun String.removePlus(): String {
    return this.replace("+", "")
}

fun String.addPlus(): String {
    return "+${this.removePlus()}"
}

fun String.addHash(): String {
    if (this.contains("#")) {
        return ""
    }
    return "#${this}"
}



fun Int.intToBool(): Boolean {
    return this == 1
}

fun Boolean.boolToInt(): Int {
    if (this) {
        return 1
    }
    return 0
}



fun Long.getMinSec(): String {
    if (this < 1000) {
        return "0:00 sec"
    }

    val minutes = this / 1000 / 60
    val seconds = this / 1000 % 60

    return "${minutes}:${seconds} sec"
}

fun Long.getMinSecList(): FormatedTime {
    if (this < 1000) {
        return FormatedTime()
    }
    val seconds: Long = this / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return FormatedTime(
        days = "${days.alwaysThree()}D",
        hours = "${(hours % 24).alwaysThree()}H",
        min = "${(minutes % 60).alwaysThree()}M",
        sec = "${(seconds % 60).alwaysThree()}S"
    )
}

fun Long.alwaysThree(): String {
    if (this < 10) {
        return " $this"
    }
    return "$this"
}

data class FormatedTime(
    var days: String = "0D",
    var hours: String = "0H",
    var min: String = "0M",
    var sec: String = "0S"
)



fun Any?.checkForSuccess(): Boolean {
    if (this is Int) {
        return this.intToBool()
    }
    if (this is Double) {
        return this.toInt().intToBool()
    }
    if (this is Long) {
        return this.toInt().intToBool()
    }
    if (this is Boolean) {
        return this
    }
    if (this is String) {
        when (this) {
            "1" -> {
                return true
            }
            else -> {
                return false
            }
        }
    }
    return false
}
fun <T> List<T>?.handleNullList(): ArrayList<T> {
    val array = ArrayList<T>()
    this?.let {
        array.addAll(this)
    }
    return array
}

fun String?.makeNull(): String? {
    if (this.isNullOrEmpty()) {
        return null
    }
    if (this.handleNull().trim().isEmpty()) {
        return null
    }
    return this
}

fun Int?.makeNullString(): String? {
    if (this == null) {
        return null
    }
    return this.toString()
}




fun String?.addStarsPhone(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    return this.mapIndexed { index, c ->
        if ((this.length - 2 == index || this.length - 3 == index) || (index > 0 && index < 3)) "*" else c
    }.joinToString("")
}

fun String?.hidePhoneFirstDigits(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    return this.mapIndexed { index, c ->
        if ((this.length - 2 == index || this.length - 3 == index) || this.length - 1 == index) c else "*"
    }.joinToString("")
}

fun String.convertEmail(): String {
    val parts = this.split("@")
    val username = parts[0]
    val domain = parts[1]

    val modifiedUsername = username.replaceRange(username.length - 2, username.length, "xx")
    val modifiedDomain = domain.replaceRange(0, domain.length, "xxxxx.xx")

    return "$modifiedUsername@$modifiedDomain"
}


fun String?.toDoubleLocal():Double{
    if (this.isNullOrEmpty()){
        return 0.0
    }
    this.toDoubleOrNull()?.let {
        return it
    }
    return 0.0
}
fun String?.toIntLocal(): Int {
    if (this.isNullOrEmpty()) {
        return 0
    }
    return this.toInt()
}
fun String?.toIntLocalNullAble(): Int? {
    if (this.isNullOrEmpty()) {
        return null
    }
    return this.toIntOrNull()
}


fun Any?.handLocalString(): String {
    when (this) {
        is Int -> {
            return this.toString()
        }
        is Long -> {
            return this.toInt().toString()
        }
        is Float -> {
            return this.toInt().toString()
        }
        is Double -> {
            return this.toInt().toString()
        }
        is String -> {
            return this.replace(",", "").toString()
        }
        else -> {
            return ""
        }
    }
}

fun String.statusHandleRateShowProduct(): Boolean {
    when (this) {
        "4" -> {
            return true
        }
        else -> {
            return false
        }
    }
}

fun String.paymentRefundAble(): Boolean {
    return when (this) {
        "1", "2", "3", "4" -> {
            true
        }
        else -> {
            false
        }
    }
}

fun String.statusHandleRateShowService(): Boolean {
    when (this) {
        "4" -> {
            return true
        }
        else -> {
            return false
        }
    }
}

fun String?.isOutOfStock(): Boolean {
    if (this == null) {
        return true
    }
    this.toIntOrNull()?.let {
        return it < 1
    }
    return false
}



fun Any.checkRatingNotZero(): Boolean {
    val rating = this.getRatingDouble()
    return rating > 0
}

fun Any?.getRatingDouble(): Double {
    when (this) {
        is Int -> {
            return this.toDouble()
        }
        is Long -> {
            return this.toDouble()
        }
        is Float -> {
            return this.toDouble()
        }
        is Double -> {
            return this
        }
        is String -> {
            this.replace(",", "").toDoubleOrNull()?.let {
                return it
            }
            return 0.0
        }
        else -> {
            return 0.0
        }
    }

}

fun <T> ArrayList<T>.swapPositions(index1: Int, index2: Int) {
    try {
        if (index1 < 0 || index1 >= size || index2 < 0 || index2 >= size) {
            return
        }
        val temp = this[index1]
        this[index1] = this[index2]
        this[index2] = temp
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun String?.handleNull(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    if (this.equals("null",true)){
        return ""
    }
    return this
}
fun String?.handlePrice():String{
    if (this.toDoubleLocal() < 0.01) {
        return ""
    }
    return this.handleNull()
}



fun String?.createFullNumber(dialCode:String?,addPlus:Boolean=true):String{
    if (this.isNullOrEmpty()){
        return ""
    }
    if (dialCode.isNullOrEmpty()){
        return ""
    }
    if (addPlus){
        return dialCode.handleNull().plus(this.handleNull()).addPlus()
    }
    return dialCode.handleNull().plus(this.handleNull())
}


fun Int.getDatePair():Pair<LocalDate,LocalDate>{
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    if (this==1){
        return Pair(today,today)
    }
    // Current Week (Monday to Sunday)
    val dayOfWeek = today.dayOfWeek // e.g., TUESDAY, WEDNESDAY
    val startOfWeek = today.minus(dayOfWeek.ordinal.toLong(), DateTimeUnit.DAY)
    val endOfWeek = startOfWeek.plus(6, DateTimeUnit.DAY)
    if (this==2){
        return Pair(startOfWeek,endOfWeek)
    }
    val monthDay= today.dayOfMonth
    val startOfMonth = today.minus(monthDay.toLong(), DateTimeUnit.DAY)
    val endOfMonth = startOfMonth.plus(1, DateTimeUnit.MONTH)

    return Pair(startOfMonth, endOfMonth)
}

fun <T> List<T>.difference(other: List<T>): List<T> {
    return this - other
}