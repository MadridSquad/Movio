package com.madrid.movio.di


import com.madrid.domain.usecase.series.GetSeriesGenresUseCase
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
import org.koin.androidx.viewmodel.dsl.viewModel
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
    single { SeeAllTVShowsFactory(get(), get(), get(), get() , get()) }
}