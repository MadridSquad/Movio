package com.madrid.presentation.viewModel.seeAll

import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.homeUseCase.GetOnAirSeriesUseCase

class SeeAllOnTvShow(
    private val getOnAirSeriesUseCase: GetOnAirSeriesUseCase
) : SeeAllTVShowsStrategy {
    override fun getTitle(): String {
        return "On TV"
    }

    override suspend fun getTVShows(page: Int): List<Series> {
        return getOnAirSeriesUseCase.invoke()
    }

    override fun showTVShowsCategory(): Boolean {
        return false
    }
}