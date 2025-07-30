package com.madrid.presentation.screens.libraryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun LibraryScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface),
        contentAlignment = Alignment.Center

    ) {
        MovioText(
            text = "Library Screen",
            textStyle = Theme.textStyle.title.largeBold14,
            color = Theme.color.brand.primary,
        )
    }
}