package com.madrid.presentation.screens.moreScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.component.DialogWithButtonLayout
import com.madrid.designSystem.component.EmptySearchLayout
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.movioCards.MovioRatingCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.detailsScreen.castDetails.LoadingScreen
import com.madrid.presentation.viewModel.myRateViewModel.MyRateUiState
import com.madrid.presentation.viewModel.myRateViewModel.MyRateViewModel
import com.madrid.presentation.viewModel.myRateViewModel.MyRatingEffect
import com.madrid.presentation.viewModel.myRateViewModel.MyRatingInteractionListener
import com.madrid.presentation.viewModel.shared.MediaType


@Composable
fun MyRatingScreen(
    viewModel: MyRateViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MyRatingEffect.NavigateBack -> {
                    navController.navigate(
                        Destinations.MoreScreen
                    )
                }

                is MyRatingEffect.NavigateToMediaDetails -> {
                    when (effect.mediaType) {
                        MediaType.MOVIE -> navController.navigate(
                            Destinations.MovieDetailsScreen(
                                movieId = effect.mediaId
                            )
                        )

                        MediaType.TV_SHOW -> navController.navigate(
                            Destinations.SeriesDetailsScreen(
                                seriesId = effect.mediaId,
                                seasonNumber = 1
                            )
                        )
                    }
                }
            }
        }
    }
    when {
        state.showLoadingScreen -> {
            LoadingScreen(message = stringResource(R.string.loading))
        }

        state.isError -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 32.dp)
            ) {

                TopAppBar(
                    text = stringResource(R.string.my_ratings),
                    secondIcon = null,
                    thirdIcon = null,
                    onFirstIconClick = viewModel::onBackClick
                )
                DialogWithButtonLayout(
                    title = stringResource(R.string.internet_is_not_available),
                    description = stringResource(R.string.please_make_sure_you_are_connected_to_the_internet_and_try_again),
                    image = R.drawable.img_no_internet,
                    imageSize = 150,
                    buttonText = stringResource(R.string.try_again),
                    onClick = { navController.navigate(Destinations.MyRatingScreen) },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }

        state.ratedMedia.isNotEmpty() -> {
            MyRatingScreenContent(
                state = state,
                interaction = viewModel as MyRatingInteractionListener,
                onBackClick = { viewModel.onBackClick() }
            )
        }

        state.ratedMedia.isEmpty() -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    TopAppBar(
                        text = stringResource(R.string.my_ratings),
                        secondIcon = null,
                        thirdIcon = null,
                        onFirstIconClick = { navController.popBackStack() },
                        modifier = Modifier.padding(16.dp)
                    )
                    EmptySearchLayout(
                        stringResource(R.string.no_ratings_yet),
                        description = stringResource(R.string.you_haven_t_rated_anything_yet_start_exploring_and_share_your_thoughts),
                        image = R.drawable.img_empty_more,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun MyRatingScreenContent(
    state: MyRateUiState,
    onBackClick: () -> Unit,
    interaction: MyRatingInteractionListener,
) {
    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .background(Theme.color.surfaces.surface)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TopAppBar(
            text = stringResource(R.string.my_ratings),
            secondIcon = null,
            thirdIcon = null,
            onFirstIconClick = { onBackClick() },
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surfaces.surface),
        ) {
            items(
                count = state.ratedMedia.size
            ) { index ->
                val Media = state.ratedMedia[index]
                MovioRatingCard(
                    movieTitle = Media.mediaTitle,
                    movieImageUrl = Media.imageUrl,
                    height = 100.dp,
                    rate = Media.rate,
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = {
                        interaction.onMediaClick(
                            mediaType = state.ratedMedia[index].mediaType,
                            mediaId = state.ratedMedia[index].mediaId
                        )
                    },
                )
            }
        }
    }
}