package com.madrid.presentation.screens.removefromlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.domain.entity.WatchList
import com.madrid.presentation.viewModel.libraryViewModel.addtolist.WatchListItemUiState

@Composable
fun RemoveFromListContent(
    movieId: Int,

    userListsContainingMovie: List<WatchListItemUiState> = emptyList(),
    isLoading: Boolean = false,
    onRemoveFromListClick: ((WatchList) -> Unit)? = null
) {
    var listsWithLocalState by remember { mutableStateOf(userListsContainingMovie) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        if (listsWithLocalState.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(
                    items = listsWithLocalState,
                    key = { it.id }
                ) { userList ->
                    RemoveFromListItem(
                        userList = userList,
                        movieId = movieId,
                        isGlobalLoading = isLoading,
                        onRemoveClick = { listToRemoveFrom ->
                            listsWithLocalState = listsWithLocalState.map { list ->
                                if (list.id == listToRemoveFrom.id) {
                                    list.copy(
                                        isLoading = true,
                                        itemCount = (list.itemCount ?: 0) - 1
                                    )
                                } else {
                                    list
                                }
                            }
                            onRemoveFromListClick?.invoke(listToRemoveFrom)
                        }
                    )
                }
            }
        } else {
            EmptyListsMessage()
        }
    }
}

@Composable
private fun RemoveFromListItem(
    userList: WatchListItemUiState,
    movieId: Int,
    isGlobalLoading: Boolean,
    onRemoveClick: (WatchList) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // List info (poster, title, count)
            Column(modifier = Modifier.weight(1f)) {
                MovioText(
                    text = userList.id.toString(),
                    textStyle = Theme.textStyle.label.smallRegular14
                )
                MovioText(
                    text = "${userList.itemCount ?: 0} items",
                    textStyle = Theme.textStyle.label.smallRegular14
                )
            }

            }
        }
    }
@Composable
private fun EmptyListsMessage() {
    // Implementation for when movie is not in any lists:
    /*
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.PlaylistRemove,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Movie not in any lists",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "This movie hasn't been added to any of your lists yet.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
    */
}