package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Artist
import com.madrid.domain.repository.SeriesRepository

class GetSeriesTopCastUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesId: Int): List<Artist> =
        seriesRepository.getSeriesCreditsById(seriesId)
}