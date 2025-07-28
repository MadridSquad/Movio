package com.madrid.domain.usecase.homeUseCase

import com.madrid.domain.repository.SeriesRepository

class GetOnAirSeriesUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke() = seriesRepository.getOnAirSeries()
}