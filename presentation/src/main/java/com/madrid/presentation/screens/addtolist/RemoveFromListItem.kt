package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun RemoveFromListItem(
    onRemoveFromList: () -> Unit,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = isEnabled && !isLoading,
                role = Role.Button,
            ) {
                if (isEnabled && !isLoading) {
                    onRemoveFromList()
                }
            }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (isEnabled && !isLoading) {
                            Theme.color.system.error.copy(alpha = 0.1f)
                        } else {
                            Theme.color.surfaces.surface
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                MovioIcon(
                    painter = painterResource(
                        id = if (isLoading) {
                            R.drawable.loading
                        } else {
                            com.madrid.presentation.R.drawable.ic_add_continer
                        }
                    ),
                    contentDescription = if (isLoading) {
                        "Removing from list"
                    } else {
                        "Remove from list"
                    },
                    modifier = Modifier.size(24.dp),
                    tint = when {
                        isLoading -> Theme.color.surfaces.onSurface.copy(alpha = 0.6f)
                        isEnabled -> Theme.color.system.error
                        else -> Theme.color.surfaces.onSurface.copy(alpha = 0.6f)
                    }
                )
            }
            MovioText(
                text = stringResource(R.string.removing_from_lists),
                textStyle = Theme.textStyle.label.smallRegular14,
                color = when {
                    isLoading -> Theme.color.surfaces.onSurface.copy(alpha = 0.6f)
                    isEnabled -> Theme.color.system.error
                    else -> Theme.color.surfaces.onSurface.copy(alpha = 0.6f)
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RemoveFromListItemPreview() {
    RemoveFromListItem(
        onRemoveFromList = {},
        isEnabled = true,
        isLoading = false
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RemoveFromListItemLoadingPreview() {
    RemoveFromListItem(
        onRemoveFromList = {},
        isEnabled = true,
        isLoading = true
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RemoveFromListItemDisabledPreview() {
    RemoveFromListItem(
        onRemoveFromList = {},
        isEnabled = false,
        isLoading = false
    )
}