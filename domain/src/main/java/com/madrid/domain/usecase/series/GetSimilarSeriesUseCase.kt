package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SeriesRepository

class GetSimilarSeriesUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesId: Int): List<Series> =
        seriesRepository.getSimilarSeriesById(seriesId)
}