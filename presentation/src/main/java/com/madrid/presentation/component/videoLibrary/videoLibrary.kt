package com.madrid.presentation.component.videoLibrary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.ImageViewer
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun VideoLibrary(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    videosNumber: Int,
    title: String,
    posterUrl: String? = null,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = Theme.color.surfaces.surface)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.height(122.dp),
            contentAlignment = Alignment.Center
        )
        {
            if (posterUrl != null) {
                ImageViewer(
                    model = posterUrl,
                    contentDescription = stringResource(com.madrid.presentation.R.string.background_image_of_squares),
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Image(
                    painter = painterResource(com.madrid.presentation.R.drawable.library_background),
                    contentDescription = stringResource(com.madrid.presentation.R.string.background_image_of_squares),
                    contentScale = ContentScale.Crop
                )
            }
            MovioIcon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 8.dp),
                painter = painterResource(R.drawable.star_img),
                contentDescription = stringResource(com.madrid.presentation.R.string.star_icon),
                tint = Theme.color.surfaces.onSurface
            )
            MovioIcon(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 21.dp, start = 6.dp),
                painter = painterResource(R.drawable.star_img_four),
                contentDescription = stringResource(com.madrid.presentation.R.string.star_icon),
                tint = Theme.color.surfaces.onSurface
            )

            MovioIcon(
                painter = painterResource(R.drawable.library_main_icon),
                contentDescription = stringResource(com.madrid.presentation.R.string.movio_icon),
            )
        }
        BottomRowForVideoLibrary(
            videosNumber,
            modifier = modifier
                .clip(RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp))
        )

        MovioText(
            text = title,
            textStyle = Theme.textStyle.title.mediumMedium14,
            color = Theme.color.surfaces.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(48.dp)
        )
    }
}

@Preview(showBackground = true, heightDp = 500)
@Composable
private fun VideoLibraryPreview() {
    VideoLibrary(
        modifier = Modifier
            .width(158.dp)
            .height(153.dp),
        videosNumber = 10,
        title = "Sample Video Library",
        posterUrl = "https://example.com/sample_poster.jpg"
    )
}