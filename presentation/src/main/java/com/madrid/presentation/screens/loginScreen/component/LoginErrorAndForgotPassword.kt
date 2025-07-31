package com.madrid.presentation.screens.loginScreen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.viewModel.LoginError
import com.madrid.presentation.viewModel.LoginUiState

@Composable
fun  LoginErrorAndForgotPassword(
    modifier: Modifier = Modifier,
    state: LoginUiState,
    onForgotPasswordClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        val errorMessage = buildString {
            when (val error = state.errorState) {
                is LoginError.EmptyFields -> {
                    if (error.usernameEmpty) append("Username is required")
                    if (error.usernameEmpty && error.passwordEmpty) append(", ")
                    if (error.passwordEmpty) append("Password is required")
                }
                is LoginError.InvalidCredentials -> append("Invalid username or password")
                is LoginError.AccountLocked -> append("Account locked. Contact support.")
                is LoginError.NetworkError -> append("Network error. Try again.")
                is LoginError.GenericError -> append(error.message)
                else -> {}
            }
        }

        if (errorMessage.isNotEmpty()) {
            MovioIcon(
                painterResource(R.drawable.info_circle),
                tint = Theme.color.system.onError,
                contentDescription = null,
                modifier = Modifier.padding(end = 4.dp)
            )

            MovioText(
                text = errorMessage,
                textStyle = Theme.textStyle.label.mediumMedium12,
                color = Theme.color.system.onError,
                maxLines = 2,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }

        MovioText(
            text = "Forgot Password?",
            textStyle = Theme.textStyle.label.mediumMedium12,
            color = Theme.color.surfaces.onSurfaceVariant,
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onForgotPasswordClick
            )
        )
    }

}
