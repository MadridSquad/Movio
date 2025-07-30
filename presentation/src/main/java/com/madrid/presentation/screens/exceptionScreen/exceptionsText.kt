package com.madrid.presentation.screens.exceptionScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import androidx.compose.ui.res.painterResource

@Composable
fun EmptyStateCard(
    icon: Painter,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    iconContentDescription: String? = null
) {
    // Responsive sizing based on modifier
    Column(
        modifier = modifier
            .background(Theme.color.surfaces.surfaceContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon/Illustration
        MovioIcon(
            painter = icon,
            contentDescription = iconContentDescription,
            modifier = Modifier
                .padding(top = 16.dp)
                .size(180.dp, 150.dp), // Default large size, can be overridden by modifier
            tint = Theme.color.brand.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Title
        MovioText(
            text = title,
            color = Theme.color.surfaces.onSurface,
            textStyle = Theme.textStyle.title.mediumMedium16,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Description
        MovioText(
            text = description,
            color = Theme.color.surfaces.onSurfaceContainer,
            textStyle = Theme.textStyle.label.smallRegular12,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}