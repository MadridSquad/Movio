package com.madrid.presentation.viewModel.seeAll

import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.homeUseCase.GetRecommendedSeriesUseCase

class SeeAllRecommendedTVShow(
    private val getRecommendedSeriesUseCase: GetRecommendedSeriesUseCase
) : SeeAllTVShowsStrategy {
    override fun getTitle(): String {
        return "More Recommended"
    }

    override suspend fun getTVShows(page: Int): List<Series> {
        return getRecommendedSeriesUseCase.invoke()
    }

    override fun showTVShowsCategory(): Boolean {
        return false
    }
}