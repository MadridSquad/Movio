package com.madrid.designSystem.component.ButtomSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.theme.Theme

@Composable
fun RatingStars(
    currentRating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxRating: Int = 5
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center, // Centered as per image
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(maxRating) { index ->
            val starNumber = index + 1
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star $starNumber",
                tint = if (starNumber <= currentRating) Theme.color.brand.primary // Selected star color
                else Theme.color.surfaces.onSurfaceVariant, // Unselected star color from image
                modifier = Modifier
                    .size(28.dp) // 28x28px as per image
                    .clickable { onRatingChange(starNumber) }
                    .padding(horizontal = 6.dp) // Half of the 12dp gap between stars
            )
        }
    }
}

@Composable
fun RatingBottomSheetContent(
    movieTitle: String,
    moviePosterResId: Int,
    initialRating: Int = 0,
    onRatingSubmitted: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedRating by remember { mutableIntStateOf(initialRating) }
    var isSubmitted by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp) // Gap between major sections
    ) {
        // Movie Info Section
        Column(
            modifier = Modifier
                .fillMaxWidth() // Fill (328px)
                .height(115.dp), // Hug (115px)
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp) // Gap 8px between image, title, description
        ) {
            // Movie Poster
            Image(
                painter = painterResource(id = moviePosterResId),
                contentDescription = movieTitle,
                modifier = Modifier
                    .size(88.dp) // 88x88px
                    .clip(CircleShape) // 137.5px radius makes it circular
                    .border(
                        width = 1.1.dp,
                        color = Theme.color.surfaces.onSurfaceAt2, // White 38% opacity, which should be onSurfaceAt2 based on your color system
                        shape = CircleShape
                    )
            )
            // Movie Title
            Text(
                text = movieTitle,
                style = MaterialTheme.typography.titleMedium, // Assuming Title Medium - Medium 16
                color = Theme.color.surfaces.onSurface, // Color from image_3116e3.jpg
                textAlign = TextAlign.Center
            )
            // Description
            Text(
                text = if (isSubmitted) "Thank you for your rating!" else "Add your overall rating for this movie",
                style = MaterialTheme.typography.labelSmall, // Assuming Label Small - Regular 14
                color = Theme.color.surfaces.onSurfaceContainer, // Color from image_31171f.png
                textAlign = TextAlign.Center
            )
        }

        // Rating Stars
        if (!isSubmitted) { // Show stars only if not yet submitted
            RatingStars(
                currentRating = selectedRating,
                onRatingChange = { newRating ->
                    selectedRating = newRating
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp) // Hug (28px) for stars row
            )
        } else {
            // Show filled stars after submission if needed, or some other confirmation visual.
            // For now, let's keep it simple as per the "Thank you" state.
            RatingStars(
                currentRating = selectedRating,
                onRatingChange = { /* No action on click after submission */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp),
                maxRating = 5 // Always show 5 stars for the "Thank you" state if you want to visually represent the rating
            )
        }


        // Submit Button
        Button(
            onClick = {
                if (!isSubmitted) {
                    onRatingSubmitted(selectedRating)
                    isSubmitted = true // Change state to submitted
                } else {
                    // If already submitted, the button might change to "Done" and dismiss the sheet
                    // You might want to add another callback for "Done"
                    onRatingSubmitted(selectedRating) // Call submit again if "Done" also performs an action or just dismisses
                }
            },
            enabled = selectedRating > 0 || isSubmitted, // Enable only if a rating is selected or already submitted (for "Done")
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp), // Fixed (48px)
            shape = RoundedCornerShape(24.dp), // 2xl radius, assuming 24.dp
            colors = ButtonDefaults.buttonColors(
                containerColor = Theme.color.brand.primary, // Primary brand color for button background
                contentColor = Theme.color.brand.onPrimary // Text color on primary background
            )
        ) {
            Text(text = if (isSubmitted) "Done" else "Submit")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RatingBottomSheetPreview() {
    RatingBottomSheetContent(
        movieTitle = "Movie Title",
        moviePosterResId = R.drawable.library_main_icon,
        initialRating = 3,
        onRatingSubmitted = { /* Handle rating submission */ }
        )
}
