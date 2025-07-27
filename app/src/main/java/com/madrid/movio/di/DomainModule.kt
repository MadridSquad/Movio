package com.madrid.movio.di


import com.madrid.domain.usecase.homeUseCase.GetAllTrendingUseCase
import com.madrid.domain.usecase.GetExploreMoreMovieUseCase
import com.madrid.domain.usecase.GetRecommendedMovieUseCase
import com.madrid.domain.usecase.mediaDeatailsUseCase.ArtistDetailsUseCase
import com.madrid.domain.usecase.homeUseCase.MoviesByGenresUseCase
import com.madrid.domain.usecase.homeUseCase.SeriesByGenresUseCase
import com.madrid.domain.usecase.mediaDeatailsUseCase.MovieDetailsUseCase
import com.madrid.domain.usecase.mediaDeatailsUseCase.SeriesDetailsUseCase
import com.madrid.domain.usecase.searchUseCase.ArtistUseCase
import com.madrid.domain.usecase.searchUseCase.MovieUseCase
import com.madrid.domain.usecase.searchUseCase.PreferredMediaUseCase
import com.madrid.domain.usecase.searchUseCase.RecentSearchUseCase
import com.madrid.domain.usecase.searchUseCase.SeriesUseCase
import com.madrid.domain.usecase.searchUseCase.TrendingMediaUseCase
import org.koin.dsl.module


val domainModule = module {
    single { ArtistUseCase(get()) }
    single { MovieUseCase(get()) }
    single { SeriesUseCase(get()) }
    single { PreferredMediaUseCase(get()) }
    single { RecentSearchUseCase(get()) }
    single { TrendingMediaUseCase(get()) }
    single { GetExploreMoreMovieUseCase(get()) }
    single { GetRecommendedMovieUseCase(get()) }
    single { MovieDetailsUseCase(get()) }
    single { ArtistDetailsUseCase(get()) }
    single { SeriesDetailsUseCase(get()) }
    single { GetAllTrendingUseCase(get()) }
    single { MoviesByGenresUseCase(get()) }
    single { SeriesByGenresUseCase(get()) }
}