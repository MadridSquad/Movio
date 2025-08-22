package com.madrid.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.presentation.component.movioCards.MovioArtistsCard
import com.madrid.presentation.viewModel.detailsViewModel.ArtistUiState
import com.madrid.presentation.viewModel.detailsViewModel.SeeAllType

@Composable
fun TopCastHorizontalScroll(
    modifier: Modifier = Modifier,
    castMembers: List<ArtistUiState>,
    onSeeAllClick: () -> Unit = {},
    onCastMemberClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        CustomTextTitle(
            primaryText = stringResource(SeeAllType.TopCast.stringResId),
            secondaryText = stringResource(com.madrid.presentation.R.string.see_all),
            endIcon = painterResource(R.drawable.outline_alt_arrow_left),
            onSeeAllClick = { onSeeAllClick() },
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(castMembers) { castMember ->
                CastMemberItem(
                    modifier = Modifier.width(86.dp),
                    castMember = castMember,
                    onCastMemberClick = { castId ->
                        onCastMemberClick(castId)
                    }
                )
            }
        }
    }
}

@Composable
private fun CastMemberItem(
    onCastMemberClick: (Int) -> Unit,
    castMember: ArtistUiState,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        MovioArtistsCard(
            imageUrl = castMember.imageUrl,
            artistsName = castMember.name,
            paddingBetweenImageAndText = 4.dp,
            onClick = { onCastMemberClick(castMember.id.toInt()) },
            circleImageSize = 68.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TopCastSectionPreview() {
    MovioTheme {
        TopCastHorizontalScroll(
            castMembers = listOf(
                ArtistUiState(
                    id = 1,
                    name = "Ana de Armas",
                    imageUrl = "https://image.tmdb.org/t/p/w500/3vxvsmYLTf4jnr163SUlBIWX8qx.jpg"
                ),
                ArtistUiState(
                    id = 2,
                    name = "Keanu Reeves",
                    imageUrl = "https://image.tmdb.org/t/p/w500/4D0PpNI0km5B9Gk7SZOo6hJxJ9P.jpg"
                ),
                ArtistUiState(
                    id = 3,
                    name = "Ian McShane",
                    imageUrl = "https://image.tmdb.org/t/p/w500/9H7oVx4b6Z0j3EjLZN9mzcqcJjU.jpg"
                )
            ),
            onSeeAllClick = {},
            onCastMemberClick = {}
        )
    }
}