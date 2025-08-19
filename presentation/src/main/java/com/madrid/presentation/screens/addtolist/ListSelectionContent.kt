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