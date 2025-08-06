package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SeriesRepository

class GetAllSeriesInHistoryUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(): List<Series> {
        return seriesRepository.getAllSeriesInHistory()
    }
}