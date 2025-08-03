package com.madrid.presentation.screens.detailsScreen.seriesDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.CustomDropdown
import com.madrid.presentation.component.movieActorBackground.MoviePosterDetailScreen
import com.madrid.presentation.component.movioCards.MovioEpisodesCard
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.detailsViewModel.EpisodeUiState
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetailsUiState
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EpisodesScreen(viewModel: SeriesDetailsViewModel = koinViewModel()) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current
    EpisodesScreenContent(
        uiState,
        viewModel::updateSelectedSeason,
        onClickBack = { navController.popBackStack() })
}

@Composable
fun EpisodesScreenContent(
    uiState: SeriesDetailsUiState,
    onSeasonSelection: (Int) -> Unit = {},
    onClickBack: () -> Unit = {},
) {
    val episodes: List<EpisodeUiState> = uiState.selectedSeasonUiState.episodesUiStates
    LazyColumn {
        item {
            Box {
                TopAppBar(
                    text = null,
                    secondIcon = null,
                    thirdIcon = null,
                    modifier = Modifier.padding(start = 24.dp, top = 12.dp),
                    onFirstIconClick = { onClickBack() }
                )
                MoviePosterDetailScreen(
                    imageUrl = uiState.selectedSeasonUiState.imageUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Theme.color.surfaces.surface)
                )
            }
        }
        item {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                MovioText(
                    text = stringResource(
                        id = R.string.episodes_count,
                        uiState.selectedSeasonUiState.numberOfEpisodes
                    ),
                    textStyle = Theme.textStyle.headline.mediumMedium18,
                    color = Theme.color.surfaces.onSurface,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(Modifier.weight(1f))
                var selectedItem by remember { mutableStateOf("Season ${uiState.selectedSeasonUiState.seasonNumber}") }
                if (uiState.currentSeasonsUiStates.isNotEmpty()) {
                    CustomDropdown(
                        items = getSeasonsNames(uiState.currentSeasonsUiStates.size, uiState),
                        selectedItem = "Season ${uiState.selectedSeasonUiState.seasonNumber}",
                        labelSelector = { it },
                        onItemSelected = {
                            selectedItem = it
                            onSeasonSelection(it.substringAfterLast(" ").toInt())
                        }
                    )
                }
            }
        }
        items(episodes) { episode ->
            MovioEpisodesCard(
                movieTitle = episode.episodeName,
                movieRate = (episode.rate.toFloat() / 2).toString().take(3),
                currentMovieEpisode = stringResource(
                    id = R.string.episode_number,
                    episode.episodeNumber
                ),
                movieTime = stringResource(
                    id = R.string.episode_duration,
                    episode.episodeDuration
                ),
                movieImageUrl = episode.imageUrl,
                onClick = {
                },
                modifier = Modifier.padding(bottom = 12.dp, start = 16.dp, end = 16.dp)
            )
        }
    }
}


private fun getSeasonsNames(numberOfSeasons: Int, uiState: SeriesDetailsUiState): List<String> {
    return if (uiState.currentSeasonsUiStates.first().seasonNumber == 0)
        (0..<numberOfSeasons).map { "Season $it" }
    else
        (1..<numberOfSeasons + 1).map { "Season $it" }
}




