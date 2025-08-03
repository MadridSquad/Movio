package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Movie
import javax.inject.Inject

class FilterMoviesByCategoryUseCase @Inject constructor() {
    operator fun invoke(movies: List<Movie>, category: Int): List<Movie> {
        return movies.filter { movie ->
            movie.genre.any { it.id == category }
        }
    }
}