package com.madrid.presentation.component.movioCards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.detectImageContent.FilteredImage
import com.madrid.presentation.R.string

@Composable
fun BasicImageCard(
    imageUrl: String,
    radius: Dp,
    contentScale: ContentScale = ContentScale.Crop,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        FilteredImage(
            imageUrl = imageUrl,
            contentDescription = stringResource(string.moive_image),
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(radius)),
            contentScale = contentScale
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BasicImageCardPreview() {
    MovioTheme {
        BasicImageCard(
            imageUrl = "https://image.tmdb.org/t/p/w500/5xKGk6q5g7mVmg7k7U1RrLSHwz6.jpg",
            8.dp
        )
    }
}