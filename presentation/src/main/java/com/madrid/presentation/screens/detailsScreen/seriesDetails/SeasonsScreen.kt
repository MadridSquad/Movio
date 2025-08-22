package com.madrid.presentation.screens.detailsScreen.seriesDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madrid.designSystem.component.TopAppBar
import com.madrid.presentation.R
import com.madrid.presentation.component.movioCards.MovioSeasonCard
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.detailsViewModel.SeasonUiState
import com.madrid.presentation.viewModel.detailsViewModel.seriesDetails.SeriesDetailsInteractionListener
import com.madrid.presentation.viewModel.detailsViewModel.seriesDetails.SeriesDetailsViewModel
import com.madrid.presentation.viewModel.shared.parser.formatFullDateKtx
import com.madrid.presentation.viewModel.shared.parser.formatYearKtx

@Composable
fun SeasonsScreen(viewModel: SeriesDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current
    val interactionListener =viewModel as SeriesDetailsInteractionListener

    SeasonsScreenContent(
        seasons = uiState.currentSeasonsUiStates,
        onClickBack = { navController.popBackStack() },
        onClickSeason = { seasonNumber ->

            interactionListener.onCurrentSeasonCardClick(seriesId = uiState.seriesId,seasonNumber)
        },
    )
}

@Composable
fun SeasonsScreenContent(
    seasons: List<SeasonUiState>,
    onClickBack: () -> Unit = {},
    onClickSeason: (Int) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        TopAppBar(
            text = stringResource(R.string.current_seasons),
            secondIcon = null, thirdIcon = null,
            onFirstIconClick = { onClickBack() },
            modifier = Modifier.padding(top = 36.dp),
        )

        LazyColumn(
            contentPadding = PaddingValues(vertical = 6.dp)
        ) {
            items(seasons) { season ->
                MovioSeasonCard(
                    movieTitle = season.title,
                    movieImage = season.imageUrl,
                    movieRate = season.rate,
                    totalNumberOfEpisodes = season.numberOfEpisodes.toString(),
                    onClick = { onClickSeason(season.seasonNumber) },
                    yearOfPublish = season.productionDate.formatYearKtx(),
                    timeOfPublish = season.productionDate.formatFullDateKtx(),
                    currentSeason = season.seasonNumber.toString(),
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }
    }
}