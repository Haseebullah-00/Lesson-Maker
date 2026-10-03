package com.lessonmaker.app.infoDialog

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.lessonmaker.app.R


class InfoDialogViewModel(var toast:Boolean=false,var message: String="",var baseActivity: Context) :ViewModel() {

    lateinit var dialogDismissInterface: DialogDismissInterface
    var infoDialogInterface: InfoDialogInterface?=null


    fun onYesButtonClicked(view: View){
        infoDialogInterface?.onYesClicked()
        dialogDismissInterface.onDismissClicked()
    }
    fun onNoButtonClicked(view: View){
        infoDialogInterface?.onNoClicked()
        dialogDismissInterface.onDismissClicked()
    }

    var isCancelAble=true

    @JvmField
    var yesText=ObservableField<String>()
    @JvmField
    var noText=ObservableField<String>()

    @JvmField
    var showYes=ObservableField<Boolean>(false)
    @JvmField
    var showNo=ObservableField<Boolean>(false)
    @JvmField
    var showNoTwo=ObservableField<Boolean>(false)

    @JvmField
    var showBlackHeading= ObservableField<Boolean>(true)

    @JvmField
    var showBoarderButton=ObservableField<Boolean>(false)

    @JvmField
    var showTitle=ObservableField<Boolean>(false)
    @JvmField
    var showLogo=ObservableField<Boolean>(false)
    @JvmField
    var showHeading=ObservableField<Boolean>(false)
    @JvmField
    var showSubHading=ObservableField<Boolean>(false)

    @JvmField
    var showYesNo=ObservableField<Boolean>(false)

    @JvmField
    var title=ObservableField<String>()
    @JvmField
    var heading=ObservableField<String>()
    @JvmField
    var subHeading=ObservableField<String>()
    @JvmField
    var logo=ObservableField<Int?>()




    init {
        if (toast){
            showToast(string=message)
        }
    }

    fun showToast(string: String){
        heading.set(string)
        showHeading.set(true)
        showYes.set(true)
        yesText.set(baseActivity.getString(R.string.ok))
    }

    fun yesNoPopUp(string: String,baseActivity: Context){
        heading.set(string)
        showHeading.set(true)
        showYes.set(true)
        yesText.set(baseActivity.getString(R.string.yes))
        showNo.set(true)
        noText.set(baseActivity.getString(R.string.no))
    }
    fun storagePermissionRequired(){
        title.set(baseActivity.getString(R.string.storage_permission_title))
        heading.set(baseActivity.getString(R.string.storage_permission_heading))
        noText.set(baseActivity.getString(R.string.exit))
        yesText.set(baseActivity.getString(R.string.settings))
        showHeading.set(true)
        showYes.set(true)
        showTitle.set(true)
        showNo.set(true)
    }
    fun cameraPermissionRequired(){
        title.set(baseActivity.getString(R.string.camera_permission_title))
        heading.set(baseActivity.getString(R.string.camera_permission_heading))
        noText.set(baseActivity.getString(R.string.exit))
        yesText.set(baseActivity.getString(R.string.settings))
        showHeading.set(true)
        showYes.set(true)
        showTitle.set(true)
        showNo.set(true)
    }

    fun locationPermissionRequired(){
        title.set(baseActivity.getString(R.string.location_permission_title))
        heading.set(baseActivity.getString(R.string.location_permission_heading))
        noText.set(baseActivity.getString(R.string.exit))
        yesText.set(baseActivity.getString(R.string.settings))
        showHeading.set(true)
        showYes.set(true)
        showTitle.set(true)
        showNo.set(true)
    }

}