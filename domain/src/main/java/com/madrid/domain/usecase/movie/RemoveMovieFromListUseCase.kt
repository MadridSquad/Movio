package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository
import jakarta.inject.Inject

class RemoveMovieFromListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(mediaId: Int, listId: Int) {
        movieRepository.removeMovieFromList(
            mediaId = mediaId,
            listId = listId,
        )
    }
}