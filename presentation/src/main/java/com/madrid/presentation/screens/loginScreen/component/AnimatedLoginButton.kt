package com.madrid.presentation.screens.loginScreen.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioButton
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun AnimatedLoginButton(
    isLoading: Boolean,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
    showLoadingIndicator: Boolean = true,
    textStyle: TextStyle = Theme.textStyle.label.mediumMedium14,
    textColor: Color = Theme.color.brand.onPrimary,
    buttonColor: Color = Theme.color.brand.primary,
    modifier: Modifier = Modifier.fillMaxWidth(),
    isTransparent: Boolean = false
) {
    val actualButtonColor = if (isTransparent) Color.Transparent else buttonColor

    MovioButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .then(
                if (isTransparent) {
                    Modifier.border(
                        width = 1.dp,
                        color = Theme.color.surfaces.onSurfaceAt3,
                        shape = RoundedCornerShape(24.dp)
                    )
                } else {
                    Modifier
                }
            ),
        enabled = enabled && !isLoading,
        color = actualButtonColor,

        ) {
        if (isLoading&&showLoadingIndicator) {
            LoadingIndicator()

            Spacer(modifier= Modifier.width(4.dp))
            MovioText(

                text = text,
                textStyle = textStyle,
                color = textColor,
                textAlign = TextAlign.Center,

                )
        } else {
            MovioText(

                text = text,
                textStyle = textStyle,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}