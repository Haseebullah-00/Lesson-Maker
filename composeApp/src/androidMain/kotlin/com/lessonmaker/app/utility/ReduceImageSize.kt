package com.lessonmaker.app.utility

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import id.zelory.compressor.Compressor
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object ReduceImageSize {
    fun compressImage(old: Uri, inContext: Context): Uri {
        try {
            val image = BitmapFactory.decodeStream(inContext.contentResolver.openInputStream(old))
            val baos = ByteArrayOutputStream()
            image.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                baos
            ) //Compression quality, here 100 means no compression, the storage of compressed data to baos
            var options = 90
            if (baos.toByteArray().size > 6000000) {
                options = 70
            }
            if (baos.toByteArray().size > 4000000) {
                options = 80
            }
            Log.i("oldSize", baos.toByteArray().size.toString())
            while (baos.toByteArray().size / 1024 > 1800 && options > 10) {  //Loop if compressed picture is greater than 400kb, than to compression
                baos.reset() //Reset baos is empty baos
                image.compress(
                    Bitmap.CompressFormat.JPEG,
                    options,
                    baos
                ) //The compression options%, storing the compressed data to the baos
                options -= 10 //Every time reduced by 10
            }
            Log.i("oldSizeNew", baos.toByteArray().size.toString())
            val isBm = ByteArrayInputStream(baos.toByteArray()) //The storage of compressed data in the baos to ByteArrayInputStream
            val bitmap = BitmapFactory.decodeStream(
                isBm,
                null,
                null
            ) //The ByteArrayInputStream data generation

            var uri=bitmap?.saveImageToGallery(context = inContext)
            uri?.let {
                return uri
            }
            return old
        } catch (e: Exception) {
            e.printStackTrace()
            return old
        }
    }
    suspend fun compressImageNew(old: File, inContext: Context): File {
        try {
            val compressedImageFile = Compressor.compress(inContext, old)
            return compressedImageFile
        } catch (e: Exception) {
            e.printStackTrace()
            return old
        }
    }
    fun saveFromBitMap(old: Bitmap?, inContext: Context): Uri? {
        try {
           return old?.saveImageToGallery(context = inContext)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
fun Bitmap.saveImageToGallery(context: Context): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_" + SimpleDateFormat("hh_mm_ss_a", Locale.ENGLISH).format(
            Calendar.getInstance().time))
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
    }

    val uri: Uri? = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let {
        context.contentResolver.openOutputStream(it)?.use { outputStream ->
            this.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
    }

    return uri
}