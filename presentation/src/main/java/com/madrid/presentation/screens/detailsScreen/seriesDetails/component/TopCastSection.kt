package com.madrid.presentation.screens.detailsScreen.seriesDetails.component

import androidx.compose.runtime.Composable
import com.madrid.presentation.component.TopCastHorizontalScroll
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.ArtistUiState
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeeAllType
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsInteractionListener

@Composable
fun TopCastSection(
    artists: List<ArtistUiState>,
    seriesId:Int,
    listener: SeriesDetailsInteractionListener
) {
    TopCastHorizontalScroll(
        castMembers = artists,
        onSeeAllClick = {
            listener.onSeeAllClick(seriesId, seeAllType = SeeAllType.TopCast)
        },
        onCastMemberClick = { castId ->
            listener.onActorCardClick(seriesId)
        }
    )
}