package com.madrid.data.datasource.local.mappers

import com.madrid.data.datasource.local.table.MovieGenreTable
import com.madrid.data.datasource.local.table.SeriesGenreTable
import com.madrid.domain.entity.Genre

fun MovieGenreTable.toGenre(): Genre {
    return Genre(
        id = this.genreId,
        name = this.genreTitle,
        interestPoints = this.interestPoints
    )
}

fun SeriesGenreTable.toGenre(): Genre {
    return Genre(
        id = this.genreId,
        name = this.genreTitle,
        interestPoints = this.interestPoints
    )
}


