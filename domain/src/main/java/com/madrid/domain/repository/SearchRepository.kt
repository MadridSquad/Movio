package com.madrid.domain.repository

import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series

interface SearchRepository {
    suspend fun getMoviesByQuery(query: String, page:Int): List<Movie>
    suspend fun getSeriesByQuery(query: String,page:Int): List<Series>
    suspend fun getArtistsByQuery(query: String, page:Int): List<Artist>

    suspend fun getRecentSearches(): List<String>
    suspend fun addRecentSearchByQuery(query: String)
    suspend fun removeRecentSearchByQuery(query: String)
    suspend fun clearAllRecentSearches()
}