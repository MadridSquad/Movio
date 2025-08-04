package com.madrid.data.dataSource.local.mappers


import com.madrid.data.dataSource.local.table.ArtistTable
import com.madrid.domain.entity.Artist

fun Artist.toArtistTable(): ArtistTable {
    return ArtistTable(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        description = this.overview,
        role = this.role,
        dateOfBirth = this.dateOfBirth,
        country = this.country
    )
}

fun ArtistTable.toArtist(): Artist {
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