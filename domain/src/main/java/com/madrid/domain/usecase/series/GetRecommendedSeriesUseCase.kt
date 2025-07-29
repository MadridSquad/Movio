package com.madrid.domain.usecase.series

import com.madrid.domain.repository.SeriesRepository

class GetRecommendedSeriesUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(page: Int) = seriesRepository.getRecommendedSeries(page)
}