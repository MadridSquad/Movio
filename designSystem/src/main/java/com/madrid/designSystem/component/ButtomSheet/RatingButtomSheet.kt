package com.madrid.designSystem.component.ButtomSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun RatingStars(
    currentRating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxRating: Int = 5,
    isInteractive: Boolean = true
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(maxRating) { index ->
            val starNumber = index + 1
            val isSelected = starNumber <= currentRating

            MovioIcon(
                painter = painterResource(
                    id = if (isSelected) R.drawable.bold_star else R.drawable.outline_star
                ),
                contentDescription = "Star $starNumber",
                tint = if (isSelected) Theme.color.brand.primary else Theme.color.surfaces.onSurfaceContainer,
                modifier = Modifier
                    .size(32.dp)
                    .clickable(enabled = isInteractive) {
                        onRatingChange(starNumber)
                    }
                    .padding(4.dp)
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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(115.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = moviePosterResId),
                contentDescription = movieTitle,
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.1.dp,
                        color = Theme.color.surfaces.onSurfaceAt2,
                        shape = CircleShape
                    )
            )
            MovioText(
                text = movieTitle,
                textStyle = MaterialTheme.typography.titleMedium,
                color = Theme.color.surfaces.onSurface,
                textAlign = TextAlign.Center
            )
            MovioText(
                text = if (isSubmitted) "Thank you for your rating!" else "Add your overall rating for this movie",
                textStyle = MaterialTheme.typography.labelSmall,
                color = Theme.color.surfaces.onSurfaceContainer,
                textAlign = TextAlign.Center
            )
        }
        RatingStars(
            currentRating = selectedRating,
            onRatingChange = { newRating ->
                if (!isSubmitted) {
                    selectedRating = newRating
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            isInteractive = !isSubmitted
        )
        Button(
            onClick = {
                if (!isSubmitted) {
                    onRatingSubmitted(selectedRating)
                    isSubmitted = true
                } else {
                    onRatingSubmitted(selectedRating)
                }
            },
            enabled = selectedRating > 0 || isSubmitted,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Theme.color.brand.primary,
                contentColor = Theme.color.brand.onPrimary
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
        movieTitle = "Ballerina",
        moviePosterResId = R.drawable.library_main_icon,
        initialRating = 0,
        onRatingSubmitted = { /* Handle rating submission */ }
    )
}