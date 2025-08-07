package com.madrid.presentation.component.videoLibrary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun BottomRowForVideoLibrary(
    videosNumber: Number
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Theme.color.surfaces.surfaceVariant)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MovioText(
            text = "$videosNumber " + stringResource(com.madrid.presentation.R.string.video_library_video_number),
            color = Theme.color.surfaces.onSurfaceVariant,
            textStyle = Theme.textStyle.label.smallRegular12,
        )
        MovioIcon(
            modifier = Modifier.size(width = 18.dp ,height = 12.dp ),
            painter = painterResource(R.drawable.video_lib),
            contentDescription = stringResource(com.madrid.presentation.R.string.watch_list_icon)
        )

    }
}