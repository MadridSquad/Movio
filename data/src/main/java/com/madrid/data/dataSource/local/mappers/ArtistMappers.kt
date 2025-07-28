package com.madrid.data.dataSource.local.mappers


import com.madrid.data.dataSource.local.table.ArtistTable
import com.madrid.data.dataSource.remote.response.artist.ArtistsResult
import com.madrid.domain.entity.Artist

fun Artist.toArtistEntity(): ArtistTable {
    return ArtistTable(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        description = this.description ?: "",
        role = this.role,
        dateOfBirth = this.dateOfBirth.toString(),
        country = this.country ?: ""
    )
}

fun ArtistTable.toArtist(): Artist {
    return Artist(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        description = this.description,
        role = this.role,
        dateOfBirth = this.dateOfBirth,
        country = this.country
    )
}


fun ArtistsResult.toArtistEntity(): ArtistTable {
    return ArtistTable(
        id = this.id ?: 0,
        name = this.name ?: "",
        imageUrl = "https://image.tmdb.org/t/p/original${this.profilePath}",
        description = this.originalName ?: "",
        role = this.role ?: "",
        dateOfBirth = "",
        country = ""
    )
}