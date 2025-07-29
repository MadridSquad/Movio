package com.madrid.domain.repository

import com.madrid.domain.entity.Cast
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.SimilarMovie
import com.madrid.domain.entity.Trailer
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieDetailsById(movieId: Int): Movie
    suspend fun getMovieTrailersById(movieId: Int): Trailer
    suspend fun getMovieCreditsById(movieId: Int): List<Cast>
    suspend fun getMovieReviewsById(movieId: Int): List<Review>
    suspend fun getSimilarMoviesById(movieId: Int): List<SimilarMovie>

    suspend fun submitMovieRating(rating: Float)
    suspend fun addMovieToFavourites(movieId: Int)
    suspend fun getCollectionsList(): Flow<List<String>>
    suspend fun addNewCollection(collection: String)
    suspend fun addMovieToList(movieId: Int,listName: String): Boolean


}