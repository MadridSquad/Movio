package com.madrid.presentation.viewModel.seeAll

import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.homeUseCase.GetAiringTodaySeriesUseCase

class SeeAllAiringTodayTvShow(
    private val getAiringTodaySeriesUseCase: GetAiringTodaySeriesUseCase
) : SeeAllTVShowsStrategy {
    override fun getTitle(): String {
        return "Airing Today"
    }

    override suspend fun getTVShows(page: Int): List<Series> {
        return getAiringTodaySeriesUseCase.invoke()
    }

    override fun showTVShowsCategory(): Boolean {
        return false
    }
}