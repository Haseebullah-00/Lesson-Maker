package com.lessonmaker.app.shared

import androidx.compose.runtime.Composable

interface PermissionHandler {

    @Composable
    fun askPermission(permission: PermissionType)

    @Composable
    fun isPermissionGranted(permission: PermissionType): Boolean

    @Composable
    fun isPermissionGrantedAndroid(permission: PermissionType, statusResults: (Boolean) -> Unit)


    @Composable
    fun launchSettings()
}