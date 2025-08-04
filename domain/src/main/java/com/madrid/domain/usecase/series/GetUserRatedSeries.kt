package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SeriesRepository

class GetUserRatedSeries(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(): List<RatedSeries> =
        seriesRepository.getUserSeriesRate()

    data class RatedSeries(
        val rate: Double,
        val series: Series
    )
}