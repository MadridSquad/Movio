package com.madrid.presentation.screens.detailsScreen.reviewsScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.component.ImageViewer
import com.madrid.designSystem.theme.Theme

@Composable
fun ReviewCard(
    reviewerName: String,
    reviewerImageUrl: String,
    rating: Float,
    date: String,
    content: String,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    val maxLines = if (isExpanded) Int.MAX_VALUE else 4
    val showReadMore = remember(content) { content.length > 100 }

    Column(
        modifier = modifier
            .width(258.dp)
            .fillMaxHeight()
            .background(
                color = Theme.color.surfaces.surfaceContainer,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(12.dp),
                color = Theme.color.surfaces.onSurfaceAt3
            )
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageViewer(
                model = reviewerImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(32.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                MovioText(
                    text = reviewerName,
                    color = Theme.color.surfaces.onSurface,
                    textStyle = Theme.textStyle.title.mediumMedium14,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                MovioText(
                    text = date,
                    color = Theme.color.surfaces.onSurfaceContainer,
                    textStyle = Theme.textStyle.body.smallRegular10
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                MovioIcon(
                    painter = painterResource(id = R.drawable.bold_star),
                    contentDescription = null,
                    tint = Theme.color.system.warning,
                    modifier = Modifier.size(16.dp)
                )
                MovioText(
                    text = rating.toString(),
                    color = Theme.color.system.onWarning,
                    textStyle = Theme.textStyle.label.smallRegular12
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        MovioText(
            text = content,
            color = Theme.color.surfaces.onSurfaceVariant,
            textStyle = Theme.textStyle.label.smallRegular12,
            maxLines = maxLines,
            modifier = Modifier.fillMaxWidth()
        )

        if (showReadMore) {
            Spacer(modifier = Modifier.height(4.dp))
            MovioText(
                text = if (isExpanded) stringResource(R.string.read_less) else stringResource(R.string.read_more),
                textStyle = Theme.textStyle.label.mediumMedium12,
                color = Theme.color.brand.onPrimaryContainer,
                modifier = Modifier
                    .clickable { isExpanded = !isExpanded }
                    .padding(top = 4.dp)
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ReviewCardPreview() {
    ReviewCard(
        reviewerName = "Awkwafina",
        reviewerImageUrl = "https://image.tmdb.org/t/p/w500/5xKGk6q5g7mVmg7k7U1RrLSHwz6.jpg",
        rating = 4.5f,
        date = "June 14, 2025",
        content = "This isn't a film, it's a live action video game with a predictable plot and loads of technologically choreographed CGI to substitute for anything vaguely akin to emotion."
    )
}