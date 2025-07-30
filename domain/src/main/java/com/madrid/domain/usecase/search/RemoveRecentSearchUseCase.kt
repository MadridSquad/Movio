package com.madrid.domain.usecase.search

import com.madrid.domain.repository.SearchRepository

class RemoveRecentSearchUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(item: String) = searchRepository.removeRecentSearchByQuery(item)
}