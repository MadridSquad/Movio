package com.madrid.presentation.screens.detailsScreen.seriesDetails

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
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
import com.madrid.presentation.utils.seriesBottomFade
import com.madrid.presentation.viewModel.detailsViewModel.EpisodeUiState
import com.madrid.presentation.viewModel.detailsViewModel.SeasonUiState
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetailsUiState
import com.madrid.presentation.viewModel.detailsViewModel.seriesDetails.SeriesDetailsInteractionListener
import com.madrid.presentation.viewModel.detailsViewModel.seriesDetails.SeriesDetailsViewModel

@Composable
fun EpisodesScreen(viewModel: SeriesDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val interactionListener = viewModel as SeriesDetailsInteractionListener
    val context = LocalContext.current

    EpisodesScreenContent(
        uiState = uiState,
        onClickEpisode = { episode ->

            interactionListener.onEpisodePlayItClick(
                seriesId = uiState.seriesId,
                seasonNumber = uiState.selectedSeasonUiState.seasonNumber,
                episodeNumber = episode.episodeNumber
            ) { trailerKey ->
                openTrailer(trailerKey, context)
            }
        },
        interactionListener = interactionListener,
    )
}

@Composable
private fun EpisodesScreenContent(
    uiState: SeriesDetailsUiState,
    interactionListener: SeriesDetailsInteractionListener,
    onClickEpisode: (EpisodeUiState) -> Unit = {},
    episodes: List<EpisodeUiState> = uiState.selectedSeasonUiState.episodesUiStates
) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface)
            .navigationBarsPadding()
    ) {
        item {
            SerirseHeaderSection(
                onBackButtonClick = { interactionListener.onBackButtonClick() },
                topImageUrl = uiState.topImageUrl
            )
        }
        item {
            Row(modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp)
            ) {
                NumberOfEpisodesTitle(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    numberOfEpisodes = uiState.selectedSeasonUiState.numberOfEpisodes.toString()
                )

                Spacer(Modifier.weight(1f))

                DropdownSeasonMenu(
                    seasonNumber = uiState.selectedSeasonUiState.seasonNumber.toString(),
                    currentSeasonsUiStates = uiState.currentSeasonsUiStates,
                    onSeasonSelection = interactionListener::updateSelectedSeason
                )
            }
        }

        items(episodes) { episode ->
            MovioEpisodesCard(
                movieTitle = episode.episodeName,
                movieRate = (episode.rate.toFloat() / 2).toString().take(3),
                currentMovieEpisode = stringResource(R.string.episode_number, episode.episodeNumber.toString()),
                movieTime = "${episode.episodeDuration} m",
                movieImageUrl = episode.imageUrl,
                onClick = { onClickEpisode(episode) },
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .padding( start = 16.dp, end = 16.dp)
            )
        }
    }
}


private fun openTrailer(trailerKey: String?, context: Context) {
    trailerKey?.let {
        val intent = Intent(
            Intent.ACTION_VIEW,
            "https://www.youtube.com/watch?v=$it".toUri()
        )
        context.startActivity(intent)
    }
}


@Composable
private fun SerirseHeaderSection(onBackButtonClick: () -> Unit, topImageUrl: String) {
    Box {
        TopAppBar(
            text = null,
            secondIcon = null, thirdIcon = null,
            modifier = Modifier.padding(start = 16.dp, top = 36.dp, end = 16.dp),
            onFirstIconClick = { onBackButtonClick() }
        )
        Box(
            modifier = Modifier.fillMaxSize().background(Theme.color.surfaces.surface)
        ) {
            MoviePosterDetailScreen(imageUrl = topImageUrl, modifier = Modifier.fillMaxSize())

            Box(modifier = Modifier.seriesBottomFade())
        }
    }
}


@Composable
private fun DropdownSeasonMenu(
    seasonNumber: String,
    currentSeasonsUiStates: List<SeasonUiState>,
    onSeasonSelection: (Int) -> Unit = {}
) {
    val seasonLabel = stringResource(R.string.season, seasonNumber)

    var selectedItem by remember { mutableStateOf(seasonLabel) }
    val seasonNumbers = (1..currentSeasonsUiStates.size).toList()

    if (currentSeasonsUiStates.isNotEmpty()) {

        CustomDropdown(
            items = seasonNumbers.map { number ->
                stringResource(R.string.season, number.toString())
            },
            selectedItem = stringResource(R.string.season, seasonNumber),
            labelSelector = { it },
            onItemSelected = { selected ->
                selectedItem = selected
                onSeasonSelection(selected.substringAfterLast(" ").toInt())
            }
        )
    }
}

@Composable
private fun NumberOfEpisodesTitle(modifier: Modifier = Modifier,numberOfEpisodes:String) {
    MovioText(
        text = stringResource(R.string.episodes, numberOfEpisodes),
        textStyle = Theme.textStyle.headline.mediumMedium18,
        color = Theme.color.surfaces.onSurface,
        modifier = modifier
            .padding(vertical = 5.dp)
    )
}