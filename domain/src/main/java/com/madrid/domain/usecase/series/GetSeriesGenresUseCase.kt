package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Genre
import com.madrid.domain.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesGenresUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(): List<Genre> {
        return seriesRepository.getSeriesGenres()
    }
}