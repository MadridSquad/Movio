package com.madrid.designSystem.component.ButtomSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
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
        modifier = modifier
            .size(28.dp),
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
                    .clickable(enabled = isInteractive) {
                        onRatingChange(starNumber)
                    }
                    .padding(8.dp)
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
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedRating by remember { mutableIntStateOf(initialRating) }
    var isSubmitted by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
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
            Spacer(modifier.size(8.dp))
            MovioText(
                text = movieTitle,
                textStyle = MaterialTheme.typography.titleMedium,
                color = Theme.color.surfaces.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier.size(5.dp))
            MovioText(
                modifier = Modifier.size(328.dp,17.dp),
                text = if (isSubmitted) "Thank you for your rating!" else "Add your overall rating for this movie",
                textStyle = MaterialTheme.typography.labelSmall,
                color = Theme.color.surfaces.onSurfaceContainer,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier.size(16.dp))
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
        Spacer(modifier.size(20.dp))
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
                disabledContainerColor = Theme.color.brand.primary,
                disabledContentColor = Color.White.copy(alpha = 0.7f)
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
        onRatingSubmitted = { }
    )
}