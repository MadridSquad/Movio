package com.madrid.presentation.screens.searchScreen.paging

import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.search.GetSeriesByQueryUseCase

class SearchSeriesPagingSource(
    private val query: String,
    private val getSeriesByQueryUseCase: GetSeriesByQueryUseCase,
) : BasePagingSource<Series>() {

    override suspend fun loadPage(page: Int): List<Series> {
        return getSeriesByQueryUseCase.invoke(query = query, page = page)
    }
}