package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository

class GetUpcomingMovieUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(page: Int) = movieRepository.getUpcomingMovie(page)
}