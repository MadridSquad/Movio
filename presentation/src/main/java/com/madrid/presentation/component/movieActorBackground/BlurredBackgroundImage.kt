package com.madrid.presentation.component.movieActorBackground

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.madrid.image_viewer.safe_image_viewer.ImageViewer

@Composable
fun BlurredBackgroundImage(
    posterImageUrl: String,
    blurRadius: Dp = 16.dp
) {
    Box(Modifier.fillMaxSize()) {
        ImageViewer(
            model = posterImageUrl,
            modifier = Modifier
                .fillMaxSize()
                .blur(blurRadius),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
} 