package com.madrid.presentation.screens.addtolist

import android.R.attr.textAlignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioButton
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
        // Header
        item {
            MovioText(
                text = "Add to list",
                textStyle = Theme.textStyle.body.mediumMedium14,
                color = Theme.color.surfaces.onSurface,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        // Create new list item
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