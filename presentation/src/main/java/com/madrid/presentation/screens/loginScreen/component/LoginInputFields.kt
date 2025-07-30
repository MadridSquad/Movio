package com.madrid.presentation.screens.loginScreen.component

import LoginUiState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.textInputField.BasicTextInputField
import com.madrid.designSystem.theme.Theme
import com.madrid.domain.exceptions.EmptyPasswordException
import com.madrid.domain.exceptions.EmptyUsernameException
import com.madrid.domain.exceptions.InvalidCredentialsException
import com.madrid.domain.exceptions.UsernameTooShortException
import com.madrid.domain.exceptions.WeakPasswordException


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
            hintText = "Username",
            value = state.username,
            onValueChange = onUsernameChange,
            modifier = Modifier.padding(bottom = 12.dp),
            isError = state.errorState is EmptyUsernameException ||
                    state.errorState is UsernameTooShortException,
            endIconPainter = null,
            errorBorderBrush = Theme.color.gradients.errorBorderGradient
        )

        BasicTextInputField(
            startIconPainter = painterResource(R.drawable.lock),
            hintText = "Password",
            value = state.password,
            onValueChange = onPasswordChange,
            visualTransformation = if (state.showPassword) VisualTransformation.None
            else PasswordVisualTransformation(),
            endIconPainter = painterResource(
                if (state.showPassword) R.drawable.eye else R.drawable.eye_slash
            ),
            onClickEndIcon = onTogglePassword,
            modifier = Modifier.padding(bottom = 12.dp),
            isError = state.errorState is EmptyPasswordException ||
                    state.errorState is WeakPasswordException ||
                    state.errorState is InvalidCredentialsException,
            errorBorderBrush = Theme.color.gradients.errorBorderGradient
        )
    }
}
