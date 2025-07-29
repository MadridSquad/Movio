package com.madrid.domain.repository

import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Movie

interface ArtistRepository {
    suspend fun getArtistDetailsById(artistId: Int): Artist
    suspend fun getArtistMovies(artistId: Int): List<Movie>
}