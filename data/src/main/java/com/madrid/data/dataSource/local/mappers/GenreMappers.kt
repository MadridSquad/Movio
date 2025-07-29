package com.madrid.data.dataSource.local.mappers

import com.madrid.data.dataSource.local.table.MovieGenreTable
import com.madrid.domain.entity.Genre

fun MovieGenreTable.toGenre(): Genre {
    return Genre(
        id = this.genreId,
        name = this.genreTitle,
        interestPoints = this.searchCount
    )
}


