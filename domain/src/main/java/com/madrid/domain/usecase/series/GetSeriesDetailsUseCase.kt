package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesDetailsUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesId: Int): Series {
        return seriesRepository.getSeriesDetailsById(seriesId)
            .also { series ->
                series.genre.forEach {
                    seriesRepository.increaseSeriesGenreInterestPoints(it.name)
                }
            }
    }
}