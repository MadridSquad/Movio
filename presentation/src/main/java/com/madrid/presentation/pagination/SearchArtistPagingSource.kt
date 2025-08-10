package com.madrid.presentation.pagination

import com.madrid.domain.entity.Artist
import com.madrid.domain.usecase.search.GetArtistsByQueryUseCase

class SearchArtistPagingSource(
    private val query: String,
    private val getArtistsByQueryUseCase: GetArtistsByQueryUseCase
) : BasePagingSource<Artist>() {

    override suspend fun loadPage(page: Int): List<Artist> {
        return getArtistsByQueryUseCase(query = query, page = page)
    }
}
