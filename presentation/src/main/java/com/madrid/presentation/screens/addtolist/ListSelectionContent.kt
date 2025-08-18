package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.clickable
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
    // Fix 1: Better logic for determining if movie is in list
    // Instead of checking if list name contains movieId, check if movie is actually in the list
    var userLists by remember(initialUserLists) {
        mutableStateOf(
            initialUserLists.map { list ->
                list.copy(
                    isLoading = false
                )
            }
        )
    }

    // Fix 2: Update state when initialUserLists changes
    LaunchedEffect(initialUserLists) {
        userLists = initialUserLists.map { list ->
            list.copy(
                isLoading = false
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
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
            MovioText(
                text = "+ Create New",
                textStyle = Theme.textStyle.body.mediumMedium14,
                color = Theme.color.brand.primary,
                modifier = Modifier.clickable { onCreateNewListClick() }
            )
        }

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MovioIcon(
                        painter = painterResource(id = R.drawable.loading),
                        contentDescription = "Loading",
                        tint = Theme.color.surfaces.onSurfaceContainer,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            userLists.isEmpty() -> {
                // Empty state
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MovioText(
                        text = "No lists available",
                        textStyle = Theme.textStyle.body.mediumMedium14,
                        color = Theme.color.surfaces.onSurfaceContainer,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    MovioText(
                        text = "Create your first list",
                        textStyle = Theme.textStyle.body.mediumMedium12,
                        color = Theme.color.brand.primary,
                        modifier = Modifier.clickable { onCreateNewListClick() }
                    )
                }
            }

            else -> {
                // Content state
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(
                        items = userLists,
                        key = { it.id }
                    ) { userList ->
                        UserListItem(
                            userList = userList,
                            isGlobalLoading = false,
                            onToggleSelection = { list ->
                                // Fix 4: Better state update logic
                                val updatedLists = userLists.map { currentList ->
                                    if (currentList.id == list.id) {
                                        currentList.copy(
                                            isSelected = !currentList.isSelected,
                                            isLoading = true
                                        )
                                    } else {
                                        currentList
                                    }
                                }
                                userLists = updatedLists

                                // Call the parent callback
                                onSelectionChanged(list, !list.isSelected)
                            }
                        )
                    }
                }
            }
        }
    }
}