package com.lessonmaker.app.permissions.storage

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.lessonmaker.app.MainActivity
import com.lessonmaker.app.base.BaseActivity
import com.lessonmaker.app.infoDialog.InfoDialog
import com.lessonmaker.app.infoDialog.InfoDialogInterface
import com.lessonmaker.app.infoDialog.InfoDialogViewModel
import com.lessonmaker.app.infoDialog.openAppSystemSettings


class StorageViewModel(private val registry : ActivityResultRegistry, val context: Activity) :DefaultLifecycleObserver {

    var storageInterface: StorageInterface?=null

    lateinit var storagePermissionRequest : ActivityResultLauncher<Array<String>>

    lateinit var getImage : ActivityResultLauncher<PickVisualMediaRequest>
    lateinit var getMultipleImage : ActivityResultLauncher<PickVisualMediaRequest>

    lateinit var owner: LifecycleOwner

    var pickedImageCallBack: ((Uri) -> Unit)?=null
    var pickedImageCallBackMultiple: ((List<Uri>) -> Unit)?=null

    override fun onCreate(owner: LifecycleOwner) {
        this.owner=owner
        storagePermissionRequest = registry.register("permissionStorage", owner, ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (!doesHavePermissions()){
                storageInterface?.showDialog()
                return@register
            }
            storageInterface?.storageProvided()
        }
        getImage=registry.register("takeImageGallery",owner,ActivityResultContracts.PickVisualMedia()){
            it?.let {
                pickedImageCallBack?.invoke(it)
            }
        }
        getMultipleImage=registry.register("takeImageGalleryMulti",owner,ActivityResultContracts.PickMultipleVisualMedia(5)){
            it?.let {
                pickedImageCallBackMultiple?.invoke(it)
            }
        }
    }
    fun checkForStoragePermission() {
        if (!doesHavePermissions()){
            storagePermissionRequest.launch(permissionsList().toTypedArray())
            return
        }
        storageInterface?.storageProvided()
    }
    fun doesHavePermissions():Boolean{
        permissionsList().forEach {
            if (ContextCompat.checkSelfPermission(context, it)!= PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }

    fun waitForPermission(activity: BaseActivity, callback: (Boolean) -> Unit){
        if (activity.checkIfAndroid13OrLater()){
            callback.invoke(true)
            return
        }
        storageInterface=object : StorageInterface {
            override fun showDialog() {
                val viewModel= InfoDialogViewModel(toast = true, baseActivity = activity)
                viewModel.infoDialogInterface=object : InfoDialogInterface {
                    override fun onYesClicked() {
                        activity.openAppSystemSettings()
                    }
                    override fun onNoClicked() {
                    }
                }
                viewModel.storagePermissionRequired()
                InfoDialog(viewModel).show(activity.supportFragmentManager,"toast")
            }
            override fun storageProvided() {
                callback.invoke(true)
            }
        }
        checkForStoragePermission()
    }

    fun permissionsList():List<String>{
        val list=ArrayList<String>()
        //Storage
        if (context.checkIfAndroid13OrLater()){
            list.add(Manifest.permission.READ_MEDIA_IMAGES)
            list.add(Manifest.permission.READ_MEDIA_VIDEO)
        }else{
            list.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            if (context.checkIfAndroid1OrLess()){
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
        return list
    }


    fun pickImageFromGallery(activity: MainActivity, callback: (Uri) -> Unit){
        pickedImageCallBack=callback
        getImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
    fun pickImageFromGalleryMultiple(activity: MainActivity, callback: (List<Uri>) -> Unit){
        pickedImageCallBackMultiple=callback
        getMultipleImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
    fun pickVideoFromGallery(activity: MainActivity, callback: (Uri) -> Unit){
        pickedImageCallBack=callback
        getImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
    }
}
fun Context.checkIfAndroid13OrLater():Boolean{
    return  Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}
fun Context.checkIfAndroid1OrLess():Boolean{
    return  Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q
}