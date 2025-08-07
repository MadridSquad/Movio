package com.madrid.presentation.viewModel.homeViewModel

import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import com.madrid.domain.entity.SortType
import com.madrid.domain.usecase.movie.GetMoviesByGenreIdUseCase
import com.madrid.domain.usecase.series.GetSeriesByGenreIdUseCase
import com.madrid.presentation.pagination.BasePagingSource
import com.madrid.presentation.viewModel.homeViewModel.HomeCategoriesPagingSource.MixedData

class HomeCategoriesPagingSource(
    private val getMoviesByGenreIdUseCase: GetMoviesByGenreIdUseCase,
    private val getSeriesByGenreIdUseCase: GetSeriesByGenreIdUseCase,
    private val genreId: Int?,
    private val sortBy: SortType
) : BasePagingSource<MixedData>() {

    override suspend fun loadPage(page: Int): List<MixedData> {
        val movies = getMoviesByGenreIdUseCase(page = page, genreId = genreId, sortBy = sortBy)
        val series = getSeriesByGenreIdUseCase(page = page, genreId = genreId, sortBy = sortBy)
        return listOf(MixedData(movies, series))
    }

    data class MixedData(
        val movies: List<Movie>,
        val series: List<Series>
    )
}