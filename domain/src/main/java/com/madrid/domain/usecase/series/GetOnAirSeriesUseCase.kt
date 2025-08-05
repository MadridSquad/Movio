package com.madrid.domain.usecase.series

import com.madrid.domain.repository.SeriesRepository
import javax.inject.Inject

class GetOnAirSeriesUseCase @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(page: Int) = seriesRepository.getOnAirSeries(page)
}