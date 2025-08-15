package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Trailer
import javax.inject.Inject

class GetMoviesWithTrailers @Inject constructor(
    val movieTrailersUseCase: GetMovieTrailersUseCase,
) {
    suspend operator fun invoke(movies: List<Movie>): List<Movie> {
        return movies.map { movie ->
            val trailer = movieTrailersUseCase(movie.id).firstOrNull()
            movie.copy(
                trailer = Trailer(
                    key = trailer?.key ?:"",
                    id = trailer?.id ?: ""
                )
            )
        }
    }
}