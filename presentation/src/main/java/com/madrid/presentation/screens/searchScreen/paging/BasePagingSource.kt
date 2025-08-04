package com.madrid.presentation.screens.searchScreen.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class BasePagingSource<T : Any>(private val initialPage: Int = 1) :
    PagingSource<Int, T>() {

    protected abstract suspend fun loadPage(page: Int): T

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val currentPage = params.key ?: 1
            val data = listOf(loadPage(currentPage))

            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == 1) null else currentPage.minus(1),
                nextKey = if (data.isEmpty()) null else currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
