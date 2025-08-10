package com.madrid.presentation.component.videoLibrary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun VideoLibrary(
    onClick: () -> Unit = { },
    videosNumber: Int,
    title : String ,
) {
    Column(
        modifier = Modifier
            .width(158.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = Theme.color.surfaces.surface)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.size(width = 158.dp , height =  110.dp),
            contentAlignment = Alignment.Center
        )
        {
            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(com.madrid.presentation.R.drawable.library_background),
                contentDescription = stringResource(com.madrid.presentation.R.string.background_image_of_squares),
            )
            MovioIcon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 8.dp)
                    .zIndex(3f),
                painter = painterResource(R.drawable.star_img),
                contentDescription = stringResource(com.madrid.presentation.R.string.star_icon),
                tint = Theme.color.surfaces.onSurface
            )
            MovioIcon(
                modifier = Modifier
                    .zIndex(3f)
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
            videosNumber
        )
        MovioText(
            text = title,
            textStyle = Theme.textStyle.title.mediumMedium14,
            color = Theme.color.surfaces.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier =  Modifier.padding(top= 8.dp).fillMaxWidth().height(48.dp)
        )
    }
}