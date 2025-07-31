package com.madrid.presentation.screens.loginScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.loginScreen.component.MovieLoginContent
import com.madrid.presentation.viewModel.LoginError
import com.madrid.presentation.viewModel.LoginUiState
import com.madrid.presentation.viewModel.loginViewModel.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun AuthenticationScreen(
    onLoginSuccess: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onGuestLogin: () -> Unit = {}
) {
    val navController = LocalNavController.current
    val viewModel: LoginViewModel = getViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = state.loginSuccess) {
        if (state.loginSuccess) {
            if (state.isGuest) {
                onGuestLogin()
            } else {
                onLoginSuccess()
            }
            navController.navigate(
                Destinations.HomeScreen
            ){
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


@Preview(showBackground = true)
@Composable
fun PreviewFullMovieLoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var loginSuccess by remember { mutableStateOf(false) }
    var isGuest by remember { mutableStateOf(false) }
    var errorState by remember { mutableStateOf<LoginError?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val state = LoginUiState(
        username = username,
        password = password,
        showPassword = showPassword,
        errorState = errorState,
        loginSuccess = loginSuccess,
        isGuest = isGuest,
        isLoading = isLoading,
        // canLogin = username.isNotBlank() && password.isNotBlank()
    )

    val scope = rememberCoroutineScope()

    fun onLoginClick() {
        scope.launch {
            isLoading = true
            delay(2000)
            if (username == "user" && password == "pass") {
                loginSuccess = true
                isGuest = false
                errorState = null
            } else {
                errorState = LoginError.InvalidCredentials
            }
            isLoading = false
        }
    }

    fun onTogglePassword() {
        showPassword = !showPassword
    }

    fun onUsernameChange(newValue: String) {
        username = newValue
        errorState = null
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
        errorState = null
    }

    MovieLoginContent(
        state = state,
        onUsernameChange = ::onUsernameChange,
        onPasswordChange = ::onPasswordChange,
        onLoginClick = ::onLoginClick,
        onTogglePassword = ::onTogglePassword,
        onForgotPasswordClick = { },
        onSignUpClick = { },
        onGuestLogin = {
            loginSuccess = true
            isGuest = true
        }
    )
}
