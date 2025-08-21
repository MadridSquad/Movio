package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.domain.entity.WatchList
import com.madrid.presentation.viewModel.libraryViewModel.addtolist.WatchListItemUiState
import com.madrid.presentation.viewModel.shared.WatchListUiState

@Composable
fun UserListItem(
    userList: WatchListItemUiState,
    isGlobalLoading: Boolean = false,
    onToggleSelection: (WatchListItemUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    val isItemEnabled = !isGlobalLoading && !userList.isLoading
    val isItemLoading = isGlobalLoading || userList.isLoading

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable(
                enabled = isItemEnabled,
                role = Role.Checkbox,
                onClickLabel = if (userList.isSelected) "Remove from list" else "Add to list"
            ) {
                if (isItemEnabled) {
                    onToggleSelection(userList)
                }
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MovioText(
            text = userList.watchListTitle,
            textStyle = Theme.textStyle.body.mediumMedium14,
            color = if (isItemEnabled) {
                Theme.color.surfaces.onSurface
            } else {
                Theme.color.surfaces.onSurface.copy(alpha = 0.6f)
            },
            modifier = Modifier.height(17.dp)
        )

        Box(
            modifier = Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                isItemLoading -> {
                    MovioIcon(
                        painter = painterResource(id = R.drawable.loading),
                        contentDescription = "Loading",
                        tint = Theme.color.surfaces.onSurfaceContainer,
                        modifier = Modifier.size(24.dp)
                    )
                }

                userList.isSelected -> {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        MovioIcon(
                            painter = painterResource(id = R.drawable.bold_check_circle),
                            contentDescription = "Added to list",
                            tint = Theme.color.brand.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                else -> {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        MovioIcon(
                            painter = painterResource(id = com.madrid.presentation.R.drawable.ic_add_continer),
                            contentDescription = "Add to list",
                            modifier = Modifier.size(24.dp),
                            tint = if (isItemEnabled) {
                                Theme.color.surfaces.onSurface
                            } else {
                                Theme.color.surfaces.onSurface.copy(alpha = 0.6f)
                            }
                        )
                    }
                }
            }
        }
    }
}