package com.madrid.domain.repository

import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.SortType
import com.madrid.domain.entity.Trailer
import com.madrid.domain.usecase.movie.GetUserRatedMovieUseCase

interface MovieRepository {
    suspend fun getMovieDetailsById(movieId: Int): Movie
    suspend fun getMovieTrailersById(movieId: Int): List<Trailer>
    suspend fun getMovieCreditsById(movieId: Int): List<Artist>
    suspend fun getMovieReviewsById(movieId: Int): List<Review>
    suspend fun getSimilarMoviesById(movieId: Int): List<Movie>
    suspend fun getRecommendedMovies(page: Int): List<Movie>
    suspend fun getExploreMoreMovies(page: Int): List<Movie>
    suspend fun getTrendingMovies(page: Int): List<Movie>
    suspend fun getMoviesGenres(): List<Genre>
    suspend fun increaseMovieGenreInterestPoints(genreTitle: String)
    suspend fun getMoviesByGenres(): Map<String, List<Movie>>
    suspend fun getTopRatedMovies(page: Int): List<Movie>
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getMoviesByGenreId(page: Int, genreId: Int?, sortBy: SortType): List<Movie>
    suspend fun getNowPlayingMovie(page: Int): List<Movie>
    suspend fun getUpcomingMovie(page: Int): List<Movie>
    suspend fun getMovieGenres(): List<Genre>
    suspend fun getUserMovieRate(sessionId :String):List<GetUserRatedMovieUseCase.RatedMovie>
    suspend fun clearHomeMoviesCache()
    suspend fun addMovieToHistory(movieId: Int)
    suspend fun getAllMoviesInHistory(): List<Movie>
}