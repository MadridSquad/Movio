package com.madrid.domain.usecase.watchList

import com.madrid.domain.entity.WatchList
import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.ListRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetWatchListsUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val listRepository: ListRepository,
    private val getWatchListItemsUseCase: GetWatchListItemsUseCase
) {
    suspend operator fun invoke(): List<WatchList> {
        val sessionId = authenticationRepository.getSessionId().first()
        val lists = listRepository.getLists(sessionId)
        return lists.map { list ->
            val items = getWatchListItemsUseCase(list.id)
            list.copy(movieIds = items.movies.map { it.id } + items.series.map { it.id })
        }
    }
}