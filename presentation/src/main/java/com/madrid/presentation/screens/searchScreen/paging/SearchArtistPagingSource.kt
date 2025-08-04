package com.madrid.presentation.screens.searchScreen.paging

import com.madrid.domain.entity.Artist
import com.madrid.domain.usecase.search.GetArtistsByQueryUseCase

class SearchArtistPagingSource(
    private val query: String,
    private val getArtistsByQueryUseCase: GetArtistsByQueryUseCase
) : BasePagingSource<List<Artist>>() {

    override suspend fun loadPage(page: Int): List<Artist> {
        return getArtistsByQueryUseCase(query = query, page = page)
    }
}
