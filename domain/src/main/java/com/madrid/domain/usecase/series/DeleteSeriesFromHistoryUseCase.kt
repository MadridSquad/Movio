package com.madrid.domain.usecase.series

import com.madrid.domain.repository.SeriesRepository
import javax.inject.Inject

class DeleteSeriesFromHistoryUseCase @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: Int) =
        seriesRepository.deleteSeriesFromHistory(seriesId = seriesId)
}