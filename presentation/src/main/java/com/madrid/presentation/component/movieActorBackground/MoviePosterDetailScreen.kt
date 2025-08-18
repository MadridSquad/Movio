package com.madrid.presentation.component.movieActorBackground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MoviePosterDetailScreen(
    modifier: Modifier = Modifier,
    imageUrl: String,
    isActor: Boolean = false
) {
    val overlay = Color(0xCC181828)
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(372.dp)
                .align(Alignment.TopCenter)
        ) {
            BlurredBackgroundImage(imageUrl, contentScale = ContentScale.Crop)
            Box(
                Modifier
                    .matchParentSize()
                    .background(overlay)
            )
            if (isActor) {
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .align(Alignment.Center)
                        .clip(CircleShape)
                ) {
                    ActorOverlay(
                        actorImageUrl = imageUrl,
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 35.dp)
                        .size(width = 200.dp, height = 260.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Black.copy(alpha = 0.7f)),
                ) {
                    PosterCard(
                        posterImageUrl = imageUrl,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoviePosterDetailScreenPreview() {
    MoviePosterDetailScreen(
        imageUrl = "https://image.tmdb.org/t/p/original/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg"
    )
}

