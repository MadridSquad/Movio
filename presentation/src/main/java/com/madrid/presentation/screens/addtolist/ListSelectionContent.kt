package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.domain.entity.WatchList


@Composable
fun ListSelectionContent(
    initialUserLists: List<WatchList>,
    movieId: Int,
    isLoading: Boolean = false,
    onCreateNewListClick: () -> Unit,
    onSelectionChanged: (WatchList, Boolean) -> Unit,
    onDeleteModeClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val processedLists = remember(initialUserLists, movieId) {
        initialUserLists.map { list ->
            list.copy(
                isSelected = list.movies.any { it.id == movieId },
                isLoading = false // Reset loading state for initial display
            )
        }
    }

    var userLists by remember(processedLists) { mutableStateOf(processedLists) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MovioText(
                text = "Add to List",
                textStyle = Theme.textStyle.body.mediumMedium14,
                color = Theme.color.surfaces.onSurface,
            )

        }

        if (isLoading) {
            MovioIcon(
                painter = painterResource(id = R.drawable.loading),
                contentDescription = "Loading",
                tint = Theme.color.surfaces.onSurfaceContainer,
                modifier = Modifier.size(24.dp)
            )
            if (userLists.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(userLists) { userList ->
                        UserListItem(
                            userList = userList,
                            isGlobalLoading = isLoading,
                            onToggleSelection = { list ->
                                userLists = userLists.map {
                                    if (it.id == list.id) {
                                        it.copy(
                                            isSelected = !it.isSelected,
                                            isLoading = true
                                        )
                                    } else it
                                }
                                onSelectionChanged(list, !list.isSelected)
                            }
                        )
                    }
                }
            }
        }
    }
}
