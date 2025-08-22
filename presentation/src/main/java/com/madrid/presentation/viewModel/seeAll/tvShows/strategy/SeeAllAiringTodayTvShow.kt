package com.madrid.presentation.viewModel.seeAll.tvShows.strategy

import android.util.Log
import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.series.FilterSeriesByCategoryUseCase
import com.madrid.domain.usecase.series.GetAiringTodaySeriesUseCase
import com.madrid.presentation.R

class SeeAllAiringTodayTvShow(
    private val getAiringTodaySeriesUseCase: GetAiringTodaySeriesUseCase,
    private val filterSeriesByCategoryUseCase: FilterSeriesByCategoryUseCase
) : SeeAllTVShowsStrategy {
    override fun getTitle(): Int {
        return R.string.airing_today
    }

    override suspend fun getAllTvShows(page: Int): List<Series> {
        return getAiringTodaySeriesUseCase(page)
    }

    override suspend fun getTvShowsBasedOnCategory(categoryId: Int,page: Int): List<Series> {
        val tvShows = getAllTvShows(page)
        Log.d("in airing getTvShowsBasedOnCategory", "in airing getTvShowsBasedOnCategory before filter: $tvShows")
        val x = filterSeriesByCategoryUseCase(series = tvShows, category = categoryId)
        Log.d("in airing getTvShowsBasedOnCategory", "in airing getTvShowsBasedOnCategory: $x")
        return x
    }

    override fun showTvShowsCategory(): Boolean {
        return false
    }

}