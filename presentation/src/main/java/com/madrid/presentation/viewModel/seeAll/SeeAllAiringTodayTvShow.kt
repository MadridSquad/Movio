package com.madrid.presentation.viewModel.seeAll

import android.util.Log
import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.series.FilterSeriesByCategoryUseCase
import com.madrid.domain.usecase.series.GetAiringTodaySeriesUseCase

class SeeAllAiringTodayTvShow(
    private val getAiringTodaySeriesUseCase: GetAiringTodaySeriesUseCase,
    private val filterSeriesByCategoryUseCase: FilterSeriesByCategoryUseCase
) : SeeAllTVShowsStrategy {
    override fun getTitle(): String {
        return "Airing Today"
    }

    override suspend fun getAllTvShows(page: Int): List<Series> {
        return getAiringTodaySeriesUseCase(1)
    }

    override suspend fun getTvShowsBasedOnCategory(categoryId: Int): List<Series> {
        val tvShows = getAllTvShows(1)
        Log.d("in airing getTvShowsBasedOnCategory", "in airing getTvShowsBasedOnCategory before filter: $tvShows")
        val x = filterSeriesByCategoryUseCase(series = tvShows, category = categoryId)
        Log.d("in airing getTvShowsBasedOnCategory", "in airing getTvShowsBasedOnCategory: $x")
        return x
    }

    override fun showTvShowsCategory(): Boolean {
        return false
    }

}