package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SeriesRepository

class GetSeriesByGenresUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(): Map<String, List<Series>> {
        return seriesRepository.getSeriesByGenres()
    }
}