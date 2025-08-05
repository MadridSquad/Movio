package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesTrailersUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesId: Int): List<Trailer> =
        seriesRepository.getSeriesTrailersById(seriesId)
}