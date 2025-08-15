package com.madrid.presentation.screens.searchScreen.features.recentSearchLayout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme
import com.madrid.designSystem.component.MovioText
import com.madrid.presentation.R

@Composable
fun SearchResultMessage(items: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MovioText(
            stringResource(id = R.string.search_result_count),
            color = Theme.color.surfaces.onSurfaceContainer,
            textStyle = Theme.textStyle.headline.mediumMedium16,
        )
        MovioText(
            text = stringResource(R.string.items_count, items),
            color = Theme.color.surfaces.onSurfaceVariant,
            textStyle = Theme.textStyle.label.smallRegular14,
        )
    }
}