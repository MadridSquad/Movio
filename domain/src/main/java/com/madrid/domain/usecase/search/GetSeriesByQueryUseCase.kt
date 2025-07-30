package com.madrid.domain.usecase.search

import com.madrid.domain.entity.Series
import com.madrid.domain.repository.SearchRepository
import com.madrid.domain.repository.SeriesRepository

class GetSeriesByQueryUseCase(
    private val seriesRepository: SeriesRepository,
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(
        query: String, page: Int = 1
    ): List<Series> {
        val genres = seriesRepository.getSeriesGenres()
        val genresMap = genres.associateBy { it.name }
        return searchRepository.getSeriesByQuery(query = query, page = page)
            .sortedByDescending { movie ->
                movie.genre.sumOf { genre ->
                    genresMap[genre]?.interestPoints ?: 0
                }
            }
    }
}