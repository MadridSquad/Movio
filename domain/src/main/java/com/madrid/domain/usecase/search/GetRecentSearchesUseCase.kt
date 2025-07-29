package com.madrid.domain.usecase.search

import com.madrid.domain.repository.SearchRepository

class GetRecentSearchesUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke() = searchRepository.getRecentSearches()
}