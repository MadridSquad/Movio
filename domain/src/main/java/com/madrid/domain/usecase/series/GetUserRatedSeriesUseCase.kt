package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Series
import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetUserRatedSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): List<RatedSeries> {
        val sessionId: String = authenticationRepository.getSessionId().first()
        return seriesRepository.getUserSeriesRate(sessionId)
    }

    data class RatedSeries(
        val rate: Double,
        val series: Series
    )
}