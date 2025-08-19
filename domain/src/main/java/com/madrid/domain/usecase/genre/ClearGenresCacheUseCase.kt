package com.madrid.domain.usecase.genre

import com.madrid.domain.repository.MovieRepository
import com.madrid.domain.repository.SeriesRepository
import javax.inject.Inject

class ClearGenresCacheUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke() {
        movieRepository.clearMovieGenres()
        seriesRepository.clearSeriesGenres()
    }
}