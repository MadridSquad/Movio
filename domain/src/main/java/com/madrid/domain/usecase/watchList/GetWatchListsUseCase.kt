package com.madrid.domain.usecase.watchList

import com.madrid.domain.entity.WatchList
import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.ListRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetWatchListsUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val listRepository: ListRepository
) {
    suspend operator fun invoke(): List<WatchList> {
        val sessionId = authenticationRepository.getSessionId().first()
        return listRepository.getLists(sessionId)
    }
}