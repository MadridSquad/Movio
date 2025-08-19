package com.madrid.presentation.screens.loginScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madrid.designSystem.component.MovioSnakBar
import com.madrid.designSystem.component.ToastDuration
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.loginScreen.component.LoginEffect
import com.madrid.presentation.screens.loginScreen.component.MovieLoginContent
import com.madrid.presentation.viewModel.loginViewModel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onGuestLogin: () -> Unit = {}
) {
    val navController = LocalNavController.current
    val viewModel: LoginViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    var toastMessage by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
      viewModel.effect.collect {effect ->
          when(effect){
          is LoginEffect.OnLoginSuccess ->{
              if (state.isGuest){
                  onGuestLogin()
              } else {
                  onLoginSuccess()
              }
              navController.navigate(Destinations.HomeScreen) {
                  popUpTo(Destinations.LoginScreen) {
                      inclusive = true
                  }
              }
          }
              is LoginEffect.WebView -> {
                  navController.navigate(Destinations.WebViewScreen(url = effect.url))
              }
            is LoginEffect.ShowToast -> {
                toastMessage = effect.message
            }
              is LoginEffect.DismissToast ->{
                  toastMessage =null

              }
      }
      }
  }

    Box(modifier = Modifier.fillMaxSize()) {
        MovieLoginContent(
            state = state,
            interactionListener = viewModel
        )
        toastMessage?.let { message ->
            MovioSnakBar(
                message = stringResource(message),
                duration = ToastDuration.SHORT,
                onDismiss = { toastMessage = null },
                modifier = Modifier.padding(16.dp)
                    .align(Alignment.BottomCenter)
            )
        }}
}

