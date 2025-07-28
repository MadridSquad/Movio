package com.madrid.designSystem.component.ButtomSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.theme.Theme

@Composable
fun AuthRequiredBottomSheetContent(
    title: String,
    description: String,
    buttonText: String,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.library_main_icon),
            contentDescription = "Movio Logo",
            modifier = Modifier.size(width = 60.dp, height = 66.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                        ?: MaterialTheme.typography.titleMedium.fontWeight
                ),
                color = Theme.color.surfaces.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = description,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = MaterialTheme.typography.labelSmall.fontWeight
                        ?: MaterialTheme.typography.labelSmall.fontWeight
                ),
                color = Theme.color.surfaces.onSurfaceContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Login Button
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Theme.color.brand.primary,
                contentColor = Theme.color.brand.onPrimary
            ),
            contentPadding = ButtonDefaults.ContentPadding
        ) {
            Text(text = buttonText)
        }
    }
}

@Preview(showBackground = true , showSystemUi = true)
@Composable
fun kjl(){
    AuthRequiredBottomSheetContent(
        title = "Title",
        description = "Description",
        buttonText = "Login",
        onLoginClick = {}
    )
}