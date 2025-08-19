package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.domain.entity.WatchList

@Composable
fun RemoveFromListBottomSheet(
    userLists: List<WatchList>,
    movieId: Int,
    isLoading: Boolean = false,
    onDeleteConfirmed: (WatchList) -> Unit,
    onDismiss: () -> Unit
) {
    val listsContainingMovie = userLists.filter {
        it.movieIds.contains(movieId) || it.isSelected
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Header
        MovioText(
            text = "Remove from which list?",
            textStyle = Theme.textStyle.body.smallRegular16,
            textAlign = TextAlign.Center,
            color = Theme.color.surfaces.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (listsContainingMovie.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(listsContainingMovie) { userList ->
                    RemoveListItem(
                        userList = userList,
                        movieId = movieId,
                        isLoading = isLoading || userList.isLoading,
                        onRemoveClick = { onDeleteConfirmed(userList) }
                    )
                }
            }
        } else {
            // Empty state
            MovioText(
                text = "This movie is not in any of your lists",
                textStyle = Theme.textStyle.body.mediumMedium14,
                textAlign = TextAlign.Center,
                color = Theme.color.surfaces.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 24.dp)
            )
        }
    }
}

// Separate component for remove list items
@Composable
fun RemoveListItem(
    userList: WatchList,
    movieId: Int,
    isLoading: Boolean = false,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable(
                enabled = !isLoading,
                role = Role.Button,
                onClickLabel = "Remove from ${userList.name}"
            ) {
                if (!isLoading) {
                    onRemoveClick()
                }
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MovioText(
            text = userList.name,
            textStyle = Theme.textStyle.body.mediumMedium14,
            color = if (!isLoading) {
                Theme.color.surfaces.onSurface
            } else {
                Theme.color.surfaces.onSurface.copy(alpha = 0.6f)
            },
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    MovioIcon(
                        painter = painterResource(id = R.drawable.loading),
                        contentDescription = "Removing from list",
                        tint = Theme.color.surfaces.onSurfaceContainer,
                        modifier = Modifier.size(24.dp)
                    )
                }
                else -> {
                    MovioIcon(
                        painter = painterResource(id = R.drawable.bold_check_circle),
                        contentDescription = "Remove from ${userList.name}",
                        tint = Theme.color.system.error,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}