package com.madrid.presentation.screens.detailsScreen.castDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.movioCards.MovioArtistsCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.detailsViewModel.MovieDetailsUiState
import com.madrid.presentation.viewModel.detailsViewModel.TopCastViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopCastDetailsScreen(
    viewModel: TopCastViewModel = koinViewModel(),
) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current

    TopCastDetailsContent(
        artist = uiState.cast,
        onActorClick = { artistId ->
            navController.navigate(
                Destinations.ActorDetails(
                    artistId,
                )
            )
        },
        onBackClick = {
            navController.popBackStack()
        }
    )
}

@Composable
fun TopCastDetailsContent(
    artist: List<MovieDetailsUiState.CastUiState>,
    onActorClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface)
            .statusBarsPadding(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 0.dp,
            bottom = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            TopAppBar(
                stringResource(R.string.top_cast),
                secondIcon = null,
                thirdIcon = null,
                onFirstIconClick = { onBackClick() }
            )
        }
        items(artist.size) { index ->
            MovioArtistsCard(
                artistsName = artist[index].actorName,
                imageUrl = artist[index].actorImageUrl,
                onClick = { onActorClick(artist[index].id.toInt()) }
            )
        }
    }
}