package com.madrid.presentation.component.movioCards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme


@Composable
fun MovioEpisodesCard(
    movieTitle: String,
    movieRate: String,
    currentMovieEpisode: String,
    movieTime: String,
    movieImageUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(74.dp)
            .clickable { onClick() },
    ) {
        Box(
            modifier = Modifier
                .height(74.dp)
        ) {
            BasicImageCard(
                imageUrl = movieImageUrl,
                modifier = Modifier
                    .height(74.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                radius = 8.dp,
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(color = Theme.color.surfaces.onSurfaceAt2)
                    .blur(16.dp)
                    .clickable {
                        onClick()
                    }
            )
            MovioIcon(
                contentDescription = "video circle",
                tint = Theme.color.surfaces.onSurface,
                painter = painterResource(R.drawable.button_video_play),
                modifier = Modifier
                    .size(width = 6.8.dp, height = 7.23.dp)
                    .align(Alignment.Center)
            )
        }
        FrameEpisodeCard(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            movieTitle = movieTitle,
            movieRate = movieRate,
            currentMovieEpisode = currentMovieEpisode,
            movieTime = movieTime,
        )
    }
}

@Composable
private fun FrameEpisodeCard(
    movieTitle: String,
    movieRate: String,
    currentMovieEpisode: String,
    movieTime: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row {
            MovioText(
                modifier = Modifier.weight(1f),
                text = movieTitle,
                color = Theme.color.surfaces.onSurface,
                textStyle = Theme.textStyle.title.mediumMedium14,
            )
            RateIcon(
                rate = movieRate,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MovioText(
                modifier = Modifier.padding(end = 6.dp),
                text = currentMovieEpisode,
                color = Theme.color.surfaces.onSurfaceContainer,
                textStyle = Theme.textStyle.label.smallRegular12,
            )
            MovioIcon(
                painter = painterResource(R.drawable.dot),
                contentDescription = "dot icon",
                modifier = Modifier.size(8.dp),
                tint = Theme.color.surfaces.onSurfaceContainer
            )
            MovioText(
                text = movieTime,
                color = Theme.color.surfaces.onSurfaceContainer,
                textStyle = Theme.textStyle.label.smallRegular12,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EpisodesCardPreview() {
    MovioTheme {
        MovioEpisodesCard(
            modifier = Modifier.padding(top = 50.dp),
            movieTitle = "Unimatrix Zero",
            movieRate = "9",
            currentMovieEpisode = "Episode 01",
            movieTime = "44m",
            movieImageUrl = "",
            onClick = { },
        )
    }
}