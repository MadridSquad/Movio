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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.viewModel.loginViewModel.LoginUiState


@Composable
fun LoginErrorAndForgotPassword(
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
        if (state.errorMessage != null) {
            MovioIcon(
                painterResource(R.drawable.info_circle),
                tint = Theme.color.system.onError,
                contentDescription = null,
                modifier = Modifier.padding(end = 4.dp)
            )

            MovioText(
                text = state.errorMessage,
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
            text = stringResource(com.madrid.presentation.R.string.forgot_password),
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