package com.madrid.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.detectImageContent.FilteredImage

@Composable
fun MovieHomeCard(
    name: String,
    movieId: String,
    genres: List<String>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(
                RoundedCornerShape(8.dp)
            ),
    ) {
        FilteredImage(
            imageUrl = movieId, // Replace with actual image loading
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .height(200.dp)
        )
        MovioIcon(
            painter = painterResource(R.drawable.bold_video_circle),
            contentDescription = "bold video circle",
            tint = Theme.color.surfaces.onSurface,
            modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp)
                .clickable {
                    onClick()
                }
        )
        Box(
            modifier = Modifier
                .zIndex(1f)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(51.dp)
        ) {

            FilteredImage(
                imageUrl = movieId,
                contentDescription = name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(25.dp)
            )


            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 4.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                MovioText(
                    name,
                    color = Theme.color.brand.onPrimary,
                    textStyle = Theme.textStyle.label.mediumMedium12,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    genres.forEach {
                        MovioText(
                            text = it,
                            color = Theme.color.surfaces.onSurfaceContainer,
                            textStyle = Theme.textStyle.body.smallRegular10,
                            modifier = Modifier
                                .border(
                                    width = 0.5.dp,
                                    Theme.color.surfaces.onSurfaceAt3,
                                    RoundedCornerShape(24.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                        )
                    }
                }
            }
        }
    }
}