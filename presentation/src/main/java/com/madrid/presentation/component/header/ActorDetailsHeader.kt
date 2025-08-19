package com.madrid.presentation.component.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme


@Composable
fun ActorDetailsHeader(
    actorName: String,
    actorRole: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        MovioText(
            actorName,
            color = Theme.color.surfaces.onSurface,
            textStyle = Theme.textStyle.headline.mediumMedium18
        )
        MovioText(
            actorRole,
            color = Theme.color.surfaces.onSurfaceVariant,
            textStyle = Theme.textStyle.label.smallRegular14
        )
    }
}

