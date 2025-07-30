package com.madrid.domain.usecase.series

import com.madrid.domain.repository.SeriesRepository

class GetAiringTodaySeriesUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(page: Int) = seriesRepository.getAiringTodaySeries(page)
}