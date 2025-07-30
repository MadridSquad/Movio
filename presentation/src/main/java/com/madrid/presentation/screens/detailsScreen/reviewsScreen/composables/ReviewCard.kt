package com.madrid.presentation.screens.detailsScreen.reviewsScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.detectImageContent.FilteredImage

@Composable
fun ReviewCard(
    reviewerName: String,
    reviewerImageUrl: String,
    rating: Float,
    date: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(258.dp)
            .height(137.dp)
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
            FilteredImage(
                imageUrl = reviewerImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(32.dp)
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
                    painter = androidx.compose.ui.res.painterResource(id = R.drawable.bold_star),
                    contentDescription = null,
                    tint = Theme.color.system.warning,
                    modifier = Modifier.size(16.dp)
                )
                MovioText(
                    text = rating.toString(),
                    color = Theme.color.system.onWarning,
                    textStyle = Theme.textStyle.label.smallRegular14
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        MovioText(
            text = content,
            color = Theme.color.surfaces.onSurfaceVariant,
            textStyle = Theme.textStyle.label.smallRegular12,
            maxLines = 4,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@Preview(showBackground = true , showSystemUi = true)
@Composable
fun kj(){
    ReviewCard(
        reviewerName = "Awkwafina",
        reviewerImageUrl = "https://image.tmdb.org/t/p/w500/5xKGk6q5g7mVmg7k7U1RrLSHwz6.jpg",
        rating = 4.5f,
        date = "June 14, 2025",
        content = "This isn't a film, it's a live action video game with a predictable plot and loads of technologically choreographed CGI to substitute for anything vaguely akin to emotion."
    )
}