package com.madrid.presentation.component.movioCards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme

@Composable
fun TrendingMovieCard(
    imgUrl: String,
    movieTitle: String,
    movieCategory: String,
    rating: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(100.dp)
            .width(240.dp)
    ) {

        AsyncImage(
            model = imgUrl,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxHeight()
                .width(76.dp),
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.bold_heart),
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
        ) {
            Column {
                Text(
                    text = movieTitle,
                    color = Theme.color.surfaces.onSurface,
                    style = Theme.textStyle.title.mediumMedium14,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MovioIcon(
                        painter = painterResource(R.drawable.bold_star),
                        contentDescription = "Default Image",
                        tint = Theme.color.system.warning,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = rating,
                        color = Theme.color.system.onWarning,
                        style = Theme.textStyle.label.smallRegular12
                    )

                }
                if (movieCategory.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .height(24.dp)
                            .background(
                                color = Theme.color.surfaces.onSurfaceAt3,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = movieCategory,
                            color = Theme.color.surfaces.onSurfaceVariant,
                            style = Theme.textStyle.label.smallRegular12
                        )
                    }
                }
            }

        }
    }
}

@Preview
@Composable
private fun TrendingMovieCardPrevNight() {
    MovioTheme(isDarkTheme = true) {
        TrendingMovieCard(
            "",
            movieTitle = "Ocean with David Attenborough",
            movieCategory = "Documentary",
            rating = "4.5"
        )
    }
}

@Preview
@Composable
private fun TrendingMovieCardPrevLight() {
    MovioTheme(isDarkTheme = false) {
        TrendingMovieCard(
            "",
            movieTitle = "Ocean with David Attenborough",
            movieCategory = "Documentary",
            rating = "4.5"
        )
    }
}