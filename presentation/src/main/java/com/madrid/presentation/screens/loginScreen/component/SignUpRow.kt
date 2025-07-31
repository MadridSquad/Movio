package com.madrid.presentation.screens.loginScreen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
 fun SignUpRow(onSignUpClick: () -> Unit,
                      navController: NavController? = null,
                      onNavigateToLogin:() -> Unit = {},
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        MovioText(
            text = "Don't have an account?",
            textStyle = Theme.textStyle.label.smallRegular14,
            color = Theme.color.surfaces.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(4.dp))
        MovioText(
            text = "Sign up",
            textStyle = Theme.textStyle.label.mediumMedium14,
            color = Theme.color.brand.primary,
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onSignUpClick
            )
        )
    }
}

