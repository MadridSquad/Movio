package com.madrid.presentation.component.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.DetailsChips


@Composable
fun ActorDetailsHeader(
    actorName: String,
    actorRole: String,
    dateOfBirth: String,
    location: String,
    modifier: Modifier = Modifier
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
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            DetailsChips(
                icon = painterResource(com.madrid.designSystem.R.drawable.outline_calendar),
                iconTint = Theme.color.surfaces.onSurfaceVariant,
                text = dateOfBirth,
            )
            DetailsChips(
                icon = painterResource(com.madrid.designSystem.R.drawable.outline_map_point),
                iconTint = Theme.color.surfaces.onSurfaceVariant,
                text = location,
            )
        }
    }
}