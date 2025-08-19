package com.madrid.presentation.screens.detailsScreen.seriesDetails.component

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.madrid.presentation.component.CastMember
import com.madrid.presentation.component.TopCastHorizontalScroll
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsInteractionListener
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsUiState

@Composable
fun TopCastSection(
    uiState: SeriesDetailsUiState,
    interactionListener: SeriesDetailsInteractionListener,
    navController: NavHostController
) {
    val artists = uiState.topCast

    TopCastHorizontalScroll(
        castMembers = artists.map { cast ->
            CastMember(
                id = cast.id.toString(),
                name = cast.name,
                imageUrl = cast.imageUrl
            )
        },
        onSeeAllClick = {
            navController.navigate(
                Destinations.TopCast(
                    mediaId = uiState.seriesId,
                    isMovie = false
                )
            )
        },
        onCastMemberClick = { castId ->
            interactionListener.onActorCardClick(uiState.seriesId)
        }
    )
}