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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.presentation.viewModel.libraryViewModel.addtolist.MovieListViewModel
import kotlinx.coroutines.delay

enum class ListBottomSheetMode {
    LIST_SELECTION,
    CREATE_NEW_LIST
}

@Composable
fun ListManagementBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    movieId: Int,
    viewModel: MovieListViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.state.collectAsState()

    var currentMode by remember { mutableStateOf(ListBottomSheetMode.LIST_SELECTION) }
    var showSuccessNotification by remember { mutableStateOf(false) }
    var successMessage: String? by remember { mutableStateOf("") }
    var bottomSheetVisible by remember(isVisible) { mutableStateOf(isVisible) }

    // Load user lists when bottom sheet becomes visible
    LaunchedEffect(isVisible) {
        if (isVisible) {
            currentMode = ListBottomSheetMode.LIST_SELECTION
            bottomSheetVisible = true
            // THIS IS THE KEY FIX - Actually load the lists!
            viewModel.loadUserLists()
        } else {
            bottomSheetVisible = false
        }
    }

    LaunchedEffect(uiState.createListSuccess) {
        if (uiState.createListSuccess && uiState.successMessage != null) {
            successMessage = uiState.successMessage
            bottomSheetVisible = false
            delay(200)
            showSuccessNotification = true
            onDismiss()
            viewModel.clearSuccess()
        }
    }

    LaunchedEffect(uiState.addToListSuccess) {
        if (uiState.addToListSuccess && uiState.successMessage != null) {
            successMessage = uiState.successMessage
            bottomSheetVisible = false
            delay(200)
            showSuccessNotification = true
            onDismiss()
            viewModel.clearSuccess()
        }
    }

    // Handle error messages
    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            // Show error message (you might want to show a toast or error dialog)
            // For now, we'll just clear it after showing
            delay(3000)
            viewModel.clearError()
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
                            initialUserLists = uiState.userLists, // Use lists from ViewModel state
                            isLoading = uiState.isLoadingLists, // Use loading state from ViewModel
                            onCreateNewListClick = {
                                currentMode = ListBottomSheetMode.CREATE_NEW_LIST
                            },
                            onSelectionChanged = { userList, isSelected ->
                                if (isSelected) {
                                    viewModel.addMovieToList(
                                        listId = userList.id.toInt(),
                                        movieId = movieId
                                    )
                                }
                            }
                        )
                    }

                    ListBottomSheetMode.CREATE_NEW_LIST -> {
                        CreateListBottomSheet(
                            show = true,
                            onCreateClick = { listName ->
                                viewModel.createMovieList(
                                    name = listName
                                )
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
                    onDismiss = {
                        showSuccessNotification = false
                    }
                )
            }
        }

        uiState.errorMessage?.let { errorMessage ->
            // You can implement error display here
            // For example, show a toast or error dialog
        }
    }
}