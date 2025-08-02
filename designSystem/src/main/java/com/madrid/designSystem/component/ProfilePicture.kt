package com.madrid.designSystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R

@Composable
fun ProfilePicture(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
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
        Image(
            painter = painterResource(id = image),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .align(Alignment.Center),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

    }
}

@Preview
@Composable
fun ProfilePicturePreview() {
    ProfilePicture(
        image = R.drawable.library_main_icon,
        size = 48.dp,
        modifier = Modifier.background(androidx.compose.ui.graphics.Color.LightGray)
    )
}