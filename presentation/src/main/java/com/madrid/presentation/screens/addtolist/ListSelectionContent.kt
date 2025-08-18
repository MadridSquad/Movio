package com.madrid.presentation.screens.addtolist

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.domain.entity.WatchList

@Composable
fun ListSelectionContent(
    initialUserLists: List<WatchList>,
    movieId: Int,
    movieInLists: Map<Int, List<Int>>,
    isLoading: Boolean = false,
    onCreateNewListClick: () -> Unit,
    onSelectionChanged: (WatchList, Boolean) -> Unit,
    onDeleteModeClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    // Process lists to set correct isSelected state based on whether movie is in each list
    val processedLists = remember(initialUserLists, movieId, movieInLists) {
        initialUserLists.map { list ->
            list.copy(
                isSelected = movieInLists[movieId]?.contains(list.id) == true,
                isLoading = false
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
                color = Theme.color.brand.primary
            )

        }

        if (isLoading) {

            MovioIcon(
                painter = painterResource(id = com.madrid.designSystem.R.drawable.loading),
                contentDescription = "Loading",
                tint = Theme.color.surfaces.onSurfaceContainer,
                modifier = Modifier.size(24.dp)
            )

            // Lists
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
                                // Update local state immediately for UI responsiveness
                                userLists = userLists.map {
                                    if (it.id == list.id) {
                                        it.copy(
                                            isSelected = !it.isSelected,
                                            isLoading = true // Show loading on this item
                                        )
                                    } else it
                                }

                                // Notify parent with the new selection state
                                onSelectionChanged(list, !list.isSelected)
                            }
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MovioText(
                        text = "No lists available",
                        textStyle = Theme.textStyle.body.mediumMedium14,
                        color = Theme.color.surfaces.onSurfaceContainer
                    )
                }
            }
        }
    }
}