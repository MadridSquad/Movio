package com.madrid.presentation.viewModel.uiStateMapper

import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import com.madrid.presentation.utils.RateFormatter
import com.madrid.presentation.viewModel.searchViewModel.SearchScreenState

fun Movie.toMovieUiState(): SearchScreenState.MovieUiState {
    return SearchScreenState.MovieUiState(
        id = this.id.toString(),
        title = this.title,
        imageUrl = this.imageUrl,
        rating = RateFormatter.formatRate(this.rate),
        category = this.genre.toString()
    )
}

fun Series.toSeriesUiState(): SearchScreenState.SeriesUiState {
    return SearchScreenState.SeriesUiState(
        id = this.id.toString(),
        title = this.title,
        imageUrl = this.imageUrl,
        rating = RateFormatter.formatRate(this.rate),
    )
}

fun Artist.toArtistUiState(): SearchScreenState.ArtistUiState {
    return SearchScreenState.ArtistUiState(
        id = this.id.toString(),
        name = this.name,
        imageUrl = this.imageUrl,
        role = this.role,
        country = this.country,
        description = this.overview
    )
}