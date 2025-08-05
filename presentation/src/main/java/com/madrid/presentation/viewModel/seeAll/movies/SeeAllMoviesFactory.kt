package com.madrid.presentation.viewModel.seeAll.movies

import com.madrid.domain.usecase.movie.FilterMoviesByCategoryUseCase
import com.madrid.domain.usecase.movie.GetNowPlayingMovieUseCase
import com.madrid.domain.usecase.movie.GetTopRatedMoviesUseCase
import com.madrid.domain.usecase.movie.GetUpcomingMovieUseCase
import com.madrid.domain.usecase.search.GetRecommendedMovieUseCase
import javax.inject.Inject

class SeeAllMoviesFactory @Inject constructor(
    private val getTopRateMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMovieUseCase: GetNowPlayingMovieUseCase,
    private val getUpcomingMovieUseCase: GetUpcomingMovieUseCase,
    private val getRecommendedMoviesUseCase: GetRecommendedMovieUseCase,
    private val filterMoviesByCategoryUseCase: FilterMoviesByCategoryUseCase
) {
    fun create(type: SeeAllMoviesType): SeeAllMoviesStrategy {
        return when (type) {
            SeeAllMoviesType.TOP_RATING -> SeeAllTopRatedMovies(
                getTopRatedMoviesUseCase = getTopRateMoviesUseCase,
                filterMoviesByCategoryUseCase = filterMoviesByCategoryUseCase
            )
            SeeAllMoviesType.NOW_PLAYING -> SeeAllNowPlayingMovies(
                getNowPlayingMovieUseCase = getNowPlayingMovieUseCase,
                filterMoviesByCategoryUseCase = filterMoviesByCategoryUseCase
            )
            SeeAllMoviesType.UPCOMING -> SeeAllUpComingMovies(
                getUpcomingMovieUseCase = getUpcomingMovieUseCase,
                filterMoviesByCategoryUseCase = filterMoviesByCategoryUseCase
            )
            SeeAllMoviesType.MORE_RECOMMENDED -> SeeAllMoreRecommendedMovies(
                getRecommendedMovieUseCase = getRecommendedMoviesUseCase,
                filterMoviesByCategoryUseCase = filterMoviesByCategoryUseCase
            )
        }
    }
}