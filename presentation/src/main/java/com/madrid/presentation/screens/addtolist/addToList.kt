// ListManagementBottomSheet.kt
package com.madrid.presentation.screens.addtolist

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.madrid.designSystem.component.MovioBottomSheet

data class UserList(
    val id: String,
    val name: String,
    var isSelected: Boolean = false,
    var isLoading: Boolean = false
)

enum class ListBottomSheetMode {
    LIST_SELECTION,
    CREATE_NEW_LIST
}

@Composable
fun ListManagementBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    initialUserLists: List<UserList>,
    onListCreated: (String) -> Unit,
    onSelectionChanged: ((UserList, Boolean) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var currentMode by remember { mutableStateOf(ListBottomSheetMode.LIST_SELECTION) }

    MovioBottomSheet(
        show = isVisible,
        onDismiss = {
            currentMode = ListBottomSheetMode.LIST_SELECTION
            onDismiss()
        },
        containerColor = if (currentMode == ListBottomSheetMode.CREATE_NEW_LIST) Color.Transparent else Color(0xFF1A1B23)
    ) {
        AnimatedContent(
            targetState = currentMode,
            transitionSpec = {
                slideInHorizontally(initialOffsetX = { if (targetState == ListBottomSheetMode.CREATE_NEW_LIST) it else -it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { if (targetState == ListBottomSheetMode.CREATE_NEW_LIST) -it else it })
            },
            label = "ListBottomSheetAnimation"
        ) { mode ->
            when (mode) {
                ListBottomSheetMode.LIST_SELECTION -> {
                    ListSelectionContent(
                        initialUserLists = initialUserLists,
                        onCreateNewListClick = {
                            currentMode = ListBottomSheetMode.CREATE_NEW_LIST
                        },
                        onSelectionChanged = onSelectionChanged
                    )
                }
                ListBottomSheetMode.CREATE_NEW_LIST -> {
                    CreateListContent(
                        onCreateClick = { listName ->
                            onListCreated(listName)
                            currentMode = ListBottomSheetMode.LIST_SELECTION
                        },
                    )
                }
            }
        }
    }
}


// Usage Example
@Preview(showBackground = true)
@Composable
fun PreviewListManagementBottomSheet() {
    ListManagementBottomSheet(
        isVisible = true,
        onDismiss = { },
        initialUserLists = listOf(
            UserList("1", "Watch later", isSelected = true),
            UserList("2", "Watching after exam", isSelected = false),
            UserList("3", "Watching soon", isSelected = false),
            UserList("4", "Adventure movies", isSelected = false)
        ),
        onListCreated = { listName ->
            println("Creating list: $listName")
        },
        onSelectionChanged = { userList, isSelected ->
            println("Selection changed: ${userList.name} -> $isSelected")
        }
    )
}