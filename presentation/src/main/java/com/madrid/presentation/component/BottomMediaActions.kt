package com.madrid.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme

@Composable
fun BottomMediaActions(
    modifier: Modifier = Modifier,
    onRateClick: ((Boolean) -> Unit)? = null,
    onPlayClick: (() -> Unit)? = null,
    onAddToListClick: ((Boolean) -> Unit)? = null,
) {
    var isRated by remember { mutableStateOf(false) }
    var isSaved by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .height(56.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (onRateClick != null) {
            Column(
                modifier = Modifier
                    .width(136.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MediaActionItem(
                    modifier = Modifier.size(24.dp),
                    label = stringResource(com.madrid.presentation.R.string.rate_it),
                    isActive = isRated,
                    inactiveIcon = R.drawable.outline_star,
                    tint = Theme.color.surfaces.onSurfaceContainer,
                    onToggle = {
                        isRated = !isRated
                        onRateClick(isRated)
                    }
                )
            }
        }
        if (onPlayClick != null) {
            PlayButton(onClick = onPlayClick)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){

        if (onAddToListClick != null) {
            Column(
                modifier = Modifier
                    .width(136.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MediaActionItem(
                    label = stringResource(com.madrid.presentation.R.string.add_to_list),
                    isActive = isSaved,
                    inactiveIcon = R.drawable.outline_bookmark,
                    onToggle = {
                        isSaved = !isSaved
                        onAddToListClick(isSaved)
                    },
                    tint = Theme.color.surfaces.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        }
    }
}

@Composable
private fun MediaActionItem(
    label: String,
    isActive: Boolean,
    inactiveIcon: Int,
    onToggle: () -> Unit,
    tint: Color,
    modifier: Modifier
) {
    Column(
        modifier = Modifier
            .padding(top=4.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        MovioIcon(
            painter = painterResource(
                inactiveIcon
            ),
            contentDescription = label,
            tint = Theme.color.surfaces.onSurfaceVariant,
            modifier = Modifier
                .size(28.dp)
                .clickable(
                    onClick = onToggle,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
        )
        MovioText(
            text = label,
            textStyle = Theme.textStyle.label.smallRegular12,
            color = Theme.color.surfaces.onSurfaceVariant,
            modifier = Modifier.padding(top = 6.dp),
        )
    }
}

@Composable
private fun PlayButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(Color(0xFFB7A4FB), Color(0xFF663EF6))
                ),
                shape = CircleShape
            )
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        MovioIcon(
            painter = painterResource(R.drawable.icon_paly),
            contentDescription = stringResource(com.madrid.presentation.R.string.play),
            modifier = Modifier
                .align(Alignment.Center)
                .size(24.dp),
            tint = Color.White
        )
    }
}


@Preview(showBackground = true)
@Composable
fun BottomActionBarPreview() {
    MovioTheme {
        BottomMediaActions(
            onRateClick = {},
            onPlayClick = {},
            onAddToListClick = {}
        )
    }
}