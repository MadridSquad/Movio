package com.madrid.domain.usecase.search

import com.madrid.domain.repository.SearchRepository

class ClearAllRecentSearchesUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke() = searchRepository.clearAllRecentSearches()
}