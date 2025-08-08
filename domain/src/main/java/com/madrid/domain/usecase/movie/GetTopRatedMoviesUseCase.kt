package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(page: Int) = movieRepository.getTopRatedMovies(page)
}