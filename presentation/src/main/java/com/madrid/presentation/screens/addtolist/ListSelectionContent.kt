package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.domain.entity.WatchList

@Composable
fun ListSelectionContent(
    initialUserLists: List<WatchList>,
    isLoading: Boolean = false,
    onCreateNewListClick: () -> Unit = {},
    onSelectionChanged: ((WatchList, Boolean) -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        CreateNewListItem(
            onListCreated = onCreateNewListClick,
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