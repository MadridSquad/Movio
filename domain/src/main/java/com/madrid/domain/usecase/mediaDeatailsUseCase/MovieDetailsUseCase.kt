package com.madrid.domain.usecase.mediaDeatailsUseCase

import com.madrid.domain.entity.Cast
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.SimilarMovie
import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieDetailsUseCase(private val movieRepository: MovieRepository) {
    suspend fun getMovieDetailsById(movieId: Int): Movie =
        movieRepository.getMovieDetailsById(movieId)

    suspend fun getMovieTrailersById(movieId: Int): Trailer =
        movieRepository.getMovieTrailersById(movieId)

    suspend fun getMovieCreditsById(movieId: Int): List<Cast> =
        movieRepository.getMovieCreditsById(movieId)

    suspend fun getMovieReviewsById(movieId: Int): List<Review> =
        movieRepository.getMovieReviewsById(movieId)

    suspend fun getSimilarMoviesById(movieId: Int): List<SimilarMovie> =
        movieRepository.getSimilarMoviesById(movieId)

    suspend fun submitMovieRating(rating: Float) = movieRepository.submitMovieRating(rating)

    suspend fun addMovieToFavourites(movieId: Int) = movieRepository.addMovieToFavourites(movieId)

    suspend fun getCollectionsList(): Flow<List<String>> = movieRepository.getCollectionsList()

    suspend fun addNewCollection(collection: String) = movieRepository.addNewCollection(collection)

    suspend fun addMovieToList(movieId: Int,listName: String): Boolean = movieRepository.addMovieToList(movieId,listName)
}