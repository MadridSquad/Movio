package com.madrid.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.presentation.R


@Composable
fun DetailsChips(
    icon: Painter,
    iconTint: Color,
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                Theme.color.surfaces.surfaceContainer,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 1.dp, color = Theme.color.surfaces.onSurfaceAt2,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(vertical = 8.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MovioIcon(
                painter = icon, contentDescription = "chips icon ",
                tint = iconTint , modifier = Modifier.size(16.dp)
            )
            MovioText(
                text,
                color = Theme.color.surfaces.onSurfaceContainer,
                textStyle = Theme.textStyle.label.smallRegular12
            )
        }
    }
}


@Preview
@Composable
private fun  MovieDetailsHeaderPreview(modifier: Modifier = Modifier) {
    MovioTheme {
        DetailsChips(
            icon = painterResource(R.drawable.icon_home),
            iconTint = Theme.color.surfaces.onSurfaceVariant,
            text = "drama",
        )
    }
}