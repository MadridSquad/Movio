package com.madrid.presentation.component.logout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.MovioButton
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.viewModel.logoutViewModel.LogoutUiState
import com.madrid.presentation.viewModel.logoutViewModel.LogoutViewModel

@Composable
fun LogoutConfirmationBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onNavigateToAuth: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.conform_log_out),
    description: String = stringResource(id = R.string.log_out),
    actionButtonText: String = stringResource(id = R.string.logout),
) {
    val viewModel: LogoutViewModel = hiltViewModel()
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(uiState.logoutSuccess) {
        if (uiState.logoutSuccess) {
            onDismiss()
            onNavigateToAuth()
        }
    }

    MovioBottomSheet(
        show = isVisible,
        onDismiss = {
            if (!uiState.isLoading) {
                viewModel.clearError()
                onDismiss()
            }
        },
        containerColor = Theme.color.surfaces.surface
    ) {
        LogoutConfirmationContent(
            uiState = uiState,
            onLogoutConfirm = {
                viewModel.logout(onSuccess = {})
            },
            onClearError = { viewModel.clearError() },
            modifier = modifier,
            title = title,
            description = description,
            textButton = actionButtonText,
        )
    }
}

@Composable
private fun LogoutConfirmationContent(
    uiState: LogoutUiState,
    onLogoutConfirm: () -> Unit,
    onClearError: () -> Unit,
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    textButton: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // App Icon Section
        LogoutIconSection()

        // Text Content Section
        LogoutTextSection(
            title = title,
            description = description
        )

        // Error Message Section
        if (uiState.errorMessage != null) {
            Spacer(modifier = Modifier.height(16.dp))
            ErrorMessageSection(errorMessage = uiState.errorMessage)
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Action Button Section
        LogoutActionButton(
            isLoading = uiState.isLoading,
            onLogoutConfirm = onLogoutConfirm,
            onClearError = onClearError,
            textButton = textButton
        )
    }
}

@Composable
private fun LogoutIconSection() {
    Box(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .size(60.dp, 66.dp),
        contentAlignment = Alignment.Center
    ) {
        MovioIcon(
            painter = painterResource(R.drawable.library_main_icon),
            contentDescription = "App Icon",
            modifier = Modifier.size(60.dp, 66.dp)
        )
    }
}

@Composable
private fun LogoutTextSection(
    title: String,
    description: String
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MovioText(
            modifier = Modifier.height(25.dp),
            text = title,
            textStyle = Theme.textStyle.title.mediumMedium16,
            color = Theme.color.surfaces.onSurface,
            textAlign = TextAlign.Center
        )
        MovioText(
            text = description,
            textStyle = Theme.textStyle.label.smallRegular12,
            color = Theme.color.surfaces.onSurfaceContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier.height(35.dp)
        )
    }
}

@Composable
private fun ErrorMessageSection(errorMessage: String) {
    MovioText(
        text = errorMessage,
        textStyle = Theme.textStyle.label.smallRegular12,
        color = Theme.color.system.error,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
private fun LogoutActionButton(
    isLoading: Boolean,
    onLogoutConfirm: () -> Unit,
    onClearError: () -> Unit,
    textButton: String,
) {
    MovioButton(
        onClick = {
            if (!isLoading) {
                onClearError()
                onLogoutConfirm()
            }
        },
        enabled = !isLoading,
        modifier = Modifier
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                if (isLoading) Theme.color.surfaces.surfaceContainer
                else Theme.color.brand.primary
            ),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Theme.color.brand.primary,
                    strokeWidth = 2.dp
                )
            } else {
                MovioText(
                    text = textButton,
                    textStyle = Theme.textStyle.label.mediumMedium14,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogoutConfirmationBottomSheetPreview() {
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MovioButton(
                onClick = { showBottomSheet = true },
                modifier = Modifier
                    .background(Theme.color.brand.primary)
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                MovioText(
                    text = "Open Logout Bottom Sheet",
                    textStyle = Theme.textStyle.body.mediumMedium12,
                    color = Color.White
                )
            }
        }

        if (showBottomSheet) {
            MovioBottomSheet(
                show = true,
                onDismiss = { showBottomSheet = false },
                containerColor = Theme.color.surfaces.surface
            ) {
                LogoutConfirmationContent(
                    uiState = LogoutUiState(
                        isLoading = false,
                        errorMessage = null,
                        logoutSuccess = false
                    ),
                    onLogoutConfirm = {
                        println("Logout confirmed")
                        showBottomSheet = false
                    },
                    onClearError = { },
                    title = stringResource(R.string.conform_log_out),
                    description = stringResource(R.string.log_out),
                    textButton = stringResource(R.string.logout),
                )
            }
        }
    }
}