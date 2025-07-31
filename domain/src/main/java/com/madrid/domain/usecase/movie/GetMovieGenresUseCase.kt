package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Genre
import com.madrid.domain.repository.MovieRepository

class GetMovieGenresUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<Genre> {
        return movieRepository.getMovieGenres()
    }
}