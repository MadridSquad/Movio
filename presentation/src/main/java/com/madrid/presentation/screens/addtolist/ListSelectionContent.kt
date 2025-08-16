package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.madrid.domain.entity.WatchList

enum class ListSelectionMode {
    ADD_TO_LIST,
    DELETE_FROM_LIST
}

@Composable
fun ListSelectionContent(
    initialUserLists: List<WatchList>,
    isLoading: Boolean = false,
    mode: ListSelectionMode = ListSelectionMode.ADD_TO_LIST,
    movieId: Int? = null,
    movieListIds: Set<Int> = emptySet(), // IDs of lists that already contain this movie
    onCreateNewListClick: () -> Unit = {},
    onSelectionChanged: ((WatchList, Boolean) -> Unit)? = null,
    onRemoveFromList: ((Int, Int) -> Unit)? = null,
    onAddToList: ((Int, Int) -> Unit)? = null // Separate callback for adding
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
                    val isMovieInList = movieListIds.contains(userList.id)

                    UserListItem(
                        userList = userList,
                        isSelected = isMovieInList,
                        onToggleSelection = { toggledList ->
                            if (!isLoading && !toggledList.isLoading) {
                                movieId?.let { id ->
                                    when (mode) {
                                        ListSelectionMode.ADD_TO_LIST -> {
                                            if (isMovieInList) {
                                                onRemoveFromList?.invoke(id, toggledList.id)
                                            } else {
                                                onAddToList?.invoke(id, toggledList.id)
                                                onSelectionChanged?.invoke(toggledList, true)
                                            }
                                        }
                                        ListSelectionMode.DELETE_FROM_LIST -> {
                                            if (isMovieInList) {
                                                onRemoveFromList?.invoke(id, toggledList.id)
                                            }
                                            onSelectionChanged?.invoke(toggledList, false)
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