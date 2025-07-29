package com.madrid.data.dataSource.local.table.relationship

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.madrid.data.dataSource.local.table.SeriesTable
import com.madrid.data.dataSource.local.table.SeriesGenreTable

@Entity(tableName = "SeriesGenreCrossRef", primaryKeys = ["genreId", "seriesId"])
data class SeriesGenreCrossRef(
    val genreId: Int,
    val seriesId: Int
)

data class SeriesWithGenres(
    @Embedded val series: SeriesTable,
    @Relation(
        parentColumn = "seriesId",
        entityColumn = "genreId",
        associateBy = Junction(SeriesGenreCrossRef::class)
    )
    val genres: List<SeriesGenreTable>
)

data class GenreWithSeries(
    @Embedded val genre: SeriesGenreTable,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "seriesId",
        associateBy = Junction(SeriesGenreCrossRef::class)
    )
    val series: List<SeriesTable>
)