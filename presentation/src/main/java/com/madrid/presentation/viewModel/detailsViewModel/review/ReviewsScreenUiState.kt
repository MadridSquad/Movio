package com.madrid.presentation.viewModel.detailsViewModel.review

import com.madrid.domain.entity.Review
import com.madrid.presentation.viewModel.shared.parser.formatDate

data class ReviewsScreenUiState(
    val reviews: List<ReviewUiState> = emptyList(),
)

data class ReviewUiState(
    val reviewerName: String,
    val reviewerImageUrl: String?,
    val rating: Float,
    val date: String,
    val content: String
)

fun Review.toReviewUiState(): ReviewUiState {
    return ReviewUiState(
        reviewerName = this.reviewerName,
        reviewerImageUrl = this.reviewerPhotoUrl,
        rating = this.rate.toFloat(),
        date = formatDate(this.date),
        content = this.comment
    )
}