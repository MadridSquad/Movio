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
import com.madrid.designSystem.theme.Theme
import com.madrid.domain.entity.WatchList
import com.madrid.presentation.viewModel.libraryViewModel.addtolist.MovieListViewModel
import kotlinx.coroutines.delay

enum class ListBottomSheetMode {
    LIST_SELECTION,
    CREATE_NEW_LIST,
    REMOVE_FROM_LIST
}

@Composable
fun ListManagementBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    movieId: Int,
    movieListIds: List<WatchList> = emptyList(), // Added missing parameter
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
            viewModel.loadUserLists()
        } else {
            bottomSheetVisible = false
        }
    }

    // Handle success states
    LaunchedEffect(
        uiState.createListSuccess,
        uiState.addToListSuccess,
        uiState.removeFromListSuccess
    ) {
        when {
            uiState.createListSuccess -> {
                successMessage = uiState.successMessage ?: "List created successfully"
                showSuccessNotification = true
                delay(2000)
                showSuccessNotification = false
                viewModel.clearSuccess()
            }
            uiState.addToListSuccess -> {
                successMessage = uiState.successMessage ?: "Added to list successfully"
                showSuccessNotification = true
                delay(2000)
                showSuccessNotification = false
                viewModel.clearSuccess()
                // Don't dismiss - user might want to add to more lists
            }
            uiState.removeFromListSuccess -> {
                successMessage = uiState.successMessage ?: "Removed from list successfully"
                showSuccessNotification = true
                delay(2000)
                showSuccessNotification = false
                viewModel.clearSuccess()
            }
        }
    }

    // Handle error messages
    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            delay(3000)
            viewModel.clearError()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        MovioBottomSheet(
            show = bottomSheetVisible,
            onDismiss = {
                bottomSheetVisible = false
                currentMode = ListBottomSheetMode.LIST_SELECTION
                onDismiss()
            },
            containerColor = if (currentMode == ListBottomSheetMode.CREATE_NEW_LIST)
                Color.Transparent else Theme.color.surfaces.surface,
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
                            initialUserLists = uiState.userLists,
                            isLoading = uiState.isLoading,
                            onCreateNewListClick = {
                                currentMode = ListBottomSheetMode.CREATE_NEW_LIST
                            },
                            onSelectionChanged = { watchList, isSelected ->
                                if (isSelected) {
                                    viewModel.addMovieToList(
                                         listId = watchList.id,
                                        movieId = movieId,
                                        onSuccess = {
                                            successMessage = "Added to ${watchList.name} successfully"
                                            showSuccessNotification = true
                                        }
                                    )
                                } else {
                                    viewModel.removeMovieFromList(mediaId = movieId, listId = watchList.id)
                                }
                            }
                        )
                    }

                    ListBottomSheetMode.CREATE_NEW_LIST -> {
                        CreateListBottomSheet(
                            show = true,
                            onCreateClick = { listName ->
                                viewModel.createMovieList(name = listName)
                            },
                            onDismiss = {
                                currentMode = ListBottomSheetMode.LIST_SELECTION
                            }
                        )
                    }

                    ListBottomSheetMode.REMOVE_FROM_LIST -> {
                        RemoveFromListItem(
                            onRemoveFromList = {
                                movieListIds.forEach { watchList ->
                                    viewModel.removeMovieFromList(
                                        mediaId = movieId,
                                        listId = watchList.id
                                    )
                                }
                                currentMode = ListBottomSheetMode.LIST_SELECTION
                            },
                            isEnabled = !uiState.isLoading,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        // Success notification
        if (showSuccessNotification) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                SuccessNotificationRow(
                    message = successMessage ?: "Operation successful",
                    isVisible = showSuccessNotification,
                    onDismiss = {
                        showSuccessNotification = false
                    }
                )
            }
        }

        // Error handling (if you want to display errors)
        uiState.errorMessage?.let { errorMessage ->
            // You can implement error display here
            // For example, show a toast or error dialog
        }
    }
}