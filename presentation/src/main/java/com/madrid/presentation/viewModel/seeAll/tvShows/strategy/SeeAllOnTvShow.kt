package com.madrid.presentation.viewModel.seeAll.tvShows.strategy

import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.series.FilterSeriesByCategoryUseCase
import com.madrid.domain.usecase.series.GetOnAirSeriesUseCase
import com.madrid.presentation.R

class SeeAllOnTvShow(
    private val getOnAirSeriesUseCase: GetOnAirSeriesUseCase,
    private val filterSeriesByCategoryUseCase: FilterSeriesByCategoryUseCase
) : SeeAllTVShowsStrategy {
    override fun getTitle(): Int {
        return R.string.on_tv
    }

    override suspend fun getAllTvShows(page: Int): List<Series> {
        return getOnAirSeriesUseCase(page)
    }

    override suspend fun getTvShowsBasedOnCategory(categoryId: Int, page: Int): List<Series> {
        val tvShows = getAllTvShows(page)
        return filterSeriesByCategoryUseCase(series = tvShows, category = categoryId)
    }
    override fun showTvShowsCategory(): Boolean {
        return false
    }

}