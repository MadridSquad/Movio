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
import com.madrid.designSystem.component.DialogWithButtonLayout
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.MovioSnakBar
import com.madrid.designSystem.component.ToastDuration
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.BottomMediaActions
import com.madrid.presentation.component.header.SeriesDetailsHeader
import com.madrid.presentation.component.logout.LogoutConfirmationBottomSheet
import com.madrid.presentation.component.movieActorBackground.MoviePosterDetailScreen
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.detailsScreen.castDetails.LoadingScreen
import com.madrid.presentation.screens.detailsScreen.reviewsScreen.composables.ReviewScreen
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.AddRatingBottomSheet
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.CurrentSeasonsSection
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.DescriptionSection
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.DoneAddRating
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.ShareBottomSheet
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.SimilarSeriesHorizontalSection
import com.madrid.presentation.screens.detailsScreen.seriesDetails.component.TopCastSection
import com.madrid.presentation.utils.copyToClipboard
import com.madrid.presentation.utils.playSeriesTrailer
import com.madrid.presentation.viewModel.detailsViewModel.SeeAllType
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetailsUiState
import com.madrid.presentation.viewModel.detailsViewModel.seriesDetails.SeriesDetailsEffect
import com.madrid.presentation.viewModel.detailsViewModel.seriesDetails.SeriesDetailsInteractionListener
import com.madrid.presentation.viewModel.detailsViewModel.seriesDetails.SeriesDetailsViewModel

@Composable
fun SeriesDetailsScreen(
    viewModel: SeriesDetailsViewModel = hiltViewModel()
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
                    navController.navigate(Destinations.LoginScreen)
                }

                is SeriesDetailsEffect.NavigateToSeeAllScreen -> {
                    when (effect.seeAllType) {
                        SeeAllType.TopCast -> navController.navigate(
                            Destinations.TopCast(
                                effect.seriesId,
                                false
                            )
                        )

                        SeeAllType.Season -> navController.navigate(
                            Destinations.SeasonsScreen(
                                effect.seriesId,
                                1
                            )
                        )

                        SeeAllType.SimilarSeries -> navController.navigate(
                            Destinations.SimilarMediaScreen(
                                effect.seriesId,
                                false
                            )
                        )

                        SeeAllType.Review -> navController.navigate(
                            Destinations.ReviewsScreen(
                                effect.seriesId,
                                false
                            )
                        )
                    }
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
                    image = Theme.drawables.noInternetId,
                    buttonText = stringResource(R.string.try_again),
                    onClick = {
                        interactionListener.onRetryButtonClick()
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
                listener = interactionListener,
            )
        }
    }
}

