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
    viewModel { SeriesDetailsViewModel(get(), get()) }
    viewModelOf(::ReviewsScreenViewModel)
    viewModel { SeriesDetailsViewModel(get(), get()) }
    viewModelOf(::SimilarMediaViewModel)
    viewModel { SeriesDetailsViewModel(get(), get()) }
}