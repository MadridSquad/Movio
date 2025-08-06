package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.MovieRepository

class GetAllMoviesInHistoryUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(): List<Movie> {
        return movieRepository.getAllMoviesInHistory()
    }
}