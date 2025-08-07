package com.madrid.presentation.viewModel.seeAll.tvShows

import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.series.FilterSeriesByCategoryUseCase
import com.madrid.domain.usecase.series.GetRecommendedSeriesUseCase

class SeeAllRecommendedTVShow(
    private val getRecommendedSeriesUseCase: GetRecommendedSeriesUseCase,
    private val filterSeriesByCategoryUseCase: FilterSeriesByCategoryUseCase
) : SeeAllTVShowsStrategy {
    override fun getTitle(): String {
        return "More Recommended"
    }

    override suspend fun getAllTvShows(page: Int): List<Series> {
        return getRecommendedSeriesUseCase(page)
    }

    override suspend fun getTvShowsBasedOnCategory(categoryId: Int,page: Int): List<Series> {
        val tvShows = getAllTvShows(page)
        return filterSeriesByCategoryUseCase(series = tvShows, category = categoryId)
    }
    override fun showTvShowsCategory(): Boolean {
        return false
    }

}