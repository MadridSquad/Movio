package com.madrid.designSystem.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.theme.Theme

@Composable
fun FloatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    size: Int,
    icon: Painter,
    contentDescription: String? = null
) {
    FloatingActionButton(
        modifier = modifier
            .size(size.dp),
        onClick = onClick,

        backgroundColor = Theme.color.brand.primary,
        content = {
            Icon(
                painter = icon,
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier.size((size / 2).dp)
            )
        }
    )
}

@Preview
@Composable
fun FloatingButtonPreview() {
    FloatingButton(
        onClick = {},
        size = 60,
        icon = painterResource(id = R.drawable.add),
    )
}