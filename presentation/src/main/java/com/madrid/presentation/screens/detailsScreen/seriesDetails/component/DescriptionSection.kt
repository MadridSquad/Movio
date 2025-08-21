package com.madrid.presentation.screens.detailsScreen.seriesDetails.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.TextWithReadMore

@Composable
fun DescriptionSection(description: String) {
    if (description.isNotEmpty()) {
        TextWithReadMore(
            description = description,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
            maxLines = 5
        )
    }
}