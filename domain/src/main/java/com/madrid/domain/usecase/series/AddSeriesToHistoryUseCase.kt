package com.madrid.domain.usecase.series

import com.madrid.domain.repository.SeriesRepository
import com.madrid.domain.usecase.authentication.GetCurrentUserDetailsUseCase
import javax.inject.Inject

class AddSeriesToHistoryUseCase @Inject constructor(private val seriesRepository: SeriesRepository,private val getCurrentUserDetailsUseCase: GetCurrentUserDetailsUseCase) {
    suspend operator fun invoke(seriesId: Int) {
        val userId = getCurrentUserDetailsUseCase()?.id
        if (!userId.isNullOrEmpty())
            seriesRepository.addSeriesToHistory(seriesId = seriesId, userId = userId.toInt())
    }
}