package com.madrid.presentation.screens.detailsScreen.seriesDetails

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
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

@Composable
fun EpisodesScreen(viewModel: SeriesDetailsViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current
    val context = LocalContext.current
    EpisodesScreenContent(
        uiState = uiState,
        onSeasonSelection = viewModel::updateSelectedSeason,
        onClickEpisode = { episode ->
            viewModel.loadEpisodeTrailer(
                seriesId = uiState.seriesId,
                seasonNumber = uiState.selectedSeasonUiState.seasonNumber,
                episodeNumber = episode.episodeNumber
            ) { trailerKey ->
                trailerKey?.let {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        "https://www.youtube.com/watch?v=$it".toUri()
                    )
                    context.startActivity(intent)
                }
            }
        },
        onClickBack = { navController.popBackStack() }
    )
}

@Composable
fun EpisodesScreenContent(
    uiState: SeriesDetailsUiState,
    onSeasonSelection: (Int) -> Unit = {},
    onClickEpisode: (EpisodeUiState) -> Unit = {},
    onClickBack: () -> Unit = {},
) {
    val episodes: List<EpisodeUiState> = uiState.selectedSeasonUiState.episodesUiStates
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface)
    ) {
        item {
            Box {
                TopAppBar(
                    text = null,
                    secondIcon = null,
                    thirdIcon = null,
                    modifier = Modifier.padding(start = 16.dp, top = 36.dp, end = 16.dp),
                    onFirstIconClick = { onClickBack() }
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.color.surfaces.surface)
                ) {
                    MoviePosterDetailScreen(
                        imageUrl = uiState.topImageUrl,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .offset(y = 342.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Theme.color.surfaces.surface)
                                )
                            )
                    )
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                MovioText(
                    text = stringResource(
                        R.string.episodes,
                        uiState.selectedSeasonUiState.numberOfEpisodes.toString()
                    ),
                    textStyle = Theme.textStyle.headline.mediumMedium18,
                    color = Theme.color.surfaces.onSurface,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(Modifier.weight(1f))
                val seasonLabel = stringResource(
                    R.string.season,
                    uiState.selectedSeasonUiState.seasonNumber.toString()
                )
                var selectedItem by remember { mutableStateOf(seasonLabel) }
                val seasonNumbers = (1..uiState.currentSeasonsUiStates.size).toList()

                if (uiState.currentSeasonsUiStates.isNotEmpty()) {
                    CustomDropdown(
                        items = seasonNumbers.map { number ->
                            stringResource(R.string.season, number.toString())
                        },
                        selectedItem = stringResource(
                            R.string.season,
                            uiState.selectedSeasonUiState.seasonNumber.toString()
                        ),
                        labelSelector = { it },
                        onItemSelected = { selected ->
                            selectedItem = selected
                            onSeasonSelection(selected.substringAfterLast(" ").toInt())
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
                    R.string.episode_number,
                    episode.episodeNumber.toString()
                ),
                movieTime = "${episode.episodeDuration} m",
                movieImageUrl = episode.imageUrl,
                onClick = { onClickEpisode(episode) },
                modifier = Modifier.padding(bottom = 12.dp, start = 16.dp, end = 16.dp)
            )
        }
    }
}