package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SeriesRepository
import com.madrid.domain.usecase.authentication.GetCurrentUserDetailsUseCase
import javax.inject.Inject

class GetAllSeriesInHistoryUseCase @Inject constructor(private val seriesRepository: SeriesRepository,private val getCurrentUserDetailsUseCase: GetCurrentUserDetailsUseCase) {
    suspend operator fun invoke(): List<Series> {
        val userId = getCurrentUserDetailsUseCase()?.id
        if (!userId.isNullOrEmpty()){
            seriesRepository.getSeriesGenres()
            return seriesRepository.getAllSeriesInHistory(userId = userId.toInt())
        }
        return emptyList()
    }
}