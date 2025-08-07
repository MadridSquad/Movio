package com.madrid.presentation.screens.libraryScreen.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun LibraryScreenHeader(
    headerTitle:String
){
    Row(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MovioText(
            text = headerTitle,
            color = Theme.color.surfaces.onSurface,
            textStyle = Theme.textStyle.headline.largeBold16,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}