package com.madrid.presentation.screens.detailsScreen.castDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.movioCards.MovioArtistsCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.detailsViewModel.actor.NetworkDetailsUiState
import com.madrid.presentation.viewModel.detailsViewModel.topcast.TopCastViewModel

@Composable
fun TopCastDetailsScreen(
    viewModel: TopCastViewModel = hiltViewModel(),
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
    artist: List<NetworkDetailsUiState.CastUiState>,
    onActorClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopAppBar(
            stringResource(R.string.top_cast),
            secondIcon = null,
            thirdIcon = null,
            modifier = Modifier.padding(start = 16.dp, top = 32.dp, end = 16.dp),
            onFirstIconClick = { onBackClick() }
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(101.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surfaces.surface),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(artist.size) { index ->
                MovioArtistsCard(
                    modifier = Modifier.size(width = 101.dp, height = 111.dp),
                    artistsName = artist[index].actorName,
                    paddingBetweenImageAndText = 8.dp,
                    imageUrl = artist[index].actorImageUrl,
                    onClick = { onActorClick(artist[index].id.toInt()) },
                    circleImageSize = 88.dp
                )
            }
        }
    }
}