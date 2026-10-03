package com.lessonmaker.app.permissions.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.lessonmaker.app.base.BaseActivity
import com.lessonmaker.app.infoDialog.InfoDialog
import com.lessonmaker.app.infoDialog.InfoDialogInterface
import com.lessonmaker.app.infoDialog.InfoDialogViewModel
import com.lessonmaker.app.infoDialog.openAppSystemSettings
import com.lessonmaker.app.permissions.Utilities

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CameraViewModel(private val registry : ActivityResultRegistry, val context: BaseActivity) :DefaultLifecycleObserver {

    var cameraInterface: CameraInterface?=null
    var imageUri:String?=null
    var videoUri:Uri?=null
    var callback: ((Uri) -> Unit)?=null

    lateinit var storagePermissionRequest : ActivityResultLauncher<Array<String>>
    lateinit var cameraImageIntent : ActivityResultLauncher<Intent>
    lateinit var cameraVideoIntent: ActivityResultLauncher<Intent>

    fun registerVideoLauncher(activity: ComponentActivity) {
        cameraVideoIntent = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                videoUri?.let {
                    callback?.invoke(it)
                }
            }
        }
    }

    lateinit var owner: LifecycleOwner
    override fun onCreate(owner: LifecycleOwner) {
        this.owner=owner
        storagePermissionRequest = registry.register("permissionCamera", owner, ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (!doesHavePermissions()){
                cameraInterface?.showDialog()
                return@register
            }
            cameraInterface?.cameraProvided()
        }


        cameraImageIntent = registry.register("cameraImage", owner, ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode==Activity.RESULT_OK){
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        imageUri?.let {uri->
                            uri.let {
                                it.handleImageRotation()?.let {
                                    callback?.invoke(it)
                                }
                            }
                        }
                    }catch (e:Exception){
                        try {
                            imageUri?.let {uri->
                                callback?.invoke(Uri.parse(uri))
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
        registerVideoLauncher(activity = context)
    }
    fun checkForStoragePermission() {
        if (!doesHavePermissions()){
            storagePermissionRequest.launch(arrayOf(Manifest.permission.CAMERA))
            return
        }
        cameraInterface?.cameraProvided()
    }
    fun doesHavePermissions():Boolean{
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED
    }

    fun waitForPermission(activity: BaseActivity, callback: (Boolean) -> Unit){
        cameraInterface=object : CameraInterface {
            override fun showDialog() {
                val viewModel= InfoDialogViewModel(toast = true, baseActivity = activity)
                viewModel.infoDialogInterface=object : InfoDialogInterface {
                    override fun onYesClicked() {
                        activity.openAppSystemSettings()
                    }
                    override fun onNoClicked() {
                    }
                }
                viewModel.cameraPermissionRequired()
                InfoDialog(viewModel).show(activity.supportFragmentManager,"toast")
            }
            override fun cameraProvided() {
                callback.invoke(true)
            }
        }
        checkForStoragePermission()
    }


    fun takeImageNow(activity: BaseActivity, callback: (Uri) -> Unit){
        this.callback=callback

        activity.createImageFile { s, photoFile ->
            imageUri = s
            // Continue only if the File was successfully created

            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    activity,
                    "com.comfort.life.fileprovider",
                    it
                )
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                cameraImageIntent.launch(cameraIntent)
            }
        }

    }
    fun recordVideoNow(activity: BaseActivity, callback: (Uri) -> Unit) {
        this.callback = callback

        activity.createVideoFile { s, videoFile ->
            videoUri = s
            // Continue only if the File was successfully created
            videoFile?.also {
                val videoURI: Uri = FileProvider.getUriForFile(
                    activity,
                    "com.comfort.life.fileprovider",
                    it
                )
                val videoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI)
                videoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1) // 1 = high quality
                cameraVideoIntent.launch(videoIntent)
            }
        }
    }
    fun String.handleImageRotation(): Uri? {
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, this.rotateBitMap(), "IMG_" + SimpleDateFormat("hh_mm_ss_a", Locale.ENGLISH).format(
            Calendar.getInstance().time), null)
        return Uri.parse(path)
    }

    private fun String.rotateBitMap(): Bitmap {
        val bitmap = BitmapFactory.decodeFile(this)
        var angle = 0f
        try {
            val ei = ExifInterface(this)
            val orientation = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )
            angle = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                ExifInterface.ORIENTATION_NORMAL -> 0f
                else -> 0f
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val matrix = Matrix()
        matrix.postRotate(angle)

        val rotatedBitmap = Bitmap.createBitmap(
            bitmap!!,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
        return rotatedBitmap
    }

}

fun BaseActivity.createVideoFile(onResult: (Uri?, File?) -> Unit) {
    try {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val videoFileName = "VIDEO_${timeStamp}_"
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        val videoFile = File.createTempFile(
            videoFileName, /* prefix */
            ".mp4",        /* suffix */
            storageDir     /* directory */
        )
        val uri = Uri.fromFile(videoFile)
        onResult(uri, videoFile)
    } catch (ex: IOException) {
        ex.printStackTrace()
        onResult(null, null)
    }
}

fun BaseActivity.createImageFile(callback:(String, File?)->Unit){
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
        val file = File("$filesDir/" + Utilities.AudioFileName().format(Calendar.getInstance().time) + ".jpg"
        )
        callback.invoke(file.absolutePath,file)
        return
    }

    // Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    if (storageDir==null){
        callback.invoke("",null)
        return
    }
    var currentPhotoPath:String=""
    val file=File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    ).apply {
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = absolutePath
    }
    callback.invoke(currentPhotoPath,file)
}