package com.madrid.presentation.screens.homeScreen.paging

import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import com.madrid.presentation.screens.searchScreen.paging.BasePagingSource

class SeeAllSeriesPagingSource(
    private val getAllSeries: suspend (Int)-> List<Series>
) : BasePagingSource<List<Series>>() {

    override suspend fun loadPage(page: Int): List<Series> {
        return getAllSeries(page)
    }
}

class SeeAllSeriesWithGenrePagingSource(
    private val genreId : Int,
    private val getAllSeries: suspend (genre :Int,Int)-> List<Series>
) : BasePagingSource<List<Series>>() {

    override suspend fun loadPage(page: Int): List<Series> {
        return getAllSeries(genreId,page)
    }
}