package com.madrid.presentation.screens.detailsScreen.seriesDetails

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
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
import com.madrid.presentation.component.CastMember
import com.madrid.presentation.component.TopCastHorizontalScroll
import com.madrid.presentation.component.header.SeriesDetailsHeader
import com.madrid.presentation.component.movieActorBackground.MoviePosterDetailScreen
import com.madrid.presentation.component.movioCards.MovioArtistsCard
import com.madrid.presentation.component.movioCards.MovioSeasonCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
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
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeeAllType
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsEffect
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsInteractionListener
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsUiState
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsViewModel

@Composable
fun SeriesDetailsScreen(
    viewModel: SeriesDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val interactionListener = viewModel as SeriesDetailsInteractionListener
    val navController = LocalNavController.current
    val seasons = uiState.currentSeasonsUiStates
    val artists = uiState.topCast
    var showAddRatingBottomSheet by remember { mutableStateOf(false) }
    var showDoneRatingBottomSheet by remember { mutableStateOf(false) }
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
                   when (effect.seeAllType){
                       SeeAllType.TopCast -> navController.navigate(Destinations.TopCast(effect.seriesId,false))
                       SeeAllType.Season -> navController.navigate(Destinations.SeasonsScreen(effect.seriesId,1))
                       SeeAllType.SimilarSeries -> navController.navigate(Destinations.SimilarMediaScreen(effect.seriesId,false))
                       SeeAllType.Review -> navController.navigate(Destinations.ReviewsScreen(effect.seriesId,false))
                    }
                }
            }
            context.startActivity(Intent.createChooser(fallback, "Share via"))
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
                viewModel = viewModel,
            )
        }
    }
}

@Composable
private fun SeriesDetailsScreenContent(
    uiState: SeriesDetailsUiState,
    context: Context,
    listener: SeriesDetailsInteractionListener,
    viewModel: SeriesDetailsViewModel,
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
            onSecondIconClick = { listener.onShareBottomSheetClick() },
            onThirdIconClick = { listener.onFavoriteClick(uiState.seriesId) },
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
                        GussetView(listener)
                    } else {
                        AddRatingBottomSheet(uiState, viewModel)
                    }
                }
            )

            DoneAddRating(
                showDoneRatingBottomSheet = uiState.showDoneRatingBottomSheet,
                userRating = uiState.userRating, listener = listener
            )

            Spacer(modifier = Modifier.height(16.dp))

            DescriptionSection(description = uiState.description)

            TopCastSection(artists = uiState.topCast, seriesId = uiState.seriesId, listener = listener)

            CurrentSeasonsSection(
                seriesId = uiState.seriesId,
                seasons = uiState.currentSeasonsUiStates,
                listener = listener
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (uiState.reviews.isNotEmpty()) {
                ReviewScreen(
                    reviews = uiState.reviews,
                    onSeeAllReviews = {
                        listener.onSeeAllClick(uiState.seriesId, SeeAllType.Review)
                    },
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            SimilarSeriesHorizontalSection(
                uiState = uiState,
                interactionListener = listener
            )
        }
        LogoutConfirmationBottomSheet(
            title = stringResource(R.string.you_dont_have_an_account),
            description = stringResource(R.string.please_log_in_or_create_an_account_to_save_items_to_your_favorites_and_access_them_later),
            actionButtonText = stringResource(R.string.login),
            isVisible = uiState.isLoginBottomSheetVisible,
            onDismiss = { viewModel.onDismissLoginBottomSheet() },
            onNavigateToAuth = {
                navController.navigate(Destinations.LoginScreen) {
                    popUpTo(Destinations.LoginScreen) { inclusive = false }
                }
            },
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