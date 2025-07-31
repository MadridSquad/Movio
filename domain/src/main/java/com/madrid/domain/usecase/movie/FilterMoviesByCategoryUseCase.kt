package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Movie

class FilterMoviesByCategoryUseCase {
    operator fun invoke(movies: List<Movie>, category: Int): List<Movie> {
        return movies.filter { movie ->
            movie.genre.any { it.id == category }
        }
    }
}