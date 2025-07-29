package com.madrid.data.dataSource.local.mappers


import com.madrid.data.dataSource.local.entity.ArtistEntity
import com.madrid.data.dataSource.remote.dto.artist.ArtistsResult
import com.madrid.domain.entity.Artist

fun Artist.toArtistEntity(): ArtistEntity {
    return ArtistEntity(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        description = this.overview,
        role = this.role,
        dateOfBirth = this.dateOfBirth.toString(),
        country = this.country
    )
}

fun ArtistEntity.toArtist(): Artist {
    return Artist(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        overview = this.description,
        role = this.role,
        dateOfBirth = this.dateOfBirth,
        country = this.country
    )
}


fun ArtistsResult.toArtistEntity(): ArtistEntity {
    return ArtistEntity(
        id = this.id ?: 0,
        name = this.name ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.profilePath}",
        description = this.originalName ?: "",
        role = this.role ?: "",
        dateOfBirth = "",
        country = ""
    )
}