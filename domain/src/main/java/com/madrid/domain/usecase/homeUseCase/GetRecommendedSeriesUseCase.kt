package com.madrid.domain.usecase.homeUseCase

import com.madrid.domain.repository.SeriesRepository

class GetRecommendedSeriesUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke() = seriesRepository.getRecommendedSeries()
}