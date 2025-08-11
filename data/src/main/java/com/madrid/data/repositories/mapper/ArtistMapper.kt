package com.madrid.data.repositories.mapper

import com.madrid.data.datasource.local.table.ArtistTable
import com.madrid.data.datasource.remote.dto.artist.ArtistsResult

fun ArtistsResult.toArtistTable(): ArtistTable {
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