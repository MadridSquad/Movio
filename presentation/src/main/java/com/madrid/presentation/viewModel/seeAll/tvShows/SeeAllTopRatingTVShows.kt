package com.madrid.presentation.viewModel.seeAll.tvShows

import android.util.Log
import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.series.FilterSeriesByCategoryUseCase
import com.madrid.domain.usecase.series.GetTopRatedSeriesUseCase

class SeeAllTopRatingTVShows(
    private val getTopRateSeriesUseCase: GetTopRatedSeriesUseCase,
    private val filterSeriesByCategoryUseCase: FilterSeriesByCategoryUseCase
) : SeeAllTVShowsStrategy {

    override fun getTitle(): String {
        return "Top Rating"
    }

    override suspend fun getAllTvShows(page: Int): List<Series> {
        return getTopRateSeriesUseCase(page)
    }

    override suspend fun getTvShowsBasedOnCategory(categoryId: Int,page: Int): List<Series> {
        Log.d("onGenreSelect", "getTvShowsBasedOnCategory: in strategy: $categoryId")
        val tvShows = getAllTvShows(page)
        Log.d("onGenreSelect", "getTvShowsBasedOnCategory: in strategy before filter: $tvShows")
        return filterSeriesByCategoryUseCase(series = tvShows, category = categoryId)
    }

    override fun showTvShowsCategory(): Boolean {
        return true
    }

}