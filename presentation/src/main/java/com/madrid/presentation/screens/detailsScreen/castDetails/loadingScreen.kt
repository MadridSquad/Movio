package com.madrid.presentation.screens.detailsScreen.castDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    message: String = "Loading..."
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            MovioText(
                text = message,
                textStyle = Theme.textStyle.label.smallRegular14,
                color = Theme.color.surfaces.onSurface,
            )
        }
    }
}
