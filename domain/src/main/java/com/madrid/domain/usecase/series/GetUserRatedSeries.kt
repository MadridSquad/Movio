package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SeriesRepository
import com.madrid.domain.repository.UserRepository
import kotlinx.coroutines.flow.first

class GetUserRatedSeries(
    private val seriesRepository: SeriesRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<RatedSeries> {
        val sessionId: String = userRepository.getSessionId().first()
        return seriesRepository.getUserSeriesRate(sessionId)
    }

    data class RatedSeries(
        val rate: Double,
        val series: Series
    )
}