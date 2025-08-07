package com.madrid.presentation.pagination

import com.madrid.domain.entity.Series

class SeeAllSeriesPagingSource(
    private val getAllSeries: suspend (Int) -> List<Series>
) : BasePagingSource<Series>() {

    override suspend fun loadPage(page: Int): List<Series> {
        return getAllSeries(page)
    }
}

class SeeAllSeriesWithGenrePagingSource(
    private val genreId: Int,
    private val getAllSeries: suspend (genre: Int, Int) -> List<Series>
) : BasePagingSource<Series>() {

    override suspend fun loadPage(page: Int): List<Series> {
        return getAllSeries(genreId, page)
    }
}