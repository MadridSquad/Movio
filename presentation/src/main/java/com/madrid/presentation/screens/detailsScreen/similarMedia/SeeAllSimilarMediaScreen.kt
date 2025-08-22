package com.madrid.presentation.screens.detailsScreen.similarMedia

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.detailsViewModel.similarMedia.SimilarMediaViewModel

@Composable
fun SeeAllSimilarMediaScreen(
    viewModel: SimilarMediaViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current
    val similarMovies = uiState.medias.map { media ->
        Log.d(
            "SimilarMediaDebug",
            "Media ID: ${media.mediaId}, " +
                    "Title: ${media.mediaName}, " +
                    "Rating: ${media.rate}, " +
                    "IsMovie: ${media.isMovie}, " +
                    "Image: ${media.imageUrl.take(20)}..."
        )

        SimilarMovie(
            id = media.mediaId,
            title = media.mediaName,
            imageUrl = media.imageUrl,
            rating = media.rate,
        )
    }

    SeeAllSimilarMediaScreenContent(
        similarMovies = similarMovies,
        headerName = uiState.headerName,
        isMovie = uiState.isMovie,
        onClickBack = { navController.popBackStack() },
        onClickMedia = { id, isMovie ->
            Log.d(
                "SimilarMediaDebug",
                "Navigating to ${if (isMovie) "movie" else "series"} details for ID: $id"
            )
            if (isMovie) {
                navController.navigate(Destinations.MovieDetailsScreen(id))
            } else {
                navController.navigate(Destinations.SeriesDetailsScreen(id, 1))
            }
        }
    )
}

@Composable
fun SeeAllSimilarMediaScreenContent(
    similarMovies: List<SimilarMovie>,
    headerName: String,
    isMovie: Boolean,
    onClickBack: () -> Unit = {},
    onClickMedia: (Int, Boolean) -> Unit = { _, _ -> }
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            text = headerName,
            secondIcon = null,
            thirdIcon = null,
            modifier = Modifier.padding(start = 16.dp, top = 32.dp, end = 16.dp),
            onFirstIconClick = { onClickBack() }
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 101.33.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.color.surfaces.surface)
                .navigationBarsPadding(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(similarMovies.size) { index ->
                val movie = similarMovies[index]
                MovioVerticalCard(
                    description = movie.title,
                    movieImage = movie.imageUrl,
                    rate = movie.rating,
                    imageHeight = 136.dp,
                    onClick = {
                        onClickMedia(movie.id, isMovie)
                    },
                )
            }
        }
    }
}