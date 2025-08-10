package com.madrid.domain.repository

import com.madrid.domain.entity.WatchList
import com.madrid.domain.usecase.watchList.GetWatchListItemsUseCase

interface ListRepository {
    suspend fun getLists(sessionId: String): List<WatchList>
    suspend fun getListItemsById(listId: Int,sessionId: String): GetWatchListItemsUseCase.WatchListItems
}