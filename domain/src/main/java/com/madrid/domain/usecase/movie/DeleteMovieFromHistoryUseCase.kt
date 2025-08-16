package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteMovieFromHistoryUseCase @Inject constructor(private val moviesRepository: MovieRepository) {
    suspend operator fun invoke(movieId: Int) =
        moviesRepository.deleteMovieFromHistory(movieId = movieId)
}
