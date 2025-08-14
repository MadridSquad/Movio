package com.madrid.presentation.screens.loginScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.loginViewModel.LoginUiState

@Composable
fun MovieLoginContent(
    state: LoginUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onTogglePassword: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onGuestLogin: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface)
            .padding(horizontal = 16.dp, vertical = 32.dp),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Spacer(modifier = Modifier.height(74.dp))
            LoginHeader()
            Spacer(modifier = Modifier.height(48.dp))

            LoginInputFields(
                state = state,
                onUsernameChange = onUsernameChange,
                onPasswordChange = onPasswordChange,
                onTogglePassword = onTogglePassword
            )

            LoginErrorAndForgotPassword(
                state = state,
                onForgotPasswordClick = onForgotPasswordClick,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            AnimatedLoginButton(
                isLoading = state.isLoading,
                onClick = {
                    keyboardController?.hide()
                    onLoginClick()
                },
                text = stringResource(R.string.login),
                modifier = Modifier.padding(bottom = 40.dp)
            )

            OrDivider()

            AnimatedLoginButton(
                isLoading = state.isGuestLoading,
                onClick = onGuestLogin,
                buttonColor = Theme.color.surfaces.surface,
                textColor = Theme.color.surfaces.onSurface,
                text = stringResource(R.string.continue_as_a_guest),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .border(
                        width = 1.dp,
                        color = Theme.color.surfaces.onSurfaceAt3,
                        shape = RoundedCornerShape(24.dp)
                    )
            )

        }
        SignUpRow(onSignUpClick = onSignUpClick)


    }
}



@Preview
@Composable
private fun MovieLoginContentPreview() {
    MovioTheme {
        MovieLoginContent(
            state = LoginUiState(),
            onUsernameChange = { },
            onPasswordChange = { },
            onLoginClick = { },
            onTogglePassword = { },
            onForgotPasswordClick = { },
            onSignUpClick = { },
            onGuestLogin = { }
        )
    }
}