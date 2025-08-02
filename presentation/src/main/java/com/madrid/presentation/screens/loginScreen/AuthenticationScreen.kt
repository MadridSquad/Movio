package com.madrid.presentation.screens.loginScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
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
}

