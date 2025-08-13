package com.madrid.domain.usecase.series

import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SetSeriesFavoriteStatusUseCase @Inject constructor(
    val seriesRepository: SeriesRepository,
    val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(seriesId: Int, isFavorite: Boolean) {
        val sessionId: String = authenticationRepository.getSessionId().first()
        seriesRepository.setSeriesFavoriteStatus(
            seriesId = seriesId,
            sessionId = sessionId,
            isFavorite = isFavorite
        )
    }
}