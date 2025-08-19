package com.madrid.presentation.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class BasePagingSource<T : Any>(
    private val initialPage: Int = 1,
    private val pageSize: Int = 20
) : PagingSource<Int, T>() {

    protected abstract suspend fun loadPage(page: Int): List<T>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val currentPage = params.key ?: initialPage
            val data = loadPage(currentPage)

            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == initialPage) null else currentPage - 1,
                nextKey = if (data.isEmpty()) null else currentPage + 1
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
