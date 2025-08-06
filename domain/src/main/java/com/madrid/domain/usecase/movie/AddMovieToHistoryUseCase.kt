package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository

class AddMovieToHistoryUseCase(private val moviesRepository: MovieRepository) {
    suspend operator fun invoke(movieId: Int) =
        moviesRepository.addMovieToHistory(movieId = movieId)
}