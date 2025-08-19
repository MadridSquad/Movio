package com.madrid.presentation.screens.addtolist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioButton
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.domain.entity.WatchList

@Composable
fun RemoveFromListBottomSheet(
    userLists: List<WatchList>,
    movieId: Int,
    isLoading: Boolean = false,
    onDeleteConfirmed: (WatchList) -> Unit,
    onDismiss: () -> Unit
) {
    val listsContainingMovie = userLists.filter { it.movieIds.contains(movieId) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (listsContainingMovie.isNotEmpty()) {
            MovioText(
                text = "Remove from which list?",
                textStyle = Theme.textStyle.body.mediumMedium14,
                textAlign = TextAlign.Center,
                color = Theme.color.surfaces.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            listsContainingMovie.forEach { userList ->
                UserListItem(
                    userList = userList,
                    isGlobalLoading = isLoading,
                    onToggleSelection = {
                        onDeleteConfirmed(userList)
                    }
                )
            }
        }

            MovioButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                MovioText(
                    text = "Close",
                    textStyle = Theme.textStyle.body.mediumMedium14
                )
            }
        }
    }