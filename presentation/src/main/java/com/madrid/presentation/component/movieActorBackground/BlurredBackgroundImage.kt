package com.madrid.presentation.component.movieActorBackground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme
import com.madrid.detectImageContent.FilteredImage

@Composable
fun BlurredBackgroundImage(
    posterImageUrl: String,
    blurRadius: Dp = 20.dp
) {
    val overlayColor = Theme.color.system.blur.copy(alpha = 0.5f)
    Box(
        modifier = Modifier.fillMaxSize()
            .background(overlayColor)
    ) {
        FilteredImage(
            imageUrl = posterImageUrl,
            modifier = Modifier
                .matchParentSize()
                .blur(blurRadius),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
} 