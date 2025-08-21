package com.madrid.data.dataSource.local

import com.madrid.data.dataSource.local.dao.ArtistDao
import com.madrid.data.dataSource.local.dao.MovieDao
import com.madrid.data.dataSource.local.dao.MovieGenreDao
import com.madrid.data.dataSource.local.dao.RecentSearchDao
import com.madrid.data.dataSource.local.dao.SeriesDao
import com.madrid.data.dataSource.local.dao.SeriesGenreDao
import com.madrid.data.dataSource.local.table.ArtistTable
import com.madrid.data.dataSource.local.table.MediaHistoryTable
import com.madrid.data.dataSource.local.table.MovieGenreTable
import com.madrid.data.dataSource.local.table.MovieSection
import com.madrid.data.dataSource.local.table.MovieTable
import com.madrid.data.dataSource.local.table.RecentSearchTable
import com.madrid.data.dataSource.local.table.SectionsMovieTable
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

    override suspend fun insertSectionMovie(movie: SectionsMovieTable) {
        movieDao.insertSectionMovie(movie)
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

    override suspend fun clearMovieGenres() {
        movieGenreDao.deleteAllGenres()
    }

    override suspend fun clearSeriesGenres() {
        seriesGenreDao.deleteAllGenres()
    }

    override suspend fun searchMovieByQueryFromDB(query: String, page: Int): List<MovieWithGenres> {
        val offset = calculateOffset(page)
        return movieDao.searchMovies("%$query%", offset)
    }

    override suspend fun searchSeriesByQueryFromDB(
        query: String,
        page: Int
    ): List<SeriesWithGenres> {
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

    override suspend fun getMovieGenresByIds(ids: List<Int>): List<MovieGenreTable> {
        return movieGenreDao.getGenresByIds(ids)
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

    override suspend fun getSeriesGenresByIds(ids: List<Int>): List<SeriesGenreTable> {
        return seriesGenreDao.getGenresByIds(ids)
    }

    override suspend fun getSeriesByGenres(): List<GenreWithSeries> {
        return seriesGenreDao.getSeriesByGenres()
    }

    override suspend fun getNowPlayingMovies(): List<SectionsMovieTable> {
        return movieDao.getMoviesBySection(MovieSection.NOW_PLAYING.value)
    }

    override suspend fun getUpComingMovies(): List<SectionsMovieTable> {
        return movieDao.getMoviesBySection(MovieSection.UPCOMING.value)
    }

    override suspend fun getTrendingMovies(): List<SectionsMovieTable> {
        return movieDao.getMoviesBySection(MovieSection.TRENDING.value)
    }

    override suspend fun getTopRatingMovies(): List<SectionsMovieTable> {
        return movieDao.getMoviesBySection(MovieSection.TOP_RATED.value)
    }

    override suspend fun getRecommendedMovies(): List<SectionsMovieTable> {
        return movieDao.getMoviesBySection(MovieSection.RECOMMENDED.value)
    }

    override suspend fun clearHomeMoviesCache() {
        movieDao.clearHomeMoviesCache()
    }

    override suspend fun addMovieToHistory(movieId: Int,userId: Int) {
        movieDao.insertHistoryMovie(
            MediaHistoryTable(
                mediaId = movieId,
                mediaType = "Movie",
                addedAt = System.currentTimeMillis(),
                userId = userId
            )
        )
    }

    override suspend fun deleteMovieFromHistory(movieId: Int,userId: Int){
        movieDao.deleteMovieFromHistory(movieId, userId = userId)
    }

    override suspend fun addSeriesToHistory(seriesId: Int,userId: Int) {
        seriesDao.insertHistorySeries(
            MediaHistoryTable(
                mediaId = seriesId,
                mediaType = "Series",
                addedAt = System.currentTimeMillis(),
                userId = userId
            )
        )
    }

    override suspend fun deleteSeriesFromHistory(seriesId: Int,userId: Int){
        seriesDao.deleteSeriesFromHistory(seriesId = seriesId, userId = userId)
    }

    override suspend fun getAllMoviesInHistory(userId: Int) = movieDao.getALLMoviesInHistory(userId = userId)

    override suspend fun getAllSeriesInHistory(userId: Int) = seriesDao.getALLSeriesInHistory(userId = userId)
}