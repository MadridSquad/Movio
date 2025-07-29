package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Review
import com.madrid.domain.repository.SeriesRepository

class GetSeriesReviewsUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesId: Int): List<Review> =
        seriesRepository.getSeriesReviewsById(seriesId)
}