package com.madrid.presentation.screens.detailsScreen.similarMedia

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.CustomTextTitel
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.movioCards.MovioVerticalCard

data class SimilarSeries(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val rating: Double,
)

@Composable
fun SimilarSeriesSection(
    similarSeries: List<SimilarSeries>,
    modifier: Modifier = Modifier,
    onSeeAllClick: () -> Unit = {},
    onSeriesClick: (SimilarSeries) -> Unit = {}
) {
    Log.d("SimilarSeriesSection", "SimilarSeriesSection: $similarSeries")
    Column(modifier = modifier) {

        CustomTextTitel(
            primaryText = stringResource(R.string.similar_series),
            secondaryText = stringResource(R.string.see_all),
            endIcon = painterResource(com.madrid.designSystem.R.drawable.outline_alt_arrow_left),
            onSeeAllClick = { onSeeAllClick() },
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(similarSeries) { series ->
                SeriesCard(
                    series = series,
                    onClick = { onSeriesClick(series) }
                )
            }
        }
    }
}

@Composable
private fun SeriesCard(
    series: SimilarSeries,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d("SeriesCard", "SeriesCard: $series")
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
            MovioVerticalCard(
                description = series.title,
                movieImage = series.imageUrl,
                rate = series.rating.toString(),
                width = 124.dp,
                height = 160.dp,
                onClick = onClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SimilarMoviesSectionPreview() {
    val fakeMovies = listOf(
        SimilarSeries(
            id = 1,
            title = "Spider-Man: Into the Spider-Verse",
            imageUrl = "https://image.tmdb.org/t/p/w500/iiZZdoQBEYBv6id8su7ImL0oCbD.jpg",
            rating = 4.8
        ),
        SimilarSeries(
            id = 2,
            title = "The Dark Knight",
            imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            rating = 5.0
        ),
        SimilarSeries(
            id = 3,
            title = "Grave of the Fireflies",
            imageUrl = "https://image.tmdb.org/t/p/w500/qG3RYlIVpTYclR9TYIsy8p7m7AT.jpg",
            rating = 4.7
        )
    )
    MovioTheme {
        Box(
            modifier = Modifier.background(Theme.color.surfaces.surfaceContainer)
        ) {
            SimilarSeriesSection(similarSeries = fakeMovies)
        }
    }
}