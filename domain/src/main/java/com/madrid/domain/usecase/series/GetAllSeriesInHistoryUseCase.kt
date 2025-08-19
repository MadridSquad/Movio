package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SeriesRepository
import javax.inject.Inject

class GetAllSeriesInHistoryUseCase @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(): List<Series> {
        seriesRepository.getSeriesGenres()
        return seriesRepository.getAllSeriesInHistory()
    }
}