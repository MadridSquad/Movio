package com.madrid.presentation.screens.detailsScreen.detailsMovieScreen

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.madrid.designSystem.R.drawable
import com.madrid.designSystem.component.EmptySearchLayout
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.component.ShareBottomSheetContent
import com.madrid.designSystem.component.TextWithReadMore
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.BottomMediaActions
import com.madrid.presentation.component.CastMember
import com.madrid.presentation.component.TopCastHorizontalScroll
import com.madrid.presentation.component.header.MovieDetailsHeader
import com.madrid.presentation.component.movieActorBackground.MoviePosterDetailScreen
import com.madrid.presentation.component.movioCards.MovioArtistsCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.addtolist.ListManagementBottomSheet
import com.madrid.presentation.screens.detailsScreen.reviewsScreen.composables.ReviewScreen
import com.madrid.presentation.screens.detailsScreen.seriesDetails.toReviewScreenUiState
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarMovie
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarMoviesSection
import com.madrid.presentation.viewModel.detailsViewModel.DetailsMovieViewModel
import com.madrid.presentation.viewModel.libraryViewModel.addtolist.MovieListViewModel


@Composable
fun MovieDetailsScreen(
    viewModel: DetailsMovieViewModel = hiltViewModel(),
    addToListViewModel: MovieListViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val addToListUiState by addToListViewModel.state.collectAsState()
    val navController = LocalNavController.current
    val context = LocalContext.current
    var showShareSheet by remember { mutableStateOf(false) }
    var showAddToListBottomSheet by remember { mutableStateOf(false) }

    fun copyToClipboard(text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Movie Link", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Link copied", Toast.LENGTH_SHORT).show()
    }

    fun shareToApp(appPackage: String, url: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
            setPackage(appPackage)
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            val fallback = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, url)
            }
            context.startActivity(Intent.createChooser(fallback, "Share via"))
        }
    }

    var showAddRatingBottomSheet by remember { mutableStateOf(false) }
    var showDoneRatingBottomSheet by remember { mutableStateOf(false) }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp),
            contentAlignment = Alignment.Center
        ) {
            EmptySearchLayout(
                title = stringResource(R.string.internet_is_not_available),
                description = stringResource(R.string.please_make_sure_you_are_connected_to_the_internet_and_try_again),
                image = R.drawable.img_no_internet
            )
        }
    } else {
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

            MovioBottomSheet(
                show = showShareSheet,
                onDismiss = { showShareSheet = false },
                containerColor = Theme.color.surfaces.surface
            ) {
                ShareBottomSheetContent(
                    onCopyLink = {
                        copyToClipboard("https://www.themoviedb.org/movie/${uiState.movieId}")
                        showShareSheet = false
                    },
                    onShareFacebook = {
                        shareToApp(
                            "com.facebook.katana",
                            "https://www.themoviedb.org/movie/${uiState.movieId}"
                        )
                        showShareSheet = false
                    },
                    onShareX = {
                        shareToApp(
                            "com.twitter.android",
                            "https://www.themoviedb.org/movie/${uiState.movieId}"
                        )
                        showShareSheet = false
                    }
                )
            }

            TopAppBar(
                text = null,
                modifier = Modifier.padding(start = 16.dp, top = 36.dp, end = 16.dp),
                onFirstIconClick = { navController.popBackStack() },
                onSecondIconClick = { showShareSheet = true },
                onThirdIconClick = {
                    viewModel.onClickLoveIcon(uiState.movieId)
                },
                isFavorite = uiState.isLoved
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 32.dp)
            ) {
                Spacer(modifier = Modifier.height(360.dp))
                MovieDetailsHeader(
                    movieName = uiState.movieName,
                    movieCategory = uiState.genreMovie,
                    date = uiState.dataMovie,
                    time = uiState.movieDuration,
                    rate = uiState.rate.take(3),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )
                BottomMediaActions(
                    onAddToListClick = {
                        showAddToListBottomSheet = true
                    },
                    onRateClick = {
                        showAddRatingBottomSheet = true
                    },
                    onPlayClick = {
                        uiState.trailerKey?.let { key ->
                            val youtubeAppIntent =
                                Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key"))
                            val youtubeWebIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.youtube.com/watch?v=$key")
                            )

                            try {
                                context.startActivity(youtubeAppIntent)
                            } catch (e: ActivityNotFoundException) {
                                context.startActivity(youtubeWebIntent)
                            }
                        }
                    },
                )

                MovioBottomSheet(
                    show = showAddRatingBottomSheet,
                    onDismiss = { showAddRatingBottomSheet = false },
                    content = {
                        if (uiState.isGuest) {
                            Column {
                                Image(
                                    painter = painterResource(id = drawable.library_main_icon),
                                    contentDescription = "Search Icon",
                                    modifier = Modifier
                                        .size(width = 60.dp, height = 66.dp)
                                        .align(CenterHorizontally)
                                        .padding(bottom = 16.dp),
                                )
                                MovioText(
                                    text = stringResource(R.string.you_dont_have_an_account),
                                    textStyle = Theme.textStyle.title.mediumMedium16,
                                    color = Theme.color.surfaces.onSurface,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp)
                                )
                                MovioText(
                                    text = stringResource(R.string.this_rating_is_only_available_to_registered_users_Login_to_share_your_rating),
                                    textStyle = Theme.textStyle.label.smallRegular12,
                                    color = Theme.color.surfaces.onSurfaceContainer,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                )
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 40.dp,
                                            bottom = 32.dp,
                                            start = 16.dp,
                                            end = 16.dp
                                        )
                                        .height(48.dp),
                                    onClick = {
                                        showAddRatingBottomSheet = false
                                        navController.navigate(Destinations.LoginScreen)                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Theme.color.brand.primary,
                                    ),
                                    shape = RoundedCornerShape(24.dp),
                                    elevation = ButtonDefaults.elevation(0.dp)
                                ) {
                                    MovioText(
                                        text = stringResource(R.string.login),
                                        textStyle = Theme.textStyle.label.mediumMedium14,
                                        color = Theme.color.brand.onPrimary,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                }
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                MovioArtistsCard(
                                    imageUrl = uiState.topImageUrl,
                                    circleImageSize = 88.dp,
                                    artistsName = uiState.movieName,
                                    paddingBetweenImageAndText = 8.dp,
                                )
                                MovioText(
                                    modifier = Modifier.padding(top = 24.dp),
                                    text = stringResource(R.string.add_your_overall_rating_for_this_movie),
                                    color = Theme.color.surfaces.onSurfaceContainer,
                                    textStyle = Theme.textStyle.label.smallRegular14
                                )
                                Row(
                                    modifier = Modifier.padding(top = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    (1..5).forEach { i ->
                                        MovioIcon(
                                            painter = painterResource(drawable.bold_star),
                                            contentDescription = null,
                                            tint = if (i <= uiState.userRating) Theme.color.system.warning else Theme.color.surfaces.onSurfaceVariant,
                                            modifier = Modifier
                                                .size(28.dp)
                                                .clickable { viewModel.onPickRatingNumber(i) }
                                        )
                                    }
                                }
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 40.dp,
                                            bottom = 32.dp,
                                            start = 16.dp,
                                            end = 16.dp
                                        )
                                        .height(48.dp),
                                    onClick = {
                                        // Only proceed if user has selected a rating
                                        if (uiState.userRating > 0) {
                                            viewModel.addRating()
                                            showAddRatingBottomSheet = false
                                            showDoneRatingBottomSheet = true
                                        }
                                    },
                                    enabled = uiState.userRating > 0, // Disable button when no rating selected
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = if (uiState.userRating > 0)
                                            Theme.color.brand.primary
                                        else
                                            Theme.color.surfaces.onSurfaceVariant.copy(alpha = 0.3f),
                                        disabledBackgroundColor = Theme.color.surfaces.onSurfaceVariant.copy(
                                            alpha = 0.3f
                                        )
                                    ),
                                    shape = RoundedCornerShape(24.dp),
                                    elevation = ButtonDefaults.elevation(0.dp)
                                ) {
                                    MovioText(
                                        text = stringResource(R.string.submit),
                                        color = if (uiState.userRating > 0)
                                            Theme.color.brand.onPrimary
                                        else
                                            Theme.color.surfaces.onSurfaceVariant.copy(alpha = 0.6f),
                                        textStyle = Theme.textStyle.label.mediumMedium14
                                    )
                                }
                            }
                        }
                    }
                )

                MovioBottomSheet(
                    show = showDoneRatingBottomSheet,
                    onDismiss = { showDoneRatingBottomSheet = false },
                    content = {
                        Column {
                            Image(
                                painter = painterResource(id = R.drawable.party_icon),
                                contentDescription = "Party Icon",
                                modifier = Modifier
                                    .size(68.dp)
                                    .align(CenterHorizontally)
                                    .padding(bottom = 8.dp),
                            )
                            MovioText(
                                text = stringResource(R.string.thank_you_for_your_rating),
                                textStyle = Theme.textStyle.label.smallRegular14,
                                color = Theme.color.surfaces.onSurfaceContainer,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .align(Alignment.CenterHorizontally),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                (1..5).forEach { i ->
                                    MovioIcon(
                                        painter = painterResource(drawable.bold_star),
                                        contentDescription = null,
                                        tint = if (i <= uiState.userRating) Theme.color.system.warning else Theme.color.surfaces.onSurfaceVariant,
                                        modifier = Modifier
                                            .size(if (i == uiState.userRating) 48.dp else 28.dp)
                                            .align(Alignment.CenterVertically)
                                    )
                                }
                            }
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 40.dp,
                                        bottom = 32.dp,
                                        start = 16.dp,
                                        end = 16.dp
                                    )
                                    .height(48.dp),
                                onClick = {
                                    showDoneRatingBottomSheet = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Theme.color.brand.primary,
                                ),
                                shape = RoundedCornerShape(24.dp),
                                elevation = ButtonDefaults.elevation(0.dp)
                            ) {
                                MovioText(
                                    text = stringResource(R.string.done),
                                    color = Theme.color.brand.onPrimary,
                                    textStyle = Theme.textStyle.label.mediumMedium14
                                )
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextWithReadMore(
                    description = uiState.description,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    maxLines = 5
                )
                TopCastHorizontalScroll(
                    onSeeAllClick = {
                        navController.navigate(
                            Destinations.TopCast(
                                mediaId = uiState.movieId,
                                isMovie = true
                            )
                        )
                    },
                    onCastMemberClick = { castId ->
                        navController.navigate(
                            Destinations.ActorDetails(
                                artistId = castId
                            )
                        )
                    },
                    modifier = Modifier.padding(top = 32.dp),
                    castMembers = uiState.casts.map { cast ->
                        CastMember(
                            id = cast.id.toString(),
                            name = cast.name,
                            imageUrl = cast.imageUrl
                        )
                    }
                )
                ReviewScreen(
                    modifier = Modifier.padding(top = 32.dp),
                    onSeeAllReviews = {
                        navController.navigate(
                            Destinations.ReviewsScreen(
                                uiState.movieId,
                                isMovie = true
                            )
                        )
                    },
                    uiState = uiState.reviews.toReviewScreenUiState()
                )
                SimilarMoviesSection(
                    modifier = Modifier.padding(vertical = 32.dp),
                    onSeeAllClick = {
                        navController.navigate(
                            Destinations.SimilarMediaScreen(
                                mediaId = uiState.movieId,
                                isMovie = true
                            )
                        )
                    },
                    onMovieClick = { movie ->
                        navController.navigate(
                            Destinations.MovieDetailsScreen(
                                movieId = movie.id
                            )
                        )
                    },
                    similarMovies = uiState.similarMovies.map { movie ->
                        SimilarMovie(
                            id = movie.id,
                            title = movie.title,
                            imageUrl = movie.imageUrl,
                            rating = movie.rating
                        )
                    }
                )
            }
        }
    }
    ListManagementBottomSheet(
        isVisible = showAddToListBottomSheet,
        onDismiss = { showAddToListBottomSheet = false },
        movieId = uiState.movieId,
        viewModel = addToListViewModel
    )
}