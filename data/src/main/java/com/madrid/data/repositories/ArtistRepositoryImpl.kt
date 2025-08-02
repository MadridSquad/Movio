package com.madrid.data.repositories

import com.madrid.data.dataSource.remote.mapper.toArtist
import com.madrid.data.dataSource.remote.mapper.toMovie
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ArtistRepository {
    override suspend fun getArtistDetailsById(artistId: Int): Artist {
        return remoteDataSource.getArtistDetailsById(artistId).toArtist()
    }

    override suspend fun getArtistMovies(artistId: Int): List<Movie> {
        return remoteDataSource.getArtistMovies(artistId).map { it.toMovie() }
    }
}