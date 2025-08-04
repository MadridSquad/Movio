package com.madrid.presentation.component.movioCards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun MovioRatingCard(
    movieTitle: String,
    movieImageUrl: String,
    height: Dp,
    rate: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        BasicImageCard(
            imageUrl = movieImageUrl,
            modifier = Modifier
                .width(76.dp)
                .height(height),
            radius = 8.dp
        )
        Column(
            modifier = Modifier
                .height(height)
                .weight(1f)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            MovioText(
                text = movieTitle,
                color = Theme.color.surfaces.onSurface,
                textStyle = Theme.textStyle.title.mediumMedium14,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            RatingStarsCard(rating = rate)
        }
    }
}


@Composable
fun RatingStarsCard(rating: String) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) { index ->
            val tint =
                if (rating.toDouble() > index) Theme.color.system.warning else Theme.color.surfaces.onSurfaceVariant
            MovioIcon(
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(16.dp),
                painter = painterResource(com.madrid.designSystem.R.drawable.bold_star),
            )
        }
        MovioText(
            text = rating,
            modifier = Modifier.padding(start = 8.dp),
            color = Theme.color.system.onWarning,
            textStyle = Theme.textStyle.label.smallRegular12,
        )
    }
}

