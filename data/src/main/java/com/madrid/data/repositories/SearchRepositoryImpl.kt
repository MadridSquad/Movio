package com.madrid.data.repositories

import com.madrid.data.dataSource.remote.mapper.toArtist
import com.madrid.data.dataSource.remote.mapper.toMovie
import com.madrid.data.dataSource.remote.mapper.toSeries
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SearchRepository
import javax.inject.Inject


class SearchRepositoryImpl @Inject constructor (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : SearchRepository {


    override suspend fun getMoviesByQuery(query: String, page: Int): List<Movie> {
        return remoteDataSource.searchMoviesByQuery(
            name = query,
            page = page
        ).movieResults.map { it.toMovie() }
    }

    override suspend fun getSeriesByQuery(query: String, page: Int): List<Series> {
        return remoteDataSource.searchSeriesByQuery(
            name = query,
            page = page
        ).seriesResults.map { it.toSeries() }
    }

    override suspend fun getArtistsByQuery(query: String, page: Int): List<Artist> {
        return remoteDataSource.searchArtistByQuery(
            name = query,
            page = page
        ).artistResults?.map { it.toArtist() } ?: emptyList()
    }

    // region Recent Searches
    override suspend fun getRecentSearches(): List<String> {
        return localDataSource.getRecentSearches().map {
            it.searchQuery
        }
    }

    override suspend fun addRecentSearchByQuery(query: String) {
        localDataSource.addRecentSearch(query)
    }

    override suspend fun removeRecentSearchByQuery(query: String) {
        localDataSource.removeRecentSearch(query)
    }

    override suspend fun clearAllRecentSearches() {
        localDataSource.clearAllRecentSearches()
    }
    // endregion
}