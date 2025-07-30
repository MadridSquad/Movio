package com.madrid.movio.di

import com.madrid.domain.usecase.artist.GetArtistDetailsUseCase
import com.madrid.domain.usecase.artist.GetArtistMoviesUseCase
import com.madrid.domain.usecase.movie.GetMovieDetailsUseCase
import com.madrid.domain.usecase.movie.GetMovieReviewsUseCase
import com.madrid.domain.usecase.movie.GetMovieTopCastUseCase
import com.madrid.domain.usecase.movie.GetMovieTrailersUseCase
import com.madrid.domain.usecase.movie.GetMoviesByGenresUseCase
import com.madrid.domain.usecase.movie.GetSimilarMoviesUseCase
import com.madrid.domain.usecase.search.AddRecentSearchUseCase
import com.madrid.domain.usecase.search.ClearAllRecentSearchesUseCase
import com.madrid.domain.usecase.search.GetArtistsByQueryUseCase
import com.madrid.domain.usecase.search.GetExploreMoreMovieUseCase
import com.madrid.domain.usecase.search.GetMoviesByQueryUseCase
import com.madrid.domain.usecase.search.GetPopularMoviesUseCase
import com.madrid.domain.usecase.search.GetRecentSearchesUseCase
import com.madrid.domain.usecase.search.GetRecommendedMovieUseCase
import com.madrid.domain.usecase.search.GetSeriesByQueryUseCase
import com.madrid.domain.usecase.search.RemoveRecentSearchUseCase
import com.madrid.domain.usecase.series.GetEpisodesForSeasonUseCase
import com.madrid.domain.usecase.series.GetSeriesByGenresUseCase
import com.madrid.domain.usecase.series.GetSeriesDetailsUseCase
import com.madrid.domain.usecase.series.GetSeriesReviewsUseCase
import com.madrid.domain.usecase.series.GetSeriesTopCastUseCase
import com.madrid.domain.usecase.series.GetSeriesTrailersUseCase
import com.madrid.domain.usecase.movie.GetTrendingMoviesUseCase
import com.madrid.domain.usecase.series.GetSimilarSeriesUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    // artist
    singleOf(::GetArtistMoviesUseCase)
    singleOf(::GetArtistDetailsUseCase)
    // search
    singleOf(::AddRecentSearchUseCase)
    singleOf(::ClearAllRecentSearchesUseCase)
    singleOf(::GetArtistsByQueryUseCase)
    singleOf(::GetExploreMoreMovieUseCase)
    singleOf(::GetMoviesByQueryUseCase)
    singleOf(::GetPopularMoviesUseCase)
    singleOf(::GetRecentSearchesUseCase)
    singleOf(::GetRecommendedMovieUseCase)
    singleOf(::GetSeriesByQueryUseCase)
    singleOf(::RemoveRecentSearchUseCase)
    // movies
    singleOf(::GetMovieDetailsUseCase)
    singleOf(::GetMovieReviewsUseCase)
    singleOf(::GetMoviesByGenresUseCase)
    singleOf(::GetMovieTopCastUseCase)
    singleOf(::GetMovieTrailersUseCase)
    singleOf(::GetSimilarMoviesUseCase)
    singleOf(::GetMovieTrailersUseCase)
    singleOf(::GetTrendingMoviesUseCase)


    // series
    singleOf(::GetSeriesByGenresUseCase)
    singleOf(::GetEpisodesForSeasonUseCase)
    singleOf(::GetSeriesDetailsUseCase)
    singleOf(::GetSeriesReviewsUseCase)
    singleOf(::GetSeriesTopCastUseCase)
    singleOf(::GetSeriesTrailersUseCase)
    singleOf(::GetSimilarSeriesUseCase)
}