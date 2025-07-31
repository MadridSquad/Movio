package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.MovieRepository

class GetMovieDetailsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Movie {
        return movieRepository.getMovieDetailsById(movieId)
            .also { movie ->
                movie.genre.forEach { genreTitle ->
                    movieRepository.increaseMovieGenreInterestPoints(genreTitle.name)
                }
            }
    }
}