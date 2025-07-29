package com.madrid.presentation.viewModel.seeAll

import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.homeUseCase.GetTopRatedSeriesUseCase

class SeeAllTopRatingTVShows(
    private val getTopRateSeriesUseCase: GetTopRatedSeriesUseCase
) : SeeAllTVShowsStrategy {
    override fun getTitle(): String {
        return "Top Rating"
    }

    override suspend fun getTVShows(page: Int): List<Series> {
        return getTopRateSeriesUseCase.invoke()
    }

    override fun showTVShowsCategory(): Boolean {
        return false
    }
}