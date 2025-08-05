package com.madrid.domain.usecase.series

import com.madrid.domain.repository.SeriesRepository
import javax.inject.Inject

class GetRecommendedSeriesUseCase @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(page: Int) = seriesRepository.getRecommendedSeries(page)
}