@Composable
private fun SeriesDetailsScreenContent(
    context: Context,
    uiState: SeriesDetailsUiState,
    listener: SeriesDetailsInteractionListener,
) {
    ShareBottomSheet(
        copyToClipboard = { text -> copyToClipboard(text, context) },
        context = context,
        uiState = uiState,
        interactionListener = listener
    )

    Box(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(Theme.color.surfaces.surface)
    ) {
        MoviePosterDetailScreen(imageUrl = uiState.topImageUrl, modifier = Modifier.fillMaxSize())

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
            onFirstIconClick = { listener.onBackButtonClick() },
            onSecondIconClick = { listener.onShareIconClick() },
            onThirdIconClick = { listener.onFavoriteClick(uiState.seriesId) },
            isFavorite = uiState.isFavourite
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(360.dp))

            SeriesDetailsHeader(
                movieName = uiState.seriesName,
                seriesCategory = uiState.seriesGenre,
                date = uiState.productionDate,
                time = stringResource(
                    id = R.string.season_count, uiState.numberOfSeasons.toString()
                ),
                rate = uiState.rate.take(3),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )

            BottomMediaActions(
                onRateClick = {
                    listener.onShowAddRatingBottomSheetClick()
                },
                onPlayClick = {
                    listener.onPlayItClick()
                    playSeriesTrailer(context, uiState)
                },
                onAddToListClick = { listener.onShowSnackBar() },
                modifier = Modifier.padding(vertical = 16.dp)
            )

            MovioBottomSheet(
                show = uiState.showAddRatingBottomSheet,
                onDismiss = { listener.onDismissAddRatingBottomSheet() },
                content = {
                    if (uiState.isGuest) {
                        LogoutConfirmationBottomSheet(
                            isVisible = uiState.isGuest,
                            onDismiss = { listener.onDismissShareBottomSheetClick() },
                            onNavigateToAuth = { listener.onLoginButtonClick() },
                            title = stringResource(R.string.you_dont_have_an_account),
                            description = stringResource(R.string.this_rating_is_only_available_to_registered_users_Login_to_share_your_rating),
                            actionButtonText = stringResource(R.string.login)
                        )
                    } else {
                        AddRatingBottomSheet(
                            onDismissAddRatingBottomSheet = { listener.onDismissAddRatingBottomSheet() },
                            onRateButtonClick = { listener.onRateButtonClick() },
                            onShowDoneRatingBottomSheetClick = { listener.onShowDoneRatingBottomSheetClick() },
                            onPickRatingNumber = { listener.onPickRatingNumber(it) },
                            imageUrl = uiState.topImageUrl,
                            seriesName = uiState.seriesName,
                            userRating = uiState.userRating,
                        )
                    }
                }
            )

            DoneAddRating(
                showDoneRatingBottomSheet = uiState.showDoneRatingBottomSheet,
                userRating = uiState.userRating,
                onDismissDoneRatingClick = { listener.onDismissShowDoneRatingBottomSheetClick() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DescriptionSection(description = uiState.description)

            TopCastSection(
                artists = uiState.topCast,
                onActorCardClick = { actorId -> listener.onActorCardClick(actorId) },
                onSeeAllClick = {
                    listener.onSeeAllClick(
                        seriesId = uiState.seriesId,
                        seeAllType = SeeAllType.TopCast
                    )
                },
            )

            CurrentSeasonsSection(
                seasons = uiState.currentSeasonsUiStates,
                onSeeAllClick = { listener.onSeeAllClick(uiState.seriesId, SeeAllType.Season) },
                onCurrentSeasonCardClick = { seasonNumber ->
                    listener.onCurrentSeasonCardClick(
                        uiState.seriesId,
                        seasonNumber = seasonNumber
                    )
                },
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (uiState.reviews.isNotEmpty()) {
                ReviewScreen(
                    reviews = uiState.reviews,
                    onSeeAllReviews = {
                        listener.onSeeAllClick(
                            uiState.seriesId,
                            SeeAllType.Review
                        )
                    },
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            SimilarSeriesHorizontalSection(
                modifier = Modifier.padding(bottom = 32.dp),
                uiState = uiState,
                onSeeAllClick = {
                    listener.onSeeAllClick(
                        seriesId = uiState.seriesId,
                        seeAllType = SeeAllType.SimilarSeries
                    )
                },
                onSimilarSeriesCardClick = { seriesId -> listener.onSimilarSeriesCardClick(seriesId) }
            )
        }

        LogoutConfirmationBottomSheet(
            title = stringResource(R.string.you_dont_have_an_account),
            description = stringResource(R.string.please_log_in_or_create_an_account_to_save_items_to_your_favorites_and_access_them_later),
            actionButtonText = stringResource(R.string.login),
            isVisible = uiState.isLoginBottomSheetVisible,
            onDismiss = { listener.onDismissLoginBottomSheet() },
            onNavigateToAuth = { listener.onLoginButtonClick() },
        )
    }

    if (uiState.showSnackBar) {
        Box(modifier = Modifier.fillMaxSize()) {
            MovioSnakBar(
                message = stringResource(uiState.errorResMessageId),
                duration = ToastDuration.SHORT,
                onDismiss = { listener.onDismissSnackBar() },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}