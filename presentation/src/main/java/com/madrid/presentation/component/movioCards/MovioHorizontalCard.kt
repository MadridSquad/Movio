package com.madrid.presentation.component.movioCards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme

@Composable
fun MovioHorizontalCard(
    movieTitle: String,
    movieRate: String,
    movieCategory: String,
    movieImageUrl: String,
    modifier: Modifier = Modifier,
    height: Dp =100.dp,
    onClick: () -> Unit,
    contentScale: ContentScale = ContentScale.Crop
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Theme.color.surfaces.surface)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        BasicImageCard(
            imageUrl = movieImageUrl,
            modifier = Modifier
                .width(76.dp)
                .height(height),
            radius = 8.dp,
            contentScale = contentScale
        )
        Column(
            modifier = modifier
                .padding(bottom = 4.dp)
                .height(height),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            MovioText(
                text = movieTitle,
                color = Theme.color.surfaces.onSurface,
                textStyle = Theme.textStyle.title.mediumMedium14,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            RateIcon(rate = movieRate, modifier = Modifier.padding(bottom = 28.dp))
            MovioCategory(movieCategory, Theme.color.surfaces.onSurfaceAt3)
        }
    }
}

@Composable
private fun MovioCategory(
    movieCategory: String,
    backgroundColor: Color
) {
    Row(
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(60.dp))
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        MovioText(
            text = movieCategory,
            color = Theme.color.surfaces.onSurfaceVariant,
            textStyle = Theme.textStyle.label.smallRegular12,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun HorizontalCardPreview() {
    MovioTheme {
        MovioHorizontalCard(
            movieTitle = "Spider-Man: Homecoming",
            movieImageUrl = "https://image.tmdb.org/t/p/w500/5xKGk6q5g7mVmg7k7U1RrLSHwz6.jpg",
            movieRate = "3.0",
            height = 150.dp,
            movieCategory = "Action",
            onClick = {},
        )
    }
}