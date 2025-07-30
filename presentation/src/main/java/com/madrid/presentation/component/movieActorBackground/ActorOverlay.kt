package com.madrid.presentation.component.movieActorBackground

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.madrid.detectImageContent.FilteredImage

@Composable
fun BoxScope.ActorOverlay(actorImageUrl: String) {
    Box(
        modifier = Modifier
            .size(160.dp)
            .align(Alignment.Center)
            .shadow(8.dp, CircleShape, clip = false)
            .clip(CircleShape)
    ) {
        FilteredImage(
            imageUrl = actorImageUrl,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
} 