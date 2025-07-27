package com.madrid.presentation.screens.detailsScreen.similarMedia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.detailsViewModel.SimilarMediaUiState
import com.madrid.presentation.viewModel.detailsViewModel.SimilarMediaViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeeAllSimilarMediaScreen(
    viewModel: SimilarMediaViewModel = koinViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current

    SeeAllSimilarMediaScreenContent(uiState, onClickBack = { navController.popBackStack() }, onClickMedia = { id,isMovie ->
        if (!isMovie) navController.navigate(Destinations.SeriesDetailsScreen(id,1))
        else navController.navigate(Destinations.MovieDetailsScreen(id))
    })
}

@Composable
fun SeeAllSimilarMediaScreenContent(
    uiState: SimilarMediaUiState,
    onClickBack: () -> Unit = {},
    onClickMedia: (Int, Boolean) -> Unit = { _, _ -> }
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        TopAppBar(
            text = uiState.headerName,
            modifier = Modifier.padding(start = 16.dp, top = 36.dp),
            onFirstIconClick = { onClickBack() }
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surfaces.surface),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(count = uiState.medias.size) { index ->
                val media = uiState.medias[index]
                MovioVerticalCard(
                    modifier = Modifier.fillMaxWidth(fraction = 0.50f),
                    description = media.mediaName,
                    movieImage = media.imageUrl,
                    rate = media.rate,
                    width = 101.dp,
                    height = 136.dp,
                    onClick = {
                        onClickMedia(media.mediaId,uiState.isMovie)
                    }
                )
            }
        }

    }

}