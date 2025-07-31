package com.madrid.presentation.viewModel.seeAll.tvShows

import com.madrid.domain.usecase.series.FilterSeriesByCategoryUseCase
import com.madrid.domain.usecase.series.GetAiringTodaySeriesUseCase
import com.madrid.domain.usecase.series.GetOnAirSeriesUseCase
import com.madrid.domain.usecase.series.GetRecommendedSeriesUseCase
import com.madrid.domain.usecase.series.GetTopRatedSeriesUseCase

class SeeAllTVShowsFactory(
    private val getTopRateSeriesUseCase: GetTopRatedSeriesUseCase,
    private val getOnAirSeriesUseCase: GetOnAirSeriesUseCase,
    private val getAiringTodaySeriesUseCase: GetAiringTodaySeriesUseCase,
    private val getRecommendedSeriesUseCase: GetRecommendedSeriesUseCase,
    private val filterSeriesByCategoryUseCase: FilterSeriesByCategoryUseCase
) {
    fun create(type: SeeAllTvShowType): SeeAllTVShowsStrategy {
        return when (type) {
            SeeAllTvShowType.TOP_RATING -> SeeAllTopRatingTVShows(
                getTopRateSeriesUseCase, filterSeriesByCategoryUseCase
            )
            SeeAllTvShowType.ON_TV -> SeeAllOnTvShow(
                getOnAirSeriesUseCase, filterSeriesByCategoryUseCase
            )
            SeeAllTvShowType.AIRING_TODAY -> SeeAllAiringTodayTvShow(
                getAiringTodaySeriesUseCase, filterSeriesByCategoryUseCase
            )
            SeeAllTvShowType.MORE_RECOMMENDED -> SeeAllRecommendedTVShow(
                getRecommendedSeriesUseCase, filterSeriesByCategoryUseCase
            )
        }
    }
}
