package com.madrid.presentation.component.addtolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.viewModel.addtolist.WatchListItemUiState

@Composable
fun ListSelectionContent(
    initialUserLists: List<WatchListItemUiState>,
    isLoading: Boolean = false,
    onCreateNewListClick: () -> Unit = {},
    onSelectionChanged: ((WatchListItemUiState, Boolean) -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CreateNewListItem(
            onListCreated = onCreateNewListClick,
        )
        Divider(
            color = Theme.color.surfaces.surfaceContainer,
            thickness = 1.dp,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),

        )
        if (initialUserLists.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(initialUserLists) { userList ->
                    UserListItem(
                        userList = userList,
                        onToggleSelection = { toggledList ->
                            if (!isLoading && !toggledList.isLoading) {
                                onSelectionChanged?.invoke(toggledList, true)
                            }
                        }
                    )
                }
            }
        }
    }
}