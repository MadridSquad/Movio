package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository
import javax.inject.Inject

class AddMovieToHistoryUseCase @Inject constructor(private val moviesRepository: MovieRepository) {
    suspend operator fun invoke(movieId: Int) =
        moviesRepository.addMovieToHistory(movieId = movieId)
}