package com.madrid.presentation.screens.detailsScreen.similarMedia

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.presentation.R
import com.madrid.presentation.component.movioCards.MovioVerticalCard

data class SimilarMovie(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val rating: String
)

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
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomTextTitle(
                primaryText = stringResource(R.string.similar_movies),
                secondaryText = stringResource(R.string.see_all),
                endIcon = painterResource(com.madrid.designSystem.R.drawable.outline_alt_arrow_left),
                onSeeAllClick = { onSeeAllClick() },
                modifier = Modifier.padding(start = 16.dp, end = 16.dp , bottom = 12.dp)
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(similarMovies) { movie ->
                MovioVerticalCard(
                    description = movie.title,
                    movieImage = movie.imageUrl,
                    rate = movie.rating,
                    width = 124.dp,
                    imageHeight = 202.dp,
                    onClick = { onMovieClick(movie) },
                )
            }
        }
    }
}