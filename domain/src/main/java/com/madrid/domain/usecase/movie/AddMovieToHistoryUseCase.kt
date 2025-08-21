package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository
import com.madrid.domain.usecase.authentication.GetCurrentUserDetailsUseCase
import javax.inject.Inject

class AddMovieToHistoryUseCase @Inject constructor(private val moviesRepository: MovieRepository,private val getCurrentUserDetailsUseCase: GetCurrentUserDetailsUseCase) {
    suspend operator fun invoke(movieId: Int){
        val userId = getCurrentUserDetailsUseCase()?.id
        if (!userId.isNullOrEmpty())
            moviesRepository.addMovieToHistory(movieId = movieId, userId = userId.toInt())
    }
}