package com.lessonmaker.app.shared

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.lessonmaker.app.shared.map.findActivity

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    return remember { PermissionsManager(callback) }
}

actual class PermissionsManager actual constructor(private val callback: PermissionCallback) :
    PermissionHandler {
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    actual override fun askPermission(permission: PermissionType) {

        when (permission) {
            PermissionType.CAMERA -> {
                LocalContext.current.findActivity()?.let {
                    it.cameraViewModel?.waitForPermission(activity = it){
                        if (it){
                            callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                        }
                    }
                }
            }

            PermissionType.GALLERY -> {
                LocalContext.current.findActivity()?.let {
                    it.storageViewModel?.waitForPermission(activity = it){
                        if (it){
                            callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                        }
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    actual override fun isPermissionGranted(permission: PermissionType):Boolean {
         when (permission) {
            PermissionType.CAMERA -> {
                LocalContext.current.findActivity()?.let {
                    it.cameraViewModel?.waitForPermission(activity = it){
                       return@waitForPermission
                    }
                }
            }

            PermissionType.GALLERY -> {
                LocalContext.current.findActivity()?.let {
                    it.storageViewModel?.waitForPermission(activity = it){
                        return@waitForPermission

                    }
                }
            }
        }
        return true

    }


    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    actual override fun isPermissionGrantedAndroid(permission: PermissionType,statusResults: (Boolean) -> Unit) {
        when (permission) {
            PermissionType.CAMERA -> {
                LocalContext.current.findActivity()?.let {
                    it.cameraViewModel?.waitForPermission(activity = it){
                        statusResults.invoke(it)
                    }
                }
            }

            PermissionType.GALLERY -> {
                LocalContext.current.findActivity()?.let {
                    it.storageViewModel?.waitForPermission(activity = it){
                        statusResults.invoke(it)
                    }
                }
            }
        }
    }


    @Composable
    actual override fun launchSettings() {
        val context = LocalContext.current
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        ).also {
            context.startActivity(it)
        }
    }
}