package com.madrid.presentation.screens.loginScreen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.textInputField.BasicTextInputField
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.viewModel.loginViewModel.LoginUiState

@Composable
fun LoginInputFields(
    modifier: Modifier = Modifier,
    state: LoginUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePassword: () -> Unit,
) {
    Column(modifier = modifier) {
        BasicTextInputField(
            startIconPainter = painterResource(R.drawable.profile_circle),
            hintText = stringResource(com.madrid.presentation.R.string.username),
            value = state.username,
            onValueChange = onUsernameChange,
            modifier = Modifier.padding(bottom = 12.dp),
            isError = state.errorMessage != null &&
                    (state.errorMessage.contains("Username") ||
                            state.errorMessage.contains("Invalid")),

            errorBorderBrush = Theme.color.gradients.errorBorderGradient,
            endIconPainter = null
        )

        BasicTextInputField(
            startIconPainter = painterResource(R.drawable.lock),
            hintText = stringResource(com.madrid.presentation.R.string.password),
            value = state.password,
            onValueChange = onPasswordChange,
            visualTransformation = if (state.showPassword) VisualTransformation.None
            else PasswordVisualTransformation(),
            endIconPainter = painterResource(
                if (state.showPassword) R.drawable.eye else R.drawable.eye_slash
            ),
            onClickEndIcon = onTogglePassword,
            modifier = Modifier.padding(bottom = 12.dp),
            isError = state.errorMessage != null &&
                    (state.errorMessage.contains("Password") ||
                            state.errorMessage.contains("Invalid")),
            errorBorderBrush = Theme.color.gradients.errorBorderGradient
        )
    }

}