package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Genre
import com.madrid.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieGenresUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<Genre> {
        return movieRepository.getMoviesGenres()
    }
}