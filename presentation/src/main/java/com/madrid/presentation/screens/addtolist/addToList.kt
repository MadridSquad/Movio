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
import com.madrid.domain.entity.WatchList
import com.madrid.presentation.viewModel.libraryViewModel.addtolist.MovieListViewModel
import kotlinx.coroutines.delay

enum class ListBottomSheetMode {
    LIST_SELECTION,
    CREATE_NEW_LIST,
    DELETE_MOVIE_FROM_LIST,
}

@Composable
fun ListManagementBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    movieId: Int,
    movieListIds: List<WatchList>, // Pass the list of IDs that contain this movie
    viewModel: MovieListViewModel = hiltViewModel(),
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

    LaunchedEffect(uiState.createListSuccess, uiState.addToListSuccess, uiState.errorMessage) {
        when {
            uiState.createListSuccess -> {
                successMessage = uiState.successMessage
                showSuccessNotification = true
                delay(2000) // Show notification for 2 seconds
                showSuccessNotification = false
                viewModel.clearSuccess()
            }
            uiState.addToListSuccess -> {
                successMessage = uiState.successMessage
                showSuccessNotification = true
                delay(2000)
                showSuccessNotification = false
                viewModel.clearSuccess()
                onDismiss()
            }
            uiState.errorMessage != null -> {
                delay(3000)
                viewModel.clearError()
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        MovioBottomSheet(
            show = bottomSheetVisible,
            onDismiss = {
                bottomSheetVisible = false
                currentMode = ListBottomSheetMode.LIST_SELECTION
                onDismiss()
            }
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
                    ListBottomSheetMode.DELETE_MOVIE_FROM_LIST -> {
                        ListSelectionContent(
                            userLists = uiState.userLists,
                            isLoading = uiState.isLoadingLists,
                            mode = ListSelectionMode.DELETE_FROM_LIST,
                            movieId = movieId,
                            movieListIds = movieListIds.map { it.id }, // Convert to List<Int>
                            onCreateNewListClick = {
                                currentMode = ListBottomSheetMode.CREATE_NEW_LIST
                            },
                            onSelectionChanged = { list, isSelected ->
                                if (isSelected) {
                                    viewModel.removeMovieFromList(mediaId = movieId, listId = list.id)
                                }
                            },
                            onRemoveFromList = { movieId, listId ->
                                viewModel.removeMovieFromList(mediaId = movieId, listId = listId)
                            }
                        )
                    }
                    ListBottomSheetMode.LIST_SELECTION -> {
                        ListSelectionContent(
                            userLists = uiState.userLists,
                            isLoading = uiState.isLoadingLists,
                            mode = ListSelectionMode.ADD_TO_LIST,
                            movieId = movieId,
                            movieListIds = movieListIds.map { it.id }, // Convert to List<Int>
                            onCreateNewListClick = {
                                currentMode = ListBottomSheetMode.CREATE_NEW_LIST
                            },
                            onSelectionChanged = { list, isSelected ->
                                if (isSelected) {
                                    viewModel.addMovieToList(listId = list.id,
                                        movieId = movieId)
                                } else {
                                    viewModel.removeMovieFromList(mediaId = movieId, listId = list.id)
                                }
                            },
                            onAddToList = { movieId, listId ->
                                viewModel.addMovieToList(
                                    listId = listId,
                                    movieId = movieId
                                )
                            },
                            onRemoveFromList = { movieId, listId ->
                                viewModel.removeMovieFromList(mediaId = movieId, listId = listId)
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
                    message = successMessage ?: "Operation successful",
                    isVisible = showSuccessNotification,
                    onDismiss = { showSuccessNotification = false }
                )
            }
        }
    }
}