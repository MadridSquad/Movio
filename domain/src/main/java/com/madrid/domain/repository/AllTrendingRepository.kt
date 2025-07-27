package com.madrid.domain.repository

import com.madrid.domain.entity.AllTrendingItem

interface AllTrendingRepository {

    suspend fun getAllTrending(page: Int): List<AllTrendingItem>
}