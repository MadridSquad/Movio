package com.madrid.presentation.screens.detailsScreen.seriesDetails

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.madrid.designSystem.component.DialogWithButtonLayout
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.BottomMediaActions
import com.madrid.presentation.component.header.SeriesDetailsHeader
import com.madrid.presentation.component.movieActorBackground.MoviePosterDetailScreen
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.addtolist.ListManagementBottomSheet
import com.madrid.presentation.screens.detailsScreen.castDetails.LoadingScreen
import com.madrid.presentation.screens.detailsScreen.reviewsScreen.composables.ReviewScreen
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.AddRatingBottomSheet
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.CurrentSeasonsSection
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.DescriptionSection
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.DoneAddRating
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.GussetView
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.ShareBottomSheet
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.SimilarSeriesHorizontalSection
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.TopCastSection
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.copyToClipboard
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.playSeriesTrailer
import com.madrid.presentation.viewModel.detailsViewModel.ReviewUiState
import com.madrid.presentation.viewModel.detailsViewModel.ReviewsScreenUiState
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsEffect
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsInteractionListener
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsUiState
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsViewModel
import com.madrid.presentation.viewModel.libraryViewModel.addtolist.MovieListViewModel

@Composable
fun SeriesDetailsScreen(
    viewModel: SeriesDetailsViewModel = hiltViewModel(),
    addToListViewModel: MovieListViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val interactionListener = viewModel as SeriesDetailsInteractionListener
    val navController = LocalNavController.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SeriesDetailsEffect.NavigateBack -> {
                    navController.popBackStack()
                }

                is SeriesDetailsEffect.NavigateToSeriesDetails -> {
                    navController.navigate(Destinations.SeriesDetailsScreen(effect.seriesId, 1))
                }

                is SeriesDetailsEffect.NavigateToActorDetails -> {
                    navController.navigate(Destinations.ActorDetails(effect.actorId))
                }

                is SeriesDetailsEffect.NavigateToEpisodesScreen -> {
                    navController.navigate(
                        Destinations.EpisodesScreen(
                            seriesId = effect.seriesId,
                            seasonNumber = effect.seasonNumber
                        )
                    )
                }

                is SeriesDetailsEffect.NavigateToAuthenticationScreen -> {
                    navController.navigate(Destinations.AuthenticationScreen)
                }
            }
        }
    }

    when {
        uiState.showLoadingScreen -> {
            LoadingScreen(message = stringResource(R.string.loading))
        }

        uiState.isError -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.dp),
                contentAlignment = Alignment.Center
            ) {
                DialogWithButtonLayout(
                    title = stringResource(R.string.internet_is_not_available),
                    description = stringResource(R.string.please_make_sure_you_are_connected_to_the_internet_and_try_again),
                    image = R.drawable.img_no_internet,
                    buttonText = stringResource(R.string.try_again),
                    onClick = {
                        interactionListener.onRetryClick()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .padding(16.dp)
                )
            }
        }

        else -> {
            SeriesDetailsScreenContent(
                context = context,
                uiState = uiState,
                interactionListener = interactionListener,
                viewModel = viewModel,
                navController = navController,
                addToListViewModel = addToListViewModel
            )
        }
    }
}

@Composable
private fun SeriesDetailsScreenContent(
    uiState: SeriesDetailsUiState,
    context: Context,
    interactionListener: SeriesDetailsInteractionListener,
    viewModel: SeriesDetailsViewModel,
    navController: NavHostController,
    addToListViewModel: MovieListViewModel
) {
    ShareBottomSheet(
        copyToClipboard = { text -> copyToClipboard(text, context) },
        context = context,
        uiState = uiState,
        interactionListener = interactionListener
    )
    Box(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
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
        TopAppBar(
            text = null,
            modifier = Modifier.padding(start = 16.dp, top = 36.dp, end = 16.dp),
            onFirstIconClick = { interactionListener.onBackClick() },
            onSecondIconClick = { interactionListener.onShareShareBottomSheetClick() },
            onThirdIconClick = { interactionListener.onFavoriteClick(uiState.seriesId) },
            isFavorite = uiState.isFavourite
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp)
        ) {
            Spacer(modifier = Modifier.height(360.dp))
            SeriesDetailsHeader(
                movieName = uiState.seriesName,
                seriesCategory = uiState.seriesGenre,
                date = uiState.productionDate,
                time = stringResource(
                    id = R.string.season_count,
                    uiState.numberOfSeasons.toString()
                ),
                rate = uiState.rate.take(3),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            BottomMediaActions(
                onRateClick = {
                    interactionListener.onShowAddRatingBottomSheetClick()
                },
                onPlayClick = {
                    interactionListener.onPlayItClick()
                    playSeriesTrailer(context, uiState)
                },
                onAddToListClick = { interactionListener.onShowAddToListBottomSheet() },
                modifier = Modifier.padding(vertical = 16.dp)
            )

            MovioBottomSheet(
                show = uiState.showAddRatingBottomSheet,
                onDismiss = { interactionListener.onDismissAddRatingBottomSheet() },
                content = {
                    if (uiState.isGuest) {
                        GussetView(uiState, interactionListener)
                    } else {
                        AddRatingBottomSheet(uiState, viewModel, interactionListener)
                    }
                }
            )
            DoneAddRating(
                uiState = uiState,
                interactionListener = interactionListener
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            DescriptionSection(
                uiState = uiState
            )
            TopCastSection(
                uiState = uiState, navController = navController,
                interactionListener = interactionListener
            )
            CurrentSeasonsSection(
                navController = navController, uiState = uiState,
                interactionListener = interactionListener
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (uiState.reviews.isNotEmpty()) {
                ReviewScreen(
                    onSeeAllReviews = {
                        navController.navigate(
                            Destinations.ReviewsScreen(
                                uiState.seriesId,
                                isMovie = false
                            )
                        )
                    },
                    uiState = uiState.reviews.toReviewScreenUiState()
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            SimilarSeriesHorizontalSection(
                navController = navController,
                uiState = uiState,
                interactionListener = interactionListener
            )
        }
    }
    ListManagementBottomSheet(
        isVisible = uiState.showAddToListBottomSheet,
        onDismiss = { interactionListener.onDismissAddToListBottomSheet() },
        movieId = uiState.seriesId,
        viewModel = addToListViewModel
    )
}

fun List<ReviewUiState>.toReviewScreenUiState(): ReviewsScreenUiState {
    return ReviewsScreenUiState(reviews = this)
}