package com.madrid.presentation.screens.loginScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioToast
import com.madrid.designSystem.component.ToastDuration

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.madrid.designSystem.R
import com.madrid.designSystem.component.CustomSnackBarHost
import com.madrid.designSystem.component.showSnackBar

import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.loginScreen.component.MovieLoginContent
import com.madrid.presentation.viewModel.loginViewModel.LoginViewModel

@Composable
fun AuthenticationScreen(
    onLoginSuccess: () -> Unit = {},
    onGuestLogin: () -> Unit = {}
) {
    val navController = LocalNavController.current
    val viewModel: LoginViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()

    LaunchedEffect(key1 = state.loginSuccess) {
        if (state.loginSuccess) {
            if (state.isGuest) {
                onGuestLogin()
            } else {
                onLoginSuccess()
            }
            navController.navigate(Destinations.HomeScreen) {
                popUpTo(Destinations.AuthenticationScreen) {
                    inclusive = true
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        MovieLoginContent(
            state = state,
            onUsernameChange = viewModel::updateUsername,
            onPasswordChange = viewModel::updatePassword,
            onLoginClick = { viewModel.login(onLoginSuccess) },
            onTogglePassword = viewModel::toggleShowPassword,
            onForgotPasswordClick = {
                navController.navigate(
                    Destinations.WebViewScreen("https://www.themoviedb.org/reset-password")
                )
            },
            onSignUpClick = {
                navController.navigate(
                    Destinations.WebViewScreen("https://www.themoviedb.org/signup")
                )
            },
            onGuestLogin = { viewModel.loginAsGuest(onGuestLogin) },
        )

        if (toastMessage != null) {
            MovioToast(
                message = toastMessage!!,
                duration = ToastDuration.SHORT,
                onDismiss = { viewModel.dismissToast() },

                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
            )
        }
    }
}


