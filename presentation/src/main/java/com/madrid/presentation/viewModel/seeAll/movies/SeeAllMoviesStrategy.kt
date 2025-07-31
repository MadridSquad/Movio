package com.madrid.presentation.viewModel.seeAll.movies

import com.madrid.domain.entity.Movie

interface SeeAllMoviesStrategy {
    fun getTitle(): String
    suspend fun getAllMovies(page: Int): List<Movie>
    suspend fun getMoviesBasedOnCategory(categoryId: Int, page: Int): List<Movie>
}