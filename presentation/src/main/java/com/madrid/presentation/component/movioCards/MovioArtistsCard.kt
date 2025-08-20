package com.madrid.presentation.component.movioCards

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.ImageViewer
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme

@Composable
fun MovioArtistsCard(
    imageUrl: String,
    circleImageSize: Dp,
    artistsName: String,
    paddingBetweenImageAndText: Dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageViewer(
            contentScale = ContentScale.Crop,
            model = imageUrl,
            contentDescription = artistsName,
            modifier = Modifier
                .size(circleImageSize)
                .clip(RoundedCornerShape(1000.dp)),
        )
        MovioText(
            text = artistsName,
            color = Theme.color.surfaces.onSurface,
            textStyle = Theme.textStyle.body.mediumMedium14,
            maxLines = 1,
            modifier = Modifier.padding(top = paddingBetweenImageAndText)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovioArtistsCardPreview() {
    MovioTheme {
        MovioArtistsCard(
            imageUrl = "https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
            artistsName = "Leonardo DiCaprio",
            circleImageSize = 88.dp,
            paddingBetweenImageAndText = 8.dp
        )
    }
}