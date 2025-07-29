package com.madrid.presentation.viewModel.seeAll

import org.koin.java.KoinJavaComponent

class SeeAllTVShowsFactory {
    fun create(seeAllTvShowType: SeeAllTvShowType): SeeAllTVShowsStrategy {
        return when (seeAllTvShowType) {

            SeeAllTvShowType.Top_Rating -> SeeAllTopRatingTVShows(
                getTopRateSeriesUseCase = KoinJavaComponent.getKoin().get()
            )

            SeeAllTvShowType.On_TV -> SeeAllOnTvShow(
                getOnAirSeriesUseCase = KoinJavaComponent.getKoin().get()
            )

            SeeAllTvShowType.Airing_Today -> SeeAllAiringTodayTvShow(
                getAiringTodaySeriesUseCase = KoinJavaComponent.getKoin().get()
            )

            SeeAllTvShowType.More_Recommended -> SeeAllRecommendedTVShow(
                getRecommendedSeriesUseCase = KoinJavaComponent.getKoin().get()
            )
        }
    }
}