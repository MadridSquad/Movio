package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Series
import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetFavoriteSeriesUseCase@Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val seriesRepository: SeriesRepository,
    private val getSeriesGenresUseCase: GetSeriesGenresUseCase,
) {
    suspend operator fun invoke(): List<Series> {
        getSeriesGenresUseCase()
        val sessionId = authenticationRepository.getSessionId().first()
        return seriesRepository.getFavoriteSeries(sessionId)
    }
}