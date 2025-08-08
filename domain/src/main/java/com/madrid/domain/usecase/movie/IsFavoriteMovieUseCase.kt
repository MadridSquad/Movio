package com.madrid.domain.usecase.movie

import jakarta.inject.Inject

class IsFavoriteMovieUseCase @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) {
    suspend operator fun invoke(
        movieId: Int
    ): Boolean {
        val favoriteMovies = getFavoriteMoviesUseCase()
        return favoriteMovies.any { it.id == movieId }
    }
}