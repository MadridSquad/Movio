package com.madrid.domain.usecase.search

import com.madrid.domain.repository.SearchRepository

class GetMoviesByQueryUseCase(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(
        query: String, page: Int = 1
    ) = searchRepository.getMoviesByQuery(query = query, page = page)
}