package com.madrid.presentation.component.header

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.DetailsChips

@Composable
fun ActorLocationInfo(
    location: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {

        DetailsChips(
            icon = painterResource(com.madrid.designSystem.R.drawable.outline_map_point),
            iconTint = Theme.color.surfaces.onSurfaceVariant,
            text = location,
        )

    }
}