package com.madrid.domain.repository

import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getMovieByQuery(query: String,page:Int): List<Movie>
    suspend fun getSeriesByQuery(query: String,page:Int): List<Series>
    suspend fun getArtistByQuery(query: String,page:Int): List<Artist>

    suspend fun getTopRatedMovies(query: String,page: Int): List<Movie>

    suspend fun getRecommendedMovie(): List<Movie>
    suspend fun getPopularMovie(page: Int): List<Movie>


    suspend fun getRecentSearches(): List<String>
    suspend fun addRecentSearchByQuery(query: String)
    suspend fun removeRecentSearchByQuery(query: String)
    suspend fun clearAllRecentSearches()
}