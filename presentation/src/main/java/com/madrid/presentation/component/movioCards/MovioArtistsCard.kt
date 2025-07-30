package com.madrid.presentation.component.movioCards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme


@Composable
fun MovioArtistsCard(
    imageUrl: String,
    artistsName: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .width(102.dp)
            .height(111.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicImageCard(
            modifier = modifier
                .size(88.dp),
            imageUrl = imageUrl,
            radius = 1000.dp,
        )
        MovioText(
            text = artistsName,
            color = Theme.color.surfaces.onSurface,
            textStyle = Theme.textStyle.body.mediumMedium14,
            maxLines = 1,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovioArtistsCardPreview() {
    MovioTheme {
        MovioArtistsCard(
            imageUrl = "https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
            artistsName = "Leonardo DiCaprio"
        )
    }
}