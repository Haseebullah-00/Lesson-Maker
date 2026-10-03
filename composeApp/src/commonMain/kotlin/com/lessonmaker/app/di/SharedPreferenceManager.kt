package com.lessonmaker.app.di
import com.russhwolf.settings.Settings
import com.lessonmaker.app.extensionFunctions.handleNull
import com.lessonmaker.app.models.auth.SingleUserModel

import kotlinx.serialization.json.Json

class SharedPreferenceManager(private val settings: Settings) {

    private val LOGGED_IN_KEY = "loggedIn"
    private val ACCESS_TOKEN_KEY = "access_token"
    private val DEVICE_CART_ID_KEY = "deviceCartID"
    private val TEMP_USER= "temp_user"
    private val Current_User="current_user"

    private val FCM_TOKEN="fcm_token"


    var getFcmToken: String
        get() = settings.getString(FCM_TOKEN, "fcm_token")
        set(value) = settings.putString(FCM_TOKEN, value)

    var deviceCartID: String
        get() = settings.getString(DEVICE_CART_ID_KEY, "DefaultID")
        set(value) = settings.putString(DEVICE_CART_ID_KEY, value)

    var isLoggedIn: Boolean
        get() = settings.getBoolean(LOGGED_IN_KEY, false)
        set(value) = settings.putBoolean(LOGGED_IN_KEY, value)

    var accessToken: String?
        get() = settings.getStringOrNull(ACCESS_TOKEN_KEY)
        set(value) = value.let { settings.putString(ACCESS_TOKEN_KEY, it.handleNull()) }

    var tempUser: String?
        get() = settings.getStringOrNull(TEMP_USER)
        set(value) = value.let { settings.putString(TEMP_USER, it.handleNull()) }


    fun saveTemUser(user: SingleUserModel,) {
        tempUser= Json.encodeToString(user)
    }
    fun getTempUser(): SingleUserModel?{
        return tempUser?.let { Json.decodeFromString(it) }
    }


    var currentUser: String?
        get() = settings.getStringOrNull(Current_User)
        set(value) = value.let { settings.putString(Current_User, it.handleNull()) }


    fun saveCurrentUser(user: SingleUserModel,) {
        currentUser= Json.encodeToString(user)
    }
    fun getCurrentUser(): SingleUserModel?{
        return currentUser?.let { Json.decodeFromString(it) }
    }

    fun clear() {
        settings.clear()
    }


    var sellerRegistered: Boolean
        get() = settings.getBoolean("sellerRegistered", false)
        set(value) = settings.putBoolean("sellerRegistered", value)
}

