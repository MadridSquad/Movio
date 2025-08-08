package com.madrid.data.repositories

import android.util.Log
import com.madrid.data.dataSource.local.mappers.toGenre
import com.madrid.data.dataSource.remote.mapper.toWatchList
import com.madrid.data.dataSource.remote.mapper.toWatchListItems
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.WatchList
import com.madrid.domain.repository.ListRepository
import com.madrid.domain.usecase.watchList.GetWatchListItemsUseCase
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : ListRepository {

    override suspend fun getLists(sessionId: String): List<WatchList> {
        return remoteDataSource.getCustomLists(sessionId).map { it.toWatchList() }
    }

    override suspend fun getListItemsById(listId: Int,sessionId: String): GetWatchListItemsUseCase.WatchListItems {
        val moviesGenres = localDataSource.getAllMovieGenres().map { it.toGenre() }.associateBy { it.id }
        val seriesGenres = localDataSource.getAllSeriesGenres().map { it.toGenre() }.associateBy { it.id }
        return remoteDataSource.getCustomListDetails(listId,sessionId).items
            .toWatchListItems(
                moviesGenres, seriesGenres
            )
    }
}

