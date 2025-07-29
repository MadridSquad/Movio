package com.madrid.movio.di

import com.madrid.presentation.screens.searchScreen.SeeAllForYou.SeeAllForYouViewModel
import com.madrid.presentation.viewModel.detailsViewModel.ActorDetailsViewModel
import com.madrid.presentation.viewModel.detailsViewModel.DetailsMovieViewModel
import com.madrid.presentation.viewModel.detailsViewModel.ReviewsScreenViewModel
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetailsViewModel
import com.madrid.presentation.viewModel.detailsViewModel.SimilarMediaViewModel
import com.madrid.presentation.viewModel.detailsViewModel.TopCastViewModel
import com.madrid.presentation.viewModel.homeViewModel.HomeViewModel
import com.madrid.presentation.viewModel.searchViewModel.SearchViewModel
import com.madrid.presentation.viewModel.seeAll.SeeAllTVShowsFactory
import com.madrid.presentation.viewModel.seeAll.SeeAllTVShowsViewModel
import com.madrid.presentation.viewModel.seeAll.SeeAllTvShowType
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::SearchViewModel)
    viewModelOf(::DetailsMovieViewModel)
    viewModelOf(::SeeAllForYouViewModel)
    viewModelOf(::TopCastViewModel)
    viewModelOf(::ActorDetailsViewModel)
    viewModelOf(::SeriesDetailsViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SeriesDetailsViewModel)
    viewModelOf(::ReviewsScreenViewModel)
    viewModelOf(::SeriesDetailsViewModel)
    viewModelOf(::SimilarMediaViewModel)
    viewModelOf(::SeriesDetailsViewModel)
    viewModel<SeeAllTVShowsViewModel>{ (type: SeeAllTvShowType) ->
        val strategy : SeeAllTVShowsFactory.create(type)
        SeeAllTVShowsViewModel(
            seriesByGenresUseCase = get(),
            getGenresUseCase = get(),
            seeAllTVShowsStrategy = strategy,
            seeAllTopRatingTVShows = get(),
            seeAllOnTvShow = get(),
            seeAllAiringTodayTvShow = get(),
            seeAllRecommendedTVShow = get()
        )
    }
}