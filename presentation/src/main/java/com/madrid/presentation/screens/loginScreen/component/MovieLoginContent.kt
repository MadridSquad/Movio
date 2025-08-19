package com.madrid.presentation.screens.loginScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.screens.loginScreen.LoginInteractionListener
import com.madrid.presentation.viewModel.loginViewModel.LoginUiState

@Composable
fun MovieLoginContent(
    state: LoginUiState,
    interactionListener: LoginInteractionListener
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surfaces.surface)
            .padding(
                horizontal = screenWidth * 0.05f,
                vertical = screenHeight * 0.03f
            )
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Spacer(modifier = Modifier.height(screenHeight * 0.15f))

            LoginHeader(
                modifier = Modifier.padding(horizontal = screenWidth * 0.02f)
            )

            Spacer(modifier = Modifier.height(screenHeight * 0.05f))

            LoginInputFields(
                state = state,
                onUsernameChange = interactionListener::onUsernameChanged,
                onPasswordChange = interactionListener::onPasswordChanged,
                onTogglePassword = interactionListener::onShowPasswordToggled,
                modifier = Modifier.padding(bottom = screenHeight * 0.03f)
            )

            LoginErrorAndForgotPassword(
                state = state,
                onForgotPasswordClick = interactionListener::onForgotPasswordClicked,
                modifier = Modifier.padding(bottom = screenHeight * 0.04f)
            )


            AnimatedLoginButton(
                isLoading = state.isLoading,
                onClick = interactionListener::onLoginClicked,
                text = stringResource(R.string.login),
                modifier = Modifier.padding(bottom = screenHeight * 0.03f)
            )

            OrDivider()

            AnimatedLoginButton(
                isLoading = state.isGuestLoading,
                onClick = interactionListener::onLoginAsGuestClicked,
                text = stringResource(R.string.continue_as_a_guest),
                isTransparent= true,
                showLoadingIndicator = false,
                textColor = Theme.color.surfaces.onSurface,
                modifier = Modifier.padding(top = screenHeight * 0.02f)


            )
        }

        SignUpRow(
            modifier = Modifier.padding(bottom = screenHeight * 0.02f),
            onSignUpClick = interactionListener::onSignUpClicked
        )
    }
}


@Preview
@Composable
private fun MovieLoginContentPreview() {
    MovioTheme {
        MovieLoginContent(
            state = LoginUiState(),
            interactionListener = object : LoginInteractionListener {
                override fun onUsernameChanged(username: String) {}
                override fun onPasswordChanged(password: String) {}
                override fun onLoginClicked(){}
                override fun onLoginAsGuestClicked() {}
                override fun onForgotPasswordClicked() {}
                override fun onSignUpClicked() {}
                override fun onShowPasswordToggled() {}
            }
        )
    }
}