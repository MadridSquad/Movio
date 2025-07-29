package com.madrid.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.madrid.designSystem.component.CustomTextTitel
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.presentation.component.movioCards.MovioArtistsCard

data class CastMember(
    val id: String,
    val name: String,
    val imageUrl: String
)

@Composable
fun TopCastSection(
    modifier: Modifier = Modifier,
    castMembers: List<CastMember>,
    onSeeAllClick: () -> Unit = {},
    onCastMemberClick: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        CustomTextTitel(
            primaryText = stringResource(com.madrid.presentation.R.string.top_cast),
            secondaryText = stringResource(com.madrid.presentation.R.string.see_all),
            endIcon = painterResource(R.drawable.outline_alt_arrow_left),
            onSeeAllClick = { onSeeAllClick() },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(castMembers) { castMember ->
                CastMemberItem(
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
    castMember: CastMember,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.size(height = 111.dp, width = 86.dp),
    ) {
        MovioArtistsCard(
            imageUrl = castMember.imageUrl,
            artistsName = castMember.name,
            onClick = { onCastMemberClick(castMember.id.toInt()) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TopCastSectionPreview() {
    MovioTheme {
        TopCastSection(
            castMembers = listOf(
                CastMember(
                    id = "1",
                    name = "Ana de Armas",
                    imageUrl = "https://image.tmdb.org/t/p/w500/3vxvsmYLTf4jnr163SUlBIWX8qx.jpg"
                ),
                CastMember(
                    id = "2",
                    name = "Keanu Reeves",
                    imageUrl = "https://image.tmdb.org/t/p/w500/4D0PpNI0km5B9Gk7SZOo6hJxJ9P.jpg"
                ),
                CastMember(
                    id = "3",
                    name = "Ian McShane",
                    imageUrl = "https://image.tmdb.org/t/p/w500/9H7oVx4b6Z0j3EjLZN9mzcqcJjU.jpg"
                )
            ),
            onSeeAllClick = {},
            onCastMemberClick = {}
        )
    }
}