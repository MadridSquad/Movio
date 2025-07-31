package com.madrid.detectImageContent

import android.graphics.Bitmap

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.madrid.detectimagecontent.R


@Composable
fun FilteredImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    val context = LocalContext.current
    var image by remember { mutableStateOf<Bitmap?>(null) }
    var isNSFW by remember { mutableStateOf(false) }
    val getImage = remember(context) { GetImageBitmap(context) }
    val detection = remember(context) { SensitiveContentDetection(context) }

    if (imageUrl.isNotBlank()) {
        LaunchedEffect(imageUrl) {
            try {
                image = getImage.getImageBitmapFromUrl(imageUrl)
                isNSFW = detection.sensitiveContentDetection(image!!)
            } catch (_: Exception) {

            }
        }

        FilteredImageContent(
            image = image,
            isNSFW = isNSFW,
            contentDescription = contentDescription,
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    }
}

@Composable
fun FilteredImageContent(
    image: Bitmap?,
    isNSFW: Boolean,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = 1f,
    colorFilter: ColorFilter? = null
) {
    if (image == null) {
        Image(
            painter = painterResource(R.drawable.place_holder),
            contentDescription = contentDescription,
            modifier = modifier
                .size(300.dp)
                .then(if (isNSFW) Modifier.blur(16.dp) else Modifier),
            contentScale = contentScale,
            alignment = alignment,
            alpha = alpha,
            colorFilter = colorFilter
        )
    } else {
        Image(
            bitmap = image.asImageBitmap(),
            contentDescription = contentDescription,
            modifier = modifier
                .size(300.dp)
                .then(if (isNSFW) Modifier.blur(16.dp) else Modifier),
            contentScale = contentScale,
            alignment = alignment,
            alpha = alpha,
            colorFilter = colorFilter
        )
    }
}