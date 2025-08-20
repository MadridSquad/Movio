package com.madrid.presentation.screens.addtolist

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madrid.designSystem.R.drawable
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.domain.entity.MovieListUiState
import com.madrid.domain.entity.WatchList
import com.madrid.presentation.R
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.detailsViewModel.DetailsMovieViewModel
import com.madrid.presentation.viewModel.libraryViewModel.addtolist.MovieListViewModel
import kotlinx.coroutines.delay

enum class ListBottomSheetMode {
    LIST_SELECTION,
    CREATE_NEW_LIST,
}

@Composable
fun ListManagementBottomSheet(
    viewModel: MovieListViewModel = hiltViewModel(),
    viewModels: DetailsMovieViewModel = hiltViewModel(),
    isVisible: Boolean,
    onDismiss: () -> Unit,
    movieId: Int,
    modifier: Modifier = Modifier
) {
    val navController = LocalNavController.current
    val uiState by viewModel.state.collectAsState()
    val movieUiState by viewModels.state.collectAsState()
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
            if (movieUiState.isGuest) {
                Column {
                    Image(
                        painter = painterResource(id = drawable.library_main_icon),
                        contentDescription = "library main icon",
                        modifier = Modifier
                            .size(width = 60.dp, height = 66.dp)
                            .align(CenterHorizontally)
                            .padding(bottom = 16.dp),
                    )
                    MovioText(
                        text = stringResource(R.string.you_dont_have_an_account),
                        textStyle = Theme.textStyle.title.mediumMedium16,
                        color = Theme.color.surfaces.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    MovioText(
                        text = stringResource(R.string.this_rating_is_only_available_to_registered_users_Login_to_share_your_rating),
                        textStyle = Theme.textStyle.label.smallRegular12,
                        color = Theme.color.surfaces.onSurfaceContainer,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 40.dp,
                                bottom = 32.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                            .height(48.dp),
                        onClick = {
                            bottomSheetVisible = false
                            onDismiss()
                            navController.navigate(Destinations.LoginScreen)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Theme.color.brand.primary,
                        ),
                        shape = RoundedCornerShape(24.dp),
                        elevation = ButtonDefaults.elevation(0.dp)
                    ) {
                        MovioText(
                            text = stringResource(R.string.login),
                            textStyle = Theme.textStyle.label.mediumMedium14,
                            color = Theme.color.brand.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            } else {
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
                    onDismiss = {
                        showSuccessNotification = false
                        successMessage = null
                    }
                )
            }
        }

        uiState.errorMessage?.let { errorMessage ->
        }
    }
}