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
    interactionListener: LoginInteractionListener,

) {
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
                onUsernameChange = interactionListener::onUsernameChanged,
                onPasswordChange = interactionListener::onPasswordChanged,
                onTogglePassword = interactionListener::onShowPasswordToggled,
            )

            LoginErrorAndForgotPassword(
                state = state,
                onForgotPasswordClick = interactionListener::onForgotPasswordClicked,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            AnimatedLoginButton(
                isLoading = state.isLoading,
                onClick = interactionListener::onLoginClicked,
                text = stringResource(R.string.login),
                modifier = Modifier.padding(bottom = 40.dp),

            )

            OrDivider()

            AnimatedLoginButton(
                isLoading = state.isGuestLoading,
                showLoadingIndicator = false,
                onClick = interactionListener::onLoginAsGuestClicked,
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
        SignUpRow(onSignUpClick = interactionListener::onSignUpClicked)


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
                override fun onLoginClicked() {}
                override fun onLoginAsGuestClicked() {}
                override fun onForgotPasswordClicked() {}
                override fun onSignUpClicked() {}
                override fun onShowPasswordToggled() {}
            }
        )
    }
}