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
    initialUserLists: List<WatchList> = generateFakeWatchLists(), // Default to fake data
    isLoading: Boolean = false,
    onCreateNewListClick: () -> Unit = {}, // Default empty callback
    onSelectionChanged: ((WatchList, Boolean) -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        // Create New List Item
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

// Helper function to generate fake watch lists
private fun generateFakeWatchLists(): List<WatchList> {
    return listOf(
        WatchList(
            id = "1".toInt(),
            name = "Favorites",
            itemCount = 12,
            isLoading = false
        ),
        WatchList(
            id = "2".toInt(),
            name = "Watch Later",
            itemCount = 5,
            isLoading = false
        ),
        WatchList(
            id = "3".toInt(),
            name = "Recommended",
            itemCount = 8,
            isLoading = false
        ),
        WatchList(
            id = "4".toInt(),
            name = "TV Shows",
            itemCount = 15,
            isLoading = false
        ),
        WatchList(
            id = "5".toInt(),
            name = "Movies",
            itemCount = 23,
            isLoading = false
        )
    )
}

// Preview for Jetpack Compose (if you're using it)
@Preview(showBackground = true)
@Composable
fun ListSelectionContentPreview() {
    ListSelectionContent()
}