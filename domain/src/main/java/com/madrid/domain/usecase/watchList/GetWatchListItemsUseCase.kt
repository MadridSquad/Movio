package com.madrid.domain.usecase.watchList

import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import com.madrid.domain.repository.AuthenticationRepository
import com.madrid.domain.repository.ListRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetWatchListItemsUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val listRepository: ListRepository
) {
    suspend operator fun invoke(listId: Int): WatchListItems {
        val sessionId = authenticationRepository.getSessionId().first()
        return listRepository.getListItemsById(listId,sessionId)
    }

    data class WatchListItems(
        val movies: List<Movie>,
        val series: List<Series>
    )
}