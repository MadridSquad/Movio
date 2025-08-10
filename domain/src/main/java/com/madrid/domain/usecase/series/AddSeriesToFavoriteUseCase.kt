package com.madrid.domain.usecase.series

import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.SeriesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddSeriesToFavoriteUseCase @Inject constructor(
    val seriesRepository: SeriesRepository,
    val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(mediaId: Int) {
        val sessionId: String = authenticationRepository.getSessionId().first()
        seriesRepository.addSeriesToFavorite(
            mediaId = mediaId,
            sessionId = sessionId
        )
    }
}