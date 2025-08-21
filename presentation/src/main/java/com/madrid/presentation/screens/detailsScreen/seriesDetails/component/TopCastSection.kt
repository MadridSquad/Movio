package com.madrid.presentation.screens.detailsScreen.seriesDetails.component

import androidx.compose.runtime.Composable
import com.madrid.presentation.component.TopCastHorizontalScroll
import com.madrid.presentation.viewModel.detailsViewModel.ArtistUiState

@Composable
fun TopCastSection(
    artists: List<ArtistUiState>,
    onActorCardClick: (actorId:Int) -> Unit,
    onSeeAllClick: () -> Unit,
) {
    TopCastHorizontalScroll(
        castMembers = artists,
        onSeeAllClick = { onSeeAllClick() },
        onCastMemberClick = { actorId -> onActorCardClick(actorId) }
    )
}