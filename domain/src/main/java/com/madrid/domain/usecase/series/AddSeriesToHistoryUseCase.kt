package com.madrid.domain.usecase.series

import com.madrid.domain.repository.SeriesRepository

class AddSeriesToHistoryUseCase(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Int) =
        seriesRepository.addSeriesToHistory(seriesId = seriesId)
}