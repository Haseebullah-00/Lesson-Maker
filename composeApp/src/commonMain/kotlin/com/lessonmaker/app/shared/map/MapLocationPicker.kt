package com.lessonmaker.app.shared.map

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

interface LocationPickerInterface{
    fun pickLocation(oldLocation: LocationPicked?=null, picked: (LocationPicked)->Unit)
    fun getCityName(oldLocation: LocationPicked,name:(String,String,String)->Unit)
    fun redirectToGoogleMaps(oldLocation: LocationPicked)
    fun getCurrentLatLng(callBack:(LocationPicked)->Unit)
}
//@Composable
//expect fun mapKit(): LocationPickerInterface
//
//@Composable
//expect fun showMapPreview(oldLocation: LocationPicked?,callBack:(Unit)->Unit)
@Serializable
data class LocationPicked(val lat:String,val lng:String,val address:String?="",val locationCode:String?="",val cityName:String?="")
