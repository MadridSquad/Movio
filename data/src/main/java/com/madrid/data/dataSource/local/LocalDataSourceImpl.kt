package com.madrid.data.dataSource.local

import com.madrid.data.dataSource.local.dao.ArtistDao
import com.madrid.data.dataSource.local.dao.MovieDao
import com.madrid.data.dataSource.local.dao.MovieGenreDao
import com.madrid.data.dataSource.local.dao.RecentSearchDao
import com.madrid.data.dataSource.local.dao.SeriesDao
import com.madrid.data.dataSource.local.dao.SeriesGenreDao
import com.madrid.data.dataSource.local.table.ArtistTable
import com.madrid.data.dataSource.local.table.MovieGenreTable
import com.madrid.data.dataSource.local.table.MovieTable
import com.madrid.data.dataSource.local.table.RecentSearchTable
import com.madrid.data.dataSource.local.table.SeriesGenreTable
import com.madrid.data.dataSource.local.table.SeriesTable
import com.madrid.data.dataSource.local.table.relationship.GenreWithMovies
import com.madrid.data.dataSource.local.table.relationship.GenreWithSeries
import com.madrid.data.dataSource.local.table.relationship.MovieGenreCrossRef
import com.madrid.data.dataSource.local.table.relationship.MovieWithGenres
import com.madrid.data.dataSource.local.table.relationship.SeriesGenreCrossRef
import com.madrid.data.dataSource.local.table.relationship.SeriesWithGenres
import com.madrid.data.dataSource.local.util.calculateOffset
import com.madrid.data.repositories.local.LocalDataSource
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val seriesDao: SeriesDao,
    private val artistDao: ArtistDao,
    private val movieGenreDao: MovieGenreDao,
    private val seriesGenreDao: SeriesGenreDao,
    private val recentSearchDao: RecentSearchDao
) : LocalDataSource {


    override suspend fun insertMovie(movie: MovieTable) {
        movieDao.insertMovie(movie)
    }

    override suspend fun insertSeries(series: SeriesTable) {
        seriesDao.insertSeries(series)
    }

    override suspend fun getTopRatedMovies(): List<MovieTable> {
        return movieDao.getTopRatedMovies()
    }

    override suspend fun insertArtist(artist: ArtistTable) {
        artistDao.insertArtist(artist = artist)
    }

    override suspend fun insertMovieGenre(genre: MovieGenreTable) {
        movieGenreDao.insertGenre(genre)
    }

    override suspend fun insertSeriesGenre(genre: SeriesGenreTable) {
        seriesGenreDao.insertGenre(genre)
    }

    override suspend fun searchMovieByQueryFromDB(query: String, page: Int): List<MovieWithGenres> {
        val offset = calculateOffset(page)
        return movieDao.searchMovies("%$query%", offset)
    }

    override suspend fun searchSeriesByQueryFromDB(query: String, page: Int): List<SeriesWithGenres> {
        val offset = calculateOffset(page)
        return seriesDao.searchSeries("%$query%", offset)
    }

    override suspend fun searchArtistByQueryFromDB(query: String, page: Int): List<ArtistTable> {
        val offset = calculateOffset(page)
        return artistDao.getArtistByName("%$query%", offset)
    }


    override suspend fun getRecentSearches(): List<RecentSearchTable> {
        return recentSearchDao.getRecentSearches()
    }

    override suspend fun addRecentSearch(item: String) {
        recentSearchDao.addRecentSearch(
            RecentSearchTable(
                searchQuery = item,
            )
        )
    }

    override suspend fun removeRecentSearch(item: String) {
        recentSearchDao.removeRecentSearch(
            item
        )
    }

    override suspend fun clearAllRecentSearches() {
        recentSearchDao.clearAllRecentSearches()
    }

    override suspend fun relateMovieToGenre(movieGenreCrossRef: MovieGenreCrossRef) {
        movieDao.insertMovieGenreCrossRef(movieGenreCrossRef)
    }

    override suspend fun increaseMovieGenreInterestPoints(genreTitle: String) {
        movieGenreDao.increaseGenreInterestPoints(genreTitle)
    }

    override suspend fun getAllMovieGenres(): List<MovieGenreTable> {
        return movieGenreDao.getAllGenres()
    }

    override suspend fun getMoviesByGenres(): List<GenreWithMovies> {
        return movieGenreDao.getMoviesByGenres()
    }

    override suspend fun relateSeriesToGenre(seriesGenreEntity: SeriesGenreCrossRef) {
        seriesDao.insertSeriesGenreCrossRef(seriesGenreEntity)
    }

    override suspend fun increaseSeriesGenreInterestPoints(genreTitle: String) {
        seriesGenreDao.increaseGenreInterestPoints(genreTitle)
    }

    override suspend fun getAllSeriesGenres(): List<SeriesGenreTable> {
        return seriesGenreDao.getAllGenres()
    }

    override suspend fun getSeriesByGenres(): List<GenreWithSeries> {
        return seriesGenreDao.getSeriesByGenres()
    }

}