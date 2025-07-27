package com.madrid.data.repositories

import com.madrid.data.dataSource.remote.mapper.toAllTrendingList
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.AllTrendingItem
import com.madrid.domain.repository.AllTrendingRepository

class AllTrendingRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : AllTrendingRepository {
    override suspend fun getAllTrending(page: Int): List<AllTrendingItem> {
        return remoteDataSource.getAllTrending(page = page).toAllTrendingList()
    }
}