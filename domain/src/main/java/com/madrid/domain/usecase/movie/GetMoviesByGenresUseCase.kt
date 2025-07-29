package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.MovieRepository

class GetMoviesByGenresUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Map<String, List<Movie>> {
        return movieRepository.getMoviesByGenres()
    }
}