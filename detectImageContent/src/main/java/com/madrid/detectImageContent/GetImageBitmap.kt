package com.madrid.detectImageContent

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import javax.inject.Inject

class GetImageBitmap @Inject constructor(
    private val context: Context
) {

    suspend fun getImageBitmapFromUrl(url: String): Bitmap {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .allowHardware(false)
            .build()

        val result = loader.execute(request)
        return if (result is SuccessResult) {
            (result.drawable as BitmapDrawable).bitmap
        } else {
            throw Exception("Failed to load image from URL: $url")
        }
    }
}