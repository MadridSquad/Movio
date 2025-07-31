package com.madrid.movio.di


import com.madrid.presentation.screens.searchScreen.SeeAllForYou.SeeAllForYouViewModel
import com.madrid.presentation.viewModel.authentication.MainViewModel
import com.madrid.presentation.viewModel.loginViewModel.LoginViewModel
import com.madrid.presentation.viewModel.detailsViewModel.ActorDetailsViewModel
import com.madrid.presentation.viewModel.detailsViewModel.DetailsMovieViewModel
import com.madrid.presentation.viewModel.detailsViewModel.ReviewsScreenViewModel
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetailsViewModel
import com.madrid.presentation.viewModel.detailsViewModel.SimilarMediaViewModel
import com.madrid.presentation.viewModel.detailsViewModel.TopCastViewModel
import com.madrid.presentation.viewModel.homeViewModel.HomeViewModel
import com.madrid.presentation.viewModel.searchViewModel.SearchViewModel
import com.madrid.presentation.viewModel.seeAll.movies.SeeAllMoviesFactory
import com.madrid.presentation.viewModel.seeAll.movies.SeeAllMoviesType
import com.madrid.presentation.viewModel.seeAll.movies.SeeAllMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import com.madrid.presentation.viewModel.seeAll.tvShows.SeeAllTVShowsFactory
import com.madrid.presentation.viewModel.seeAll.tvShows.SeeAllTVShowsViewModel
import com.madrid.presentation.viewModel.seeAll.tvShows.SeeAllTvShowType
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val presentationModule = module {
    viewModelOf(::SearchViewModel)
    viewModelOf(::DetailsMovieViewModel)
    viewModelOf(::SeeAllForYouViewModel)
    viewModelOf(::TopCastViewModel)
    viewModelOf(::ActorDetailsViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SeriesDetailsViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ReviewsScreenViewModel)
    viewModelOf(::SimilarMediaViewModel)
    viewModelOf(::MainViewModel)
    viewModel { (type: SeeAllTvShowType) ->
        val factory: SeeAllTVShowsFactory = get()
        val strategy = factory.create(type)
        SeeAllTVShowsViewModel(get(), get(), strategy)
    }
    viewModel { (type: SeeAllMoviesType) ->
        val factory: SeeAllMoviesFactory = get()
        val strategy = factory.create(type)
        SeeAllMoviesViewModel(get(), strategy)
    }
    single { SeeAllTVShowsFactory(get(), get(), get(), get() , get()) }
    single { SeeAllMoviesFactory(get(), get(), get(), get() , get()) }
}