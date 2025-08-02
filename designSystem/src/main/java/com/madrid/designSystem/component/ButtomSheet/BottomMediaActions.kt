package com.madrid.designSystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.theme.Theme

@Composable
fun BottomMediaActions(
    onRateClick: () -> Unit,
    onPlayClick: () -> Unit,
    onAddToListClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = onRateClick,
            modifier = Modifier.size(48.dp)
        ) {
            MovioIcon(
                painter = painterResource(id = R.drawable.outline_star),
                contentDescription = "Rate",
                tint = Theme.color.surfaces.onSurface
            )
        }

        IconButton(
            onClick = onPlayClick,
            modifier = Modifier.size(48.dp)
        ) {
            MovioIcon(
                painter = painterResource(id = R.drawable.bold_video_circle),
                contentDescription = "Play",
                tint = Theme.color.surfaces.onSurface
            )
        }

        IconButton(
            onClick = onAddToListClick,
            modifier = Modifier.size(48.dp)
        ) {
            MovioIcon(
                painter = painterResource(id = R.drawable.outline_minimalistic),
                contentDescription = "Add to list",
                tint = Theme.color.surfaces.onSurface
            )
        }

        IconButton(
            onClick = { /* Handle share action */ },
            modifier = Modifier.size(48.dp)
        ) {
            MovioIcon(
                painter = painterResource(id = R.drawable.outline_share),
                contentDescription = "Share",
                tint = Theme.color.surfaces.onSurface
            )
        }
    }
}