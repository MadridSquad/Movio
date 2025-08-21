package com.madrid.presentation.screens.detailsScreen.seriesDetails.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.movioCards.MovioArtistsCard

@Composable
fun AddRatingBottomSheet(
    imageUrl: String,
    seriesName: String,
    userRating: Int,
    onPickRatingNumber: (rating: Int) -> Unit,
    onDismissAddRatingBottomSheet: () -> Unit,
    onRateButtonClick: () -> Unit,
    onShowDoneRatingBottomSheetClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MovioArtistsCard(
            imageUrl = imageUrl,
            circleImageSize = 88.dp,
            artistsName = seriesName,
            paddingBetweenImageAndText = 8.dp,
        )
        MovioText(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(R.string.add_your_overall_rating_for_this_movie),
            color = Theme.color.surfaces.onSurfaceContainer,
            textStyle = Theme.textStyle.label.smallRegular14
        )
        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            (1..5).forEach { i ->
                MovioIcon(
                    painter = painterResource(com.madrid.designSystem.R.drawable.bold_star),
                    contentDescription = null,
                    tint = if (i <= userRating) Theme.color.system.warning else Theme.color.surfaces.onSurfaceVariant,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onPickRatingNumber(i) }
                )
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 40.dp,
                    bottom = 32.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .height(48.dp),
            onClick = {
                onRateButtonClick()
                onDismissAddRatingBottomSheet()
                onShowDoneRatingBottomSheetClick()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Theme.color.brand.primary,
            ),
            shape = RoundedCornerShape(24.dp),
            elevation = ButtonDefaults.elevation(0.dp)
        ) {
            MovioText(
                text = stringResource(R.string.submit),
                color = Theme.color.brand.onPrimary,
                textStyle = Theme.textStyle.label.mediumMedium14
            )
        }
    }
}