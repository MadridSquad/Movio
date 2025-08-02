package com.madrid.presentation.screens.detailsScreen.reviewsScreen.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.detailsViewModel.ReviewUiState
import com.madrid.presentation.viewModel.detailsViewModel.ReviewsScreenUiState

@Composable
fun ReviewScreen(
    uiState: ReviewsScreenUiState,
    modifier: Modifier = Modifier,
    onSeeAllReviews: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        CustomTextTitle(
            primaryText = stringResource(R.string.reviews),
            secondaryText = stringResource(R.string.see_all),
            endIcon = painterResource(com.madrid.designSystem.R.drawable.outline_alt_arrow_left),
            onSeeAllClick = { onSeeAllReviews() },
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
        )
        ReviewsSectionContent(reviews = uiState.reviews)
    }
}

@Composable
private fun ReviewsSectionContent(reviews: List<ReviewUiState>) {
    if (reviews.isEmpty()) {
        EmptyReviewsMessage()
    } else {
        ReviewsList(reviews = reviews)
    }
}

@Composable
private fun ReviewsList(reviews: List<ReviewUiState>) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp)
            .height(137.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        reviews.forEach { review ->
            ReviewCard(
                reviewerName = review.reviewerName,
                reviewerImageUrl = review.reviewerImageUrl,
                rating = review.rating,
                date = review.date,
                content = review.content
            )
        }
    }
}

@Composable
private fun EmptyReviewsMessage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        MovioText(
            text = stringResource(R.string.no_reviews_available),
            color = Theme.color.surfaces.onSurfaceVariant,
            textStyle = Theme.textStyle.body.mediumMedium14
        )
    }
}