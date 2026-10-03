package com.lessonmaker.app.shared.map

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.asFlow
import com.lessonmaker.app.base.BaseActivity
import com.lessonmaker.app.extensionFunctions.makeNull
import com.lessonmaker.app.shared.map.LocationPicked
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


//@Composable
//actual fun mapKit(): LocationPickerInterface = object : LocationPickerInterface {
//    val context = LocalContext.current
//    var picked: ((LocationPicked) -> Unit)?=null
//
//    override fun pickLocation(oldLocation: LocationPicked?, picked: (LocationPicked) -> Unit) {
//        this.picked = picked
//        launchAddress.launch(Intent(OnlyLocationPickerActivity.getEditIntent(context, pickedLocation = oldLocation)))
//    }
//    val launchAddress = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
//        result.data?.extras.handleEmpty().let {
//            it.convertToModel<LocationPicked>("pickedLocation")?.let {
//                picked?.invoke(it)
//            }
//        }
//    }
//
//    override fun getCityName(oldLocation: LocationPicked, name: (String,String,String) -> Unit) {
//        context.findActivity()?.let {
//            oldLocation.getLatitudeLongitude()?.let { latLng->
//                it.getCountryCode(latLng = latLng) {
//                    name.invoke(it.address.handleNull(),it.locationCode.handleNull(),it.cityName.handleNull())
//                }
//            }
//        }
//    }
//
//    override fun redirectToGoogleMaps(oldLocation: LocationPicked) {
//        oldLocation.getLatitudeLongitude()?.let {
//            val gmmIntentUri = Uri.parse("google.navigation:q=${it.latitude},${it.longitude}")
//            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//            mapIntent.setPackage("com.google.android.apps.maps")
//            context.startActivity(mapIntent)
//        }
//    }
//
//    override fun getCurrentLatLng(callBack:(LocationPicked)->Unit) {
//
//    }
//
//
//
//}
//@Composable
//fun getResizedBitmapDescriptor(@DrawableRes id: Int, context: Context, width: Int, height: Int): BitmapDescriptor? {
//    val vectorDrawable = ContextCompat.getDrawable(context, id) ?: return null
//    val bitmap = Bitmap.createBitmap(
//        width,
//        height,
//        Bitmap.Config.ARGB_8888
//    )
//    val canvas = Canvas(bitmap)
//    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
//    vectorDrawable.draw(canvas)
//    return BitmapDescriptorFactory.fromBitmap(bitmap)
//}
//@Composable
//actual fun showMapPreview(oldLocation: LocationPicked?, callBack: (Unit) -> Unit) {
//    val context = LocalContext.current
//    var mapReady by remember { mutableStateOf(false) }
//    var rememberCurrentLocation by remember { mutableStateOf<LatLng?>(null) }
//
//    var markerPosition by remember { mutableStateOf(oldLocation?.getLatitudeLongitude() ?: LatLng(0.0, 0.0)) }
//
//
//    val cameraPositionState = rememberCameraPositionState {
//        oldLocation?.getLatitudeLongitude()?.let {
//            position = CameraPosition.fromLatLngZoom(it, 12f)
//        }
//    }
//
//    // Move Camera to Current Location if `oldLocation` is null
//    LaunchedEffect(oldLocation) {
//        delay(1000)
//        mapReady = true
//        delay(1000)
//        oldLocation?.getLatitudeLongitude()?.let {
//            cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(it, 15f))
//            markerPosition = it
//        }
//        if (oldLocation == null) {
//            context.findActivity()?.let {  baseActivity->
//                baseActivity.locationViewModel.checkForLocationPermission()
//                baseActivity.locationViewModel.userLocationUpdate.asFlow().collectLatest {
//                    it?.let {
//                        Log.e("NewLocation",Gson().toJson(it))
//                        rememberCurrentLocation = it
//                        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(it, 15f))
//                        markerPosition = it
//                    }
//                }
//            }
//        }
//    }
//
//    val markerState = remember { MarkerState(markerPosition) }
//
//    LaunchedEffect(markerPosition) {
//        markerState.position = markerPosition
//    }
//
//    if (mapReady) {
//        GoogleMap(
//            modifier = Modifier.fillMaxSize(),
//            cameraPositionState = cameraPositionState,
//            onMapClick = { callBack.invoke(Unit) },
//            uiSettings = MapUiSettings(
//                zoomControlsEnabled = false,
//                zoomGesturesEnabled = true,
//                scrollGesturesEnabled = true,
//                compassEnabled = false
//            )
//        ) {
//            val customIcon = getResizedBitmapDescriptor(R.drawable.map_pin, context, 100, 100)
//
//            Marker(
//                state = markerState,
//                title = "Current Location",
//                icon = customIcon,
//                onClick = {
//                    callBack.invoke(Unit)
//                    return@Marker true
//                }
//            )
//        }
//    }
//}
// Extension function to find the Activity from a Context
fun Context.findActivity(): BaseActivity? = when (this) {
    is BaseActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}


fun convertToLatLng(lat: String?,lng: String?):LatLng{
    try {
        var doubleLat=lat?.makeNull()?.toDoubleOrNull()
        var doubleLng=lng?.makeNull()?.toDoubleOrNull()

        if (doubleLat==null){
            doubleLat=0.0
        }
        if (doubleLng==null){
            doubleLng=0.0
        }
        return LatLng(doubleLat,doubleLng)
    }catch (e:Exception){
        e.printStackTrace()
        return LatLng(0.0,0.0)
    }
}