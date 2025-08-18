package com.madrid.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.madrid.designSystem.R

@Composable
fun ProfilePicture(
    modifier: Modifier = Modifier,
    image: String? = null,
    size: Dp,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(shape = CircleShape)
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        androidx.compose.ui.graphics.Color(0xFF724CF8),
                        androidx.compose.ui.graphics.Color(0xFF432D92)
                    )
                )
            )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = "Profile Picture",
            placeholder = painterResource(R.drawable.bold_profile_circle),
            error = painterResource(R.drawable.bold_profile_circle),
            modifier = Modifier
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
private fun ProfilePicturePreview() {
    ProfilePicture(
        image = null,
        size = 48.dp,
        modifier = Modifier.background(androidx.compose.ui.graphics.Color.LightGray)
    )
}