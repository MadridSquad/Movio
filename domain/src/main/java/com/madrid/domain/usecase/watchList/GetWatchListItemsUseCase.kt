package com.madrid.domain.usecase.watchList

import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import com.madrid.domain.repository.ListRepository
import javax.inject.Inject

class GetWatchListItemsUseCase @Inject constructor(private val listRepository: ListRepository) {
    suspend operator fun invoke(listId: Int): WatchListItems {
        return listRepository.getListItemsById(listId)
    }

    data class WatchListItems(
        val movies: List<Movie>,
        val series: List<Series>
    )
}