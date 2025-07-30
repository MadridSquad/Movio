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
import com.madrid.designSystem.component.CustomTextTitel
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.detectImageContent.FilteredImage
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
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomTextTitel(
                primaryText = stringResource(R.string.similar_movies),
                secondaryText = stringResource(R.string.see_all),
                endIcon = painterResource(com.madrid.designSystem.R.drawable.outline_alt_arrow_left),
                onSeeAllClick = { onSeeAllClick() },
                modifier = Modifier.padding(horizontal = 16.dp)
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
                    rate = movie.rating.toString(),
                    width = 124.dp,
                    height = 160.dp,
                    onClick = { onMovieClick(movie) },
                )
            }
        }
    }
}