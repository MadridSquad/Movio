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
                .fillMaxWidth(), // Fill (328px) in the image, achieved with fillMaxWidth and parent padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp) // Gap 8px between title and description
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    // You might need to define your custom typography in your Theme file
                    // or adjust FontFamilies/Weights directly if not yet in your theme.
                    // Assuming Title Medium - Medium (Weight 500) from image_22fb7b.jpg
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                        ?: MaterialTheme.typography.titleMedium.fontWeight // If you have a custom font, it should be defined in your Theme's Typography
                ),
                color = Theme.color.surfaces.onSurface, // Color from image_22fb7b.jpg
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = description,
                style = MaterialTheme.typography.labelSmall.copy(
                    // Assuming Label Small - Regular 12 (Weight 400) from image_22fb80.jpg
                    fontWeight = MaterialTheme.typography.labelSmall.fontWeight
                        ?: MaterialTheme.typography.labelSmall.fontWeight // If you have a custom font, it should be defined in your Theme's Typography
                ),
                color = Theme.color.surfaces.onSurfaceContainer, // Color from image_22fb80.jpg
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Login Button
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth() // Fill (328px)
                .height(48.dp), // Fixed (48px)
            shape = RoundedCornerShape(24.dp), // 2xl radius, assuming 24.dp
            colors = ButtonDefaults.buttonColors(
                containerColor = Theme.color.brand.primary, // Primary brand color for button background
                contentColor = Theme.color.brand.onPrimary // Text color on primary background
            ),
            contentPadding = ButtonDefaults.ContentPadding // Default Material Design padding (adjust if "md" in image is different)
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