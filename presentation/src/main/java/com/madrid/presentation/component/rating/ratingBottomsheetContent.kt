package com.madrid.presentation.component.rating

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R.drawable
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.movioCards.MovioArtistsCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController

enum class RatingSheetState {
    RATING, SUCCESS
}

@Composable
fun RatingBottomSheetContent(
    isGuest: Boolean,
    movieName: String,
    topImageUrl: String,
    userRating: Int,
    onPickRatingNumber: (Int) -> Unit,
    onSubmitRating: () -> Unit,
    onDismiss: () -> Unit
) {
    val navController = LocalNavController.current
    var sheetState by remember { mutableStateOf(RatingSheetState.RATING) }

    AnimatedContent(
        targetState = sheetState,
        transitionSpec = {
            (slideInVertically(
                animationSpec = tween(300),
                initialOffsetY = { it }
            ) + fadeIn(animationSpec = tween(300))) togetherWith
                    (slideOutVertically(
                        animationSpec = tween(300),
                        targetOffsetY = { -it }
                    ) + fadeOut(animationSpec = tween(300)))
        },
        label = "RatingSheetTransition"
    ) { state ->
        when (state) {
            RatingSheetState.RATING -> {
                if (isGuest) {
                    GuestRatingContent(
                        onLoginClick = {
                            onDismiss()
                            navController.navigate(Destinations.AuthenticationScreen)
                        }
                    )
                } else {
                    AuthenticatedRatingContent(
                        movieName = movieName,
                        topImageUrl = topImageUrl,
                        userRating = userRating,
                        onPickRatingNumber = onPickRatingNumber,
                        onSubmitRating = {
                            onSubmitRating()
                            sheetState = RatingSheetState.SUCCESS
                        }
                    )
                }
            }
            RatingSheetState.SUCCESS -> {
                RatingSuccessBottomSheetContent(
                    userRating = userRating,
                    onDoneClick = onDismiss
                )
            }
        }
    }
}

@Composable
private fun GuestRatingContent(
    onLoginClick: () -> Unit
) {
    Column {
        Image(
            painter = painterResource(id = drawable.library_main_icon),
            contentDescription = "Search Icon",
            modifier = Modifier
                .size(width = 60.dp, height = 66.dp)
                .align(CenterHorizontally)
                .padding(bottom = 16.dp),
        )
        MovioText(
            text = stringResource(R.string.you_dont_have_an_account),
            textStyle = Theme.textStyle.title.mediumMedium16,
            color = Theme.color.surfaces.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        MovioText(
            text = stringResource(R.string.this_rating_is_only_available_to_registered_users_Login_to_share_your_rating),
            textStyle = Theme.textStyle.label.smallRegular12,
            color = Theme.color.surfaces.onSurfaceContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
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
            onClick = onLoginClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Theme.color.brand.primary,
            ),
            shape = RoundedCornerShape(24.dp),
            elevation = ButtonDefaults.elevation(0.dp)
        ) {
            MovioText(
                text = stringResource(R.string.login),
                textStyle = Theme.textStyle.label.mediumMedium14,
                color = Theme.color.brand.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AuthenticatedRatingContent(
    movieName: String,
    topImageUrl: String,
    userRating: Int,
    onPickRatingNumber: (Int) -> Unit,
    onSubmitRating: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = CenterHorizontally
    ) {
        MovioArtistsCard(
            imageUrl = topImageUrl,
            circleImageSize = 88.dp,
            artistsName = movieName,
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
                    painter = painterResource(drawable.bold_star),
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
            onClick = onSubmitRating,
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
