package com.madrid.presentation.component.movioCards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.madrid.designSystem.component.ImageViewer
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme

@Composable
fun MovioVerticalCard(
    description: String,
    movieImage: String,
    rate: String,
    width: Dp? = null,
    imageHeight: Dp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .then(if (width != null) Modifier.width(width) else Modifier.fillMaxWidth())
                .height(imageHeight)
                .padding(bottom = 8.dp)
        ) {
            ImageViewer(
                model = movieImage,
                contentDescription = description,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillBounds,
            )
            RateIcon(
                rate = rate,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .zIndex(1f)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Theme.color.system.startColorForCardShadow,
                                Theme.color.system.endColorForCardShadow
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
        }

        MovioText(
            text = description,
            textStyle = Theme.textStyle.title.mediumMedium14,
            color = Theme.color.surfaces.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = if (width != null) Modifier.width(width) else Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun VerticalCardPreview() {
    MovioTheme {
        MovioVerticalCard(
            description = "Spider-Man ",
            movieImage = "https://image.tmdb.org/t/p/w500/5xKGk6q5g7mVmg7k7U1RrLSHwz6.jpg",
            width = 200.dp,
            imageHeight = 150.dp,
            onClick = {},
            rate = "4.0",
        )
    }
}