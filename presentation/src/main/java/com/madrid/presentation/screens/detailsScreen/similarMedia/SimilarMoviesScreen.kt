package com.madrid.presentation.screens.detailsScreen.similarMedia

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme
import com.madrid.detectImageContent.FilteredImage
import com.madrid.presentation.R
import java.text.DecimalFormat

data class SimilarMovie(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val rating: Double
)

fun Double.toOneDecimalPlace(): String {
    return try {
        val truncated = Math.floor(this * 10) / 10
        DecimalFormat("#.#").format(truncated)
    } catch (e: Exception) {
        this.toString()
    }
}

@Composable
fun SimilarMoviesSection(
    similarMovies: List<SimilarMovie>,
    modifier: Modifier = Modifier,
    onSeeAllClick: () -> Unit = {},
    onMovieClick: (SimilarMovie) -> Unit = {}
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MovioText(
                text = stringResource(id = R.string.similar_movies),
                color = Theme.color.surfaces.onSurface,
                textStyle = Theme.textStyle.headline.mediumMedium18
            )

            MovioText(
                text = stringResource(id = R.string.see_all),
                color = Theme.color.surfaces.onSurfaceVariant,
                textStyle = Theme.textStyle.label.smallRegular14,
                modifier = Modifier.clickable(onClick = onSeeAllClick)
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(similarMovies) { movie ->
                MovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie) }
                )
            }
        }
    }
}

@Composable
private fun MovieCard(
    movie: SimilarMovie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(124.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        ) {
            FilteredImage(
                imageUrl = movie.imageUrl,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Theme.color.surfaces.surfaceContainer.copy(alpha = 0.7f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .align(Alignment.TopStart)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MovioIcon(
                        painter = painterResource(id = com.madrid.designSystem.R.drawable.bold_star),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = Theme.color.system.warning
                    )
                    MovioText(
                        text = movie.rating.toOneDecimalPlace(),
                        color = Theme.color.surfaces.onSurface,
                        textStyle = Theme.textStyle.label.smallRegular12
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        MovioText(
            text = movie.title,
            color = Theme.color.surfaces.onSurface,
            textStyle = Theme.textStyle.label.smallRegular12,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
        )
    }
}