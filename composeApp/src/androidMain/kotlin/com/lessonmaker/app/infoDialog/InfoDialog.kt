package com.lessonmaker.app.infoDialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.lessonmaker.app.MainActivity
import com.lessonmaker.app.base.BaseActivity
import com.lessonmaker.app.databinding.DialogInfoBinding


class InfoDialog(val infoDialogViewModel: InfoDialogViewModel, val callBack: ((Boolean) -> Unit)? =null) : DialogFragment() {
    lateinit var binding: DialogInfoBinding
    lateinit var myDialog: Dialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        binding = DialogInfoBinding.inflate(requireActivity().layoutInflater)
        infoDialogViewModel.dialogDismissInterface=object : DialogDismissInterface {
            override fun onDismissClicked() {
                callBack?.invoke(true)
                dialog?.dismiss()
            }
        }
        binding.viewModel=infoDialogViewModel
        isCancelable=true
         builder.setView(binding.root)
         myDialog = builder.create()

         myDialog.adjustSize()

        return myDialog
    }


}


fun MainActivity.showLocationPermissionRequired(callBack: ((Boolean) -> Unit)?){
    val infoDialogViewModel= InfoDialogViewModel(baseActivity = this)
    infoDialogViewModel.locationPermissionRequired()
    infoDialogViewModel.infoDialogInterface=object : InfoDialogInterface {
        override fun onYesClicked() {
            callBack?.invoke(true)
        }

        override fun onNoClicked() {
            callBack?.invoke(false)
        }
    }
//    InfoDialog(infoDialogViewModel = infoDialogViewModel).show(this.supportFragmentManager,"location")
}

fun Context.openAppSystemSettings() {
    startActivity(Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", packageName, null)
    })
}

fun FragmentManager.showToast(message: String?, isSuccess:Boolean=true, showIcon:Boolean=false, baseActivity: BaseActivity, callBack:((Boolean)->Unit)?=null): DialogFragment?{
    if (message.isNullOrEmpty()){
        callBack?.invoke(false)
        return null
    }

    val viewModel= InfoDialogViewModel(toast = true, message = message, baseActivity = baseActivity)
    viewModel.showLogo.set(showIcon)
    try {
        val dialog= InfoDialog(viewModel,callBack=callBack)
        dialog.show(this,"toast")
        return dialog
    }catch (e:Exception){
        e.printStackTrace()
    }
    return null
}
fun FragmentManager.showChoice(message: String?,
                               title: String?=null,
                               isSuccess:Boolean=true,
                               showIcon:Boolean=false,
                               baseActivity: MainActivity,
                               callBack:((Boolean)->Unit)?=null){
    if (message.isNullOrEmpty()){
        callBack?.invoke(false)
        return
    }

    val viewModel= InfoDialogViewModel(toast = true, "", baseActivity)
    viewModel.infoDialogInterface=object : InfoDialogInterface {
        override fun onYesClicked() {
            callBack?.invoke(true)
        }

        override fun onNoClicked() {
            callBack?.invoke(false)
        }
    }

    viewModel.yesNoPopUp(message,baseActivity)
    title?.let {
        viewModel.title.set(title)
        viewModel.showTitle.set(true)
    }
    viewModel.showLogo.set(showIcon)

    InfoDialog(viewModel).show(this,"toast")
}

fun Context?.PxToDp(dpValue:Int):Int{
    try {
        this?.let {
            val dpRatio: Float = it.resources.displayMetrics.density
            return (dpValue * dpRatio).toInt()
        }
    }catch (e:Exception){
        e.printStackTrace()
    }
    return dpValue
}
fun Dialog.adjustSize(){
    window?.let {
        it.setLayout(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT)
        val back = ColorDrawable(Color.TRANSPARENT)
        val margin = context.PxToDp(25)
        val inset = InsetDrawable(back, margin)
        it.setBackgroundDrawable(inset)
        it.requestFeature(Window.FEATURE_NO_TITLE)
    }
}