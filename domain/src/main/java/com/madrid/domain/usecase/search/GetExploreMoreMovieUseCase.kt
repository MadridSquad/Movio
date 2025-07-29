package com.madrid.domain.usecase.search

import com.madrid.domain.repository.MovieRepository

class GetExploreMoreMovieUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(page: Int) = movieRepository.getExploreMoreMovies(page)
}