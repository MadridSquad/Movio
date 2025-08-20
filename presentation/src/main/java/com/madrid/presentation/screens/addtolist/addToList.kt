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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.viewModel.libraryViewModel.addtolist.MovieListViewModel
import kotlinx.coroutines.delay

enum class ListBottomSheetMode {
    LIST_SELECTION,
    CREATE_NEW_LIST,
}

@Composable
fun ListManagementBottomSheet(
    viewModel: MovieListViewModel = hiltViewModel(),
    isVisible: Boolean,
    onDismiss: () -> Unit,
    movieId: Int,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.state.collectAsState()
    var currentMode by remember { mutableStateOf(ListBottomSheetMode.LIST_SELECTION) }
    var showSuccessNotification by remember { mutableStateOf(false) }
    var successMessage: String? by remember { mutableStateOf("") }
    var bottomSheetVisible by remember(isVisible) { mutableStateOf(isVisible) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            currentMode = ListBottomSheetMode.LIST_SELECTION
            bottomSheetVisible = true
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

    LaunchedEffect(uiState.removeFromListSuccess) {
        if (uiState.removeFromListSuccess && uiState.successMessage != null) {
            successMessage = uiState.successMessage
            bottomSheetVisible = false
            delay(200)
            showSuccessNotification = true
            onDismiss()
            viewModel.clearSuccess()
        }
    }

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
                onDismiss()
            },
            containerColor = Theme.color.surfaces.surface
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
                            isLoading = uiState.isLoadingLists,
                            onCreateNewListClick = {
                                currentMode = ListBottomSheetMode.CREATE_NEW_LIST
                            },
                            onSelectionChanged = { userList, isSelected ->
                                if (!userList.movieIds.contains(movieId)) {
                                    viewModel.addMovieToList(
                                        listId = userList.id,
                                        movieId = movieId
                                    )
                                } else {
                                    viewModel.removeMovieFromList(
                                        listId = userList.id,
                                        movieId = movieId
                                    )
                                }
                            },
                            movieId = movieId,
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