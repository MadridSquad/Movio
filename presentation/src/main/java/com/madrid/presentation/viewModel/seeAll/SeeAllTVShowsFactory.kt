package com.madrid.presentation.viewModel.seeAll

import com.madrid.domain.usecase.series.FilterSeriesByCategoryUseCase
import com.madrid.domain.usecase.series.GetAiringTodaySeriesUseCase
import com.madrid.domain.usecase.series.GetOnAirSeriesUseCase
import com.madrid.domain.usecase.series.GetRecommendedSeriesUseCase
import com.madrid.domain.usecase.series.GetTopRatedSeriesUseCase
import org.koin.java.KoinJavaComponent

class SeeAllTVShowsFactory(
    private val getTopRateSeriesUseCase: GetTopRatedSeriesUseCase,
    private val getOnAirSeriesUseCase: GetOnAirSeriesUseCase,
    private val getAiringTodaySeriesUseCase: GetAiringTodaySeriesUseCase,
    private val getRecommendedSeriesUseCase: GetRecommendedSeriesUseCase,
    private val filterSeriesByCategoryUseCase: FilterSeriesByCategoryUseCase
) {
    fun create(type: SeeAllTvShowType): SeeAllTVShowsStrategy {
        return when (type) {
            SeeAllTvShowType.Top_Rating -> SeeAllTopRatingTVShows(
                getTopRateSeriesUseCase, filterSeriesByCategoryUseCase
            )
            SeeAllTvShowType.On_TV -> SeeAllOnTvShow(
                getOnAirSeriesUseCase, filterSeriesByCategoryUseCase
            )
            SeeAllTvShowType.Airing_Today -> SeeAllAiringTodayTvShow(
                getAiringTodaySeriesUseCase, filterSeriesByCategoryUseCase
            )
            SeeAllTvShowType.More_Recommended -> SeeAllRecommendedTVShow(
                getRecommendedSeriesUseCase, filterSeriesByCategoryUseCase
            )
        }
    }
}
