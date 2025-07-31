package com.madrid.data.repositories

import com.madrid.data.dataSource.local.mappers.toArtist
import com.madrid.data.dataSource.local.mappers.toMovie
import com.madrid.data.dataSource.local.mappers.toSeries
import com.madrid.data.dataSource.local.table.relationship.MovieGenreCrossRef
import com.madrid.data.dataSource.local.table.relationship.SeriesGenreCrossRef
import com.madrid.data.dataSource.mapper.toArtistTable
import com.madrid.data.dataSource.mapper.toMovieTable
import com.madrid.data.dataSource.mapper.toSeriesTable
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SearchRepository
import com.madrid.data.repositories.SeriesRepositoryImpl


class SearchRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : SearchRepository {


    override suspend fun getMoviesByQuery(query: String, page: Int): List<Movie> {
        var result = localDataSource.searchMovieByQueryFromDB(query, page)
        if (result.isEmpty()) {
            remoteDataSource.searchMoviesByQuery(
                name = query,
                page = page
            ).movieResults?.forEach { movieResult ->
                movieResult.genreIds?.forEach { genreId ->
                    localDataSource.relateMovieToGenre(
                        MovieGenreCrossRef(
                            movieId = movieResult.id ?: 0,
                            genreId = genreId
                        )
                    )
                }
                localDataSource.insertMovie(movieResult.toMovieTable())
            }
            result = localDataSource.searchMovieByQueryFromDB(query, page)
        }
        return result.map { it.toMovie() }
    }

    override suspend fun getSeriesByQuery(query: String, page: Int): List<Series> {
        var result = localDataSource.searchSeriesByQueryFromDB(query, page)
        if (result.isEmpty()) {
            remoteDataSource.searchSeriesByQuery(
                name = query,
                page = page
            ).seriesResults?.forEach { seriesResult ->
                seriesResult.genreIds?.forEach { genreId ->
                    localDataSource.relateSeriesToGenre(
                        SeriesGenreCrossRef(
                            seriesId = seriesResult.id ?: 0,
                            genreId = genreId
                        )
                    )
                }
                localDataSource.insertSeries(seriesResult.toSeriesTable())
            }
            result = localDataSource.searchSeriesByQueryFromDB(query, page)
        }
        return result.map { it.toSeries() }
    }

    override suspend fun getArtistsByQuery(query: String, page: Int): List<Artist> {
        val result = localDataSource.searchArtistByQueryFromDB(query, page)
        if (result.isEmpty()) {
            remoteDataSource.searchArtistByQuery(
                name = query,
                page = page
            ).artistResults?.forEach {
                localDataSource.insertArtist(it.toArtistTable())
            }
        }
        return localDataSource.searchArtistByQueryFromDB(query, page).map { it.toArtist() }
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