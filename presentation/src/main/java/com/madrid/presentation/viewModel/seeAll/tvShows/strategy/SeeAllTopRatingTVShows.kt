package com.madrid.presentation.viewModel.seeAll.tvShows.strategy

import android.util.Log
import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.series.FilterSeriesByCategoryUseCase
import com.madrid.domain.usecase.series.GetTopRatedSeriesUseCase
import com.madrid.presentation.R

class SeeAllTopRatingTVShows(
    private val getTopRateSeriesUseCase: GetTopRatedSeriesUseCase,
    private val filterSeriesByCategoryUseCase: FilterSeriesByCategoryUseCase
) : SeeAllTVShowsStrategy {

    override fun getTitle(): Int {
        return R.string.top_rating
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