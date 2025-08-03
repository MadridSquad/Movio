package com.madrid.domain.usecase.search

import com.madrid.domain.repository.MovieRepository
import javax.inject.Inject

class GetExploreMoreMovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(page: Int) = movieRepository.getExploreMoreMovies(page)
}