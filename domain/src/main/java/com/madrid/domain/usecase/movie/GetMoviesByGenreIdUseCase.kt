package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.SortType
import com.madrid.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesByGenreIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(page: Int, genreId: Int?, sortBy: SortType): List<Movie> {
        return movieRepository.getMoviesByGenreId(page, genreId, sortBy)
    }
}