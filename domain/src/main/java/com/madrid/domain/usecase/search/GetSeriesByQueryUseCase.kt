package com.madrid.domain.usecase.search

import com.madrid.domain.repository.SearchRepository

class GetSeriesByQueryUseCase(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(
        query: String, page: Int = 1
    ) = searchRepository.getSeriesByQuery(query = query, page = page)
}