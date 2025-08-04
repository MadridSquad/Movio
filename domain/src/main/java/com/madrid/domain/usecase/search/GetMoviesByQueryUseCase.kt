package com.madrid.domain.usecase.search

import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.MovieRepository
import com.madrid.domain.repository.SearchRepository
import javax.inject.Inject

class GetMoviesByQueryUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(
        query: String, page: Int = 1
    ): List<Movie> {
        val genres = movieRepository.getMoviesGenres()
        val genresMap = genres.associateBy { it.name }
        return searchRepository.getMoviesByQuery(query = query, page = page)
            .sortedByDescending { movie ->
                movie.genre.sumOf { genre ->
                    genresMap[genre.name]?.interestPoints ?: 0
                }
            }
    }
}