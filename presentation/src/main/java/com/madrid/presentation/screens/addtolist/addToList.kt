// ListManagementBottomSheet.kt
package com.madrid.presentation.screens.addtolist

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioBottomSheet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    var showSuccessNotification by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }
    var bottomSheetVisible by remember(isVisible) { mutableStateOf(isVisible) }

    // Reset mode when bottom sheet becomes visible
    LaunchedEffect(isVisible) {
        if (isVisible) {
            currentMode = ListBottomSheetMode.LIST_SELECTION
            bottomSheetVisible = true
        } else {
            bottomSheetVisible = false
        }
    }

    // Handle showing success notification when lists are selected
    LaunchedEffect(initialUserLists) {
        val selectedLists = initialUserLists.filter { it.isSelected }
        if (selectedLists.isNotEmpty() && isVisible) {
            successMessage = when (selectedLists.size) {
                1 -> "Successfully added to ${selectedLists.first().name}."
                else -> "Successfully added to ${selectedLists.size} lists."
            }
            // Close bottom sheet first
            bottomSheetVisible = false
            // Small delay to ensure bottom sheet closes, then show notification
            delay(200)
            showSuccessNotification = true
            // Close the parent bottom sheet
            onDismiss()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Bottom Sheet
        MovioBottomSheet(
            show = bottomSheetVisible,
            onDismiss = {
                bottomSheetVisible = false
                currentMode = ListBottomSheetMode.LIST_SELECTION
                onDismiss()
            },
            containerColor = if (currentMode == ListBottomSheetMode.CREATE_NEW_LIST)
                Color.Transparent else com.madrid.designSystem.theme.Theme.color.surfaces.surface,
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
                            onSelectionChanged = { userList, isSelected ->
                                onSelectionChanged?.invoke(userList, isSelected)
                                if (isSelected) {
                                    successMessage = "Successfully added to ${userList.name}."
                                    // Close bottom sheet immediately
                                    bottomSheetVisible = false
                                    // Show notification after a brief delay
                                    kotlinx.coroutines.MainScope().launch {
                                        delay(200)
                                        showSuccessNotification = true
                                        onDismiss()
                                    }
                                }
                            }
                        )
                    }
                    ListBottomSheetMode.CREATE_NEW_LIST -> {
                        CreateListBottomSheet(
                            show = true,
                            onCreateClick = { listName ->
                                onListCreated(listName)
                                successMessage = "Successfully created list: $listName"
                                // Close bottom sheet immediately
                                bottomSheetVisible = false
                                // Show notification after a brief delay
                                kotlinx.coroutines.MainScope().launch {
                                    delay(200)
                                    showSuccessNotification = true
                                    onDismiss()
                                }
                            },
                            onDismiss = {
                                currentMode = ListBottomSheetMode.LIST_SELECTION
                            },
                        )
                    }
                }
            }
        }

        // Success notification - positioned at bottom of screen, outside bottom sheet
        if (showSuccessNotification) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                SuccessNotificationRow(
                    isVisible = showSuccessNotification,
                    message = successMessage,
                    onDismiss = { showSuccessNotification = false }
                )
            }
        }
    }
}