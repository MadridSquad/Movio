package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Movie
import com.madrid.domain.repository.MovieRepository
import com.madrid.domain.usecase.authentication.GetCurrentUserDetailsUseCase
import javax.inject.Inject

class GetAllMoviesInHistoryUseCase @Inject constructor(private val movieRepository: MovieRepository,private val getCurrentUserDetailsUseCase: GetCurrentUserDetailsUseCase) {
    suspend operator fun invoke(): List<Movie> {
        val userId = getCurrentUserDetailsUseCase()?.id
        if (!userId.isNullOrEmpty()){
            movieRepository.getMoviesGenres()
            return movieRepository.getAllMoviesInHistory(userId = userId.toInt())
        }
        return emptyList()
    }
}