package com.madrid.data.dataSource.remote.mapper

import com.madrid.data.dataSource.remote.dto.artist.ArtistDetailsResponse
import com.madrid.data.dataSource.remote.dto.artist.ArtistsResult
import com.madrid.data.dataSource.remote.dto.artist.KnownForNetwork
import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Movie

fun ArtistDetailsResponse.toArtist(): Artist {
    return Artist(
        id = this.id ?: 0,
        name = this.name ?: "",
        role = this.role ?: "",
        dateOfBirth = this.birthDay ?: "",
        country = this.placeOfBirth ?: "",
        overview = this.biography ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.profilePath}",
    )
}

fun ArtistsResult.toArtist(): Artist {
    return Artist(
        id = this.id ?: 0,
        name = this.name ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.profilePath}",
        role = this.role ?: "",
        dateOfBirth = "",
        country = "",
        overview = "",
    )
}

fun KnownForNetwork.toMovie(): Movie {
    return Movie(
        id = this.id ?: 0,
        imageUrl = "https://image.tmdb.org/t/p/original${this.posterPath}",
        title = this.title ?: "",
        rate = this.voteAverage ?: 0.0,
        releaseDate = this.releaseDate ?: "",
        movieDuration = "",
        description = "",
        genre = emptyList(),
    )
}