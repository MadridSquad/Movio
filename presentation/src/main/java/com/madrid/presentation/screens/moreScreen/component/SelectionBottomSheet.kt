package com.madrid.presentation.screens.moreScreen.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.MovioButton
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun SelectionBottomSheet(
    title: String,
    isShown: Boolean,
    onDismiss: () -> Unit,
    selectedOption: String,
    options: List<OptionUiState>,
    modifier: Modifier = Modifier,
    onOptionSelected: (OptionUiState) -> Unit,
    onConfirmButtonClick: () -> Unit,
) {
    MovioBottomSheet(
        show = isShown,
        onDismiss = onDismiss,
        containerColor = Theme.color.surfaces.surface,
    ) {
        LazyColumn(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Title(
                    title = title,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(options) { option ->
                Option(
                    modifier = Modifier.padding(bottom = 12.dp),
                    option = option.title,
                    isSelected = option.id == selectedOption,
                    onClick = { onOptionSelected(option) },
                    leadingIcon = option.leadingIcon,
                    trailingIcon = option.trailingIcon
                )
            }

            item {
                MovioButton(
                    color = Theme.color.brand.primary,
                    onClick = onConfirmButtonClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    MovioText(
                        text = stringResource(com.madrid.presentation.R.string.confirm),
                        color = Theme.color.brand.onPrimary,
                        textStyle = Theme.textStyle.label.mediumMedium14,
                    )
                }
            }
        }
    }
}

@Composable
private fun Title(
    modifier: Modifier = Modifier,
    title: String,
) {
    MovioText(
        modifier = modifier,
        text = title,
        color = Theme.color.surfaces.onSurface,
        textStyle = Theme.textStyle.title.mediumMedium14,
    )
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
private fun Option(
    option: String,
    modifier: Modifier = Modifier,
    leadingIcon: Painter? = null,
    trailingIcon: Painter = painterResource(R.drawable.bold_check_circle),
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(8.dp)
    val borderModifier = if (isSelected)
        Modifier.border(
            brush = Theme.color.gradients.borderGradient,
            width = 1.dp,
            shape = shape
        ) else
        Modifier.border(
            brush = SolidColor(Color.Transparent),
            width = 1.dp,
            shape = shape
        )

    Row(
        modifier = modifier
            .height(48.dp)

            .clip(shape)
            .then(
                borderModifier
            )
            .background(Theme.color.surfaces.surfaceContainer)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = onClick
            ).padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadingIcon != null) {
            MovioIcon(
                modifier = Modifier.padding(end = 8.dp),
                painter = leadingIcon,
                contentDescription = null,
                tint = Theme.color.surfaces.onSurface
            )
        }
        MovioText(
            modifier = Modifier.weight(1f),
            text = option,
            color = Theme.color.surfaces.onSurface,
            textStyle = Theme.textStyle.title.mediumMedium14,
        )
        if (isSelected) {
            MovioIcon(
                painter = trailingIcon,
                contentDescription = null,
                tint = Theme.color.brand.primary
            )
        }
    }
}

data class OptionUiState(
    val id: String,
    val title: String,
    val leadingIcon: Painter? = null,
    val trailingIcon: Painter
)