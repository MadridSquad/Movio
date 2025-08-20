package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            CreateNewListItem(
                onListCreated = onCreateNewListClick,
                isEnabled = !isLoading
            )
        }
        items(initialUserLists) { userList ->
            UserListItem(
                userList = userList,
                movieId = movieId,
                isGlobalLoading = isLoading,
                onToggleSelection = { list, isSelected ->
                    onSelectionChanged(list, isSelected)
                }
            )
        }
    }
}