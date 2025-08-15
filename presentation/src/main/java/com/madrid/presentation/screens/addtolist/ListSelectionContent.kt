package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.madrid.domain.entity.WatchList

@Composable
fun ListSelectionContent(
    initialUserLists: List<WatchList> = emptyList(),
    isLoading: Boolean = false,
    onCreateNewListClick: () -> Unit = {},
    onSelectionChanged: ((WatchList, Boolean) -> Unit)? = null
) {
    var listsWithLocalState by remember { mutableStateOf(initialUserLists) }

    LaunchedEffect(initialUserLists) {
        listsWithLocalState = initialUserLists.map { newList ->
            val existingList = listsWithLocalState.find { it.id == newList.id }
            if (existingList?.isLoading == true && !newList.isLoading) {
                newList
            } else {
                newList
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        CreateNewListItem(
            isLoading = isLoading,
            onListCreated = onCreateNewListClick,
        )

        if (listsWithLocalState.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(
                    items = listsWithLocalState,
                    key = { it.id }
                ) { userList ->
                    UserListItem(
                        userList = userList,
                        isGlobalLoading = isLoading,
                        onToggleSelection = { toggledList ->
                            listsWithLocalState = listsWithLocalState.map { list ->
                                if (list.id == toggledList.id) {
                                    list.copy(
                                        isLoading = true,
                                        isSelected = !list.isSelected
                                    )
                                }
                                else {
                                    list
                                }
                            }
                            val isNowSelected = !toggledList.isSelected
                            onSelectionChanged?.invoke(toggledList, isNowSelected)
                        }
                    )
                }
            }
        }
    }
}