package com.lessonmaker.app.utility

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

fun LocalDate.toCalendar(): Calendar {
    val date: Date = Date.from(
        this.toJavaLocalDate() // Convert kotlinx.datetime.LocalDate to java.time.LocalDate
            .atStartOfDay(ZoneId.systemDefault()) // Start of day with system's timezone
            .toInstant() // Convert to Instant
    )
    return Calendar.getInstance().apply {
        time = date
    }
}

fun View.getLocalText():String{
    if (this is TextInputEditText){
        return this.text.toString()
    }
    if (this is EditText){
        return this.text.toString()
    }
    if (this is TextView){
        return this.text.toString()
    }
    return ""
}
fun <T> Bundle.getExtraOrNull(key:String):T?{
    if (this.containsKey(key)){
        try {
            return this.get(key) as T
        }catch (e:Exception){
            return null
        }
    }
    return null
}
fun Uri.getExtraOrNullQuery(key:String):String?{
    if (this.toString().contains(key)){
        val query=this.getQueryParameter(key)
        query?.let {
            return it
        }
    }
    return null
}

inline fun <reified T> Bundle.convertToModel(key:String):T?{
    if (this.containsKey(key)){
        getExtraOrNull<String>(key)?.let {
            try {
                Gson().fromJson(it,T::class.java)?.let {
                    return it
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
    return null
}
fun Bundle?.handleEmpty(): Bundle {
    if (this == null) {
        return Bundle()
    }
    return this
}
fun Context.myToast(message: String?) {
    if (!message.isNullOrEmpty() && !message.contains("Unable to resolve host")) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
