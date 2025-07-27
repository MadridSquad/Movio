package com.madrid.data.repositories

import com.madrid.data.dataSource.remote.mapper.toCredits
import com.madrid.data.dataSource.remote.mapper.toMovie
import com.madrid.data.dataSource.remote.mapper.toReviewResult
import com.madrid.data.dataSource.remote.mapper.toSimilarMovie
import com.madrid.data.dataSource.remote.mapper.toTrailer
import com.madrid.data.dataSource.local.mappers.toMovieGenreEntity
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.Cast
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.SimilarMovie
import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow

class MovieDetailsRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : MovieDetailsRepository {

    override suspend fun getMovieDetailsById(movieId: Int): Movie {
        val movieResponse = remoteDataSource.getMovieDetailsById(movieId)
        movieResponse.movieGenres.map { genre ->
            val genreEntity = genre.toMovieGenreEntity()
            localDataSource.increaseMovieGenreSeenCount(genreEntity.genreTitle)
        }
        return movieResponse.toMovie()
    }

    override suspend fun getMovieTrailersById(movieId: Int): Trailer {
        return remoteDataSource.getMovieTrailersById(movieId).toTrailer()
    }

    override suspend fun getMovieCreditsById(movieId: Int): List<Cast> {
        return remoteDataSource.getMovieCreditById(movieId).toCredits().cast ?: emptyList()
    }

    override suspend fun getMovieReviewsById(movieId: Int): List<Review> {
        return remoteDataSource.getMovieReviewsById(movieId).toReviewResult().results ?: emptyList()
    }

    override suspend fun getSimilarMoviesById(movieId: Int): List<SimilarMovie> {
        return remoteDataSource.getSimilarMoviesById(movieId).similarMovie?.map { it.toSimilarMovie() }
            ?: emptyList()
    }

    override suspend fun submitMovieRating(rating: Float) {
        TODO("Not yet implemented")
    }

    override suspend fun addMovieToFavourites(movieId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getCollectionsList(): Flow<List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun addNewCollection(collection: String) {
        TODO("Not yet implemented")
    }

    override suspend fun addMovieToList(movieId: Int, listName: String): Boolean {
        TODO("Not yet implemented")
    }
}