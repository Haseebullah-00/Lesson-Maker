package com.lessonmaker.app.base


import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.lessonmaker.app.infoDialog.InfoDialog
import com.lessonmaker.app.infoDialog.InfoDialogInterface
import com.lessonmaker.app.infoDialog.InfoDialogViewModel
import com.lessonmaker.app.infoDialog.openAppSystemSettings
import com.lessonmaker.app.di.SharedPreferenceManager
import com.lessonmaker.app.permissions.camera.CameraViewModel
import com.lessonmaker.app.permissions.storage.StorageViewModel
import com.lessonmaker.app.utility.ConnectionLiveData

import org.koin.android.ext.android.inject

open class BaseActivity : AppCompatActivity() {

    var storageViewModel: StorageViewModel? = null
    var cameraViewModel: CameraViewModel? = null
     val sharePref by inject<SharedPreferenceManager>()
//    lateinit var locationViewModel: LocationViewModel



    lateinit var connectionLiveData: ConnectionLiveData
    lateinit var commonDialog: Dialog

//
//    private fun implementLocation() {
//        locationViewModel = LocationViewModel(registry = activityResultRegistry, context = this)
//        locationViewModel.locationInterface = object : LocationInterface {
//            override fun locationProvided() {
//
//            }
//
//            override fun showTurnOnLocation() {
//                if (locationViewModel.dialogShowing) {
//                    return
//                }
//                locationViewModel.dialogShowing = true
//                val viewModel = InfoDialogViewModel(toast = true, baseActivity = this@BaseActivity)
//                viewModel.infoDialogInterface = object : InfoDialogInterface {
//                    override fun onYesClicked() {
//                        this@BaseActivity.openAppSystemSettings()
//                    }
//
//                    override fun onNoClicked() {
//                        finishAffinity()
//                    }
//                }
//                viewModel.locationPermissionRequired()
//                InfoDialog(viewModel).show(supportFragmentManager, "toast")
//            }
//        }
//        lifecycle.addObserver(locationViewModel)
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // implementLocation()
        storageViewModel = StorageViewModel(registry = activityResultRegistry, context = this)
        storageViewModel?.let {
            lifecycle.addObserver(it)
        }
        cameraViewModel = CameraViewModel(registry = activityResultRegistry, context = this)
        cameraViewModel?.let {
            lifecycle.addObserver(it)
        }


        commonDialog = showNoConnectionDialog(this)
        connectionLiveData = ConnectionLiveData(this)
        netWorkCheck()
      }

    fun netWorkCheck() {
        connectionLiveData.observe(this) { isConnected ->
            isConnected?.let {
                showMessage(isConnected)
            }
        }
    }

    private fun showMessage(isConnected: Boolean) {
        if (!isConnected) {
            if (!commonDialog.isShowing) {
                commonDialog.show()
            }
        } else {
            if (commonDialog.isShowing) {
                commonDialog.dismiss()
            }
        }
    }

    fun showNoConnectionDialog(context: Context): AlertDialog {
        return AlertDialog.Builder(context).setMessage(
            "No internet"
        ).setCancelable(false).setNeutralButton("Check connection")
        { _, _ ->
            try {
                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }.create()
    }


    fun showKeyboard() {
        val inputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun hideSoftKeyboard() {
        try {
            currentFocus?.let {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
