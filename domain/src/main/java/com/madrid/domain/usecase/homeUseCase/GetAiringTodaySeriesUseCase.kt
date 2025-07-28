package com.madrid.domain.usecase.homeUseCase

import com.madrid.domain.repository.SeriesRepository

class GetAiringTodaySeriesUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke() = seriesRepository.getAiringTodaySeries()
}