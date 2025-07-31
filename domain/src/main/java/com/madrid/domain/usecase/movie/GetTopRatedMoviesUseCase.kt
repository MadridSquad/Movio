package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository

class GetTopRatedMoviesUseCase(val movieRepository: MovieRepository) {
    suspend operator fun invoke(page: Int) = movieRepository.getTopRatedMovies(page)
}