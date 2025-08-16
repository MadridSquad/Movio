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

enum class ListSelectionMode {
    ADD_TO_LIST,
    DELETE_FROM_LIST
}

@Composable
fun ListSelectionContent(
    initialUserLists: List<WatchList> ,
    isLoading: Boolean = false,
    mode: ListSelectionMode = ListSelectionMode.ADD_TO_LIST,
    movieId: Int? = null,
    onCreateNewListClick: () -> Unit = {},
    onSelectionChanged: ((WatchList, Boolean) -> Unit)? = null,
    onRemoveFromList: ((Int, Int) -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        if (mode == ListSelectionMode.ADD_TO_LIST) {
            CreateNewListItem(
                onListCreated = onCreateNewListClick,
                isLoading = isLoading
            )
        }

        if (initialUserLists.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(initialUserLists) { userList ->
                    UserListItem(
                        userList = userList,
                        onToggleSelection = { toggledList ->
                            if (!isLoading && !toggledList.isLoading) {
                                when (mode) {
                                    ListSelectionMode.ADD_TO_LIST -> {
                                        onSelectionChanged?.invoke(toggledList, true)
                                    }
                                    ListSelectionMode.DELETE_FROM_LIST -> {
                                        movieId?.let { id ->
                                            onRemoveFromList?.invoke(id, toggledList.id)
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}