package com.lessonmaker.app.shared.map


import androidx.compose.runtime.Composable
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun showMapPreview(oldLocation: LocationPicked?, callBack: (Unit) -> Unit) {


}


@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun mapKit(): LocationPickerInterface = object : LocationPickerInterface {

    override fun pickLocation(oldLocation: LocationPicked?, picked: (LocationPicked) -> Unit) {
       }

    override fun getCityName(oldLocation: LocationPicked, name: (String,String,String) -> Unit) {

    }

    override fun redirectToGoogleMaps(oldLocation: LocationPicked) {
    }

    override fun getCurrentLatLng(callBack: (LocationPicked) -> Unit) {

    }
}



