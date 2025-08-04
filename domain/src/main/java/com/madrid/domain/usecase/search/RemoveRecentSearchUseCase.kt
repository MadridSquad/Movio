package com.madrid.domain.usecase.search

import com.madrid.domain.repository.SearchRepository
import javax.inject.Inject

class RemoveRecentSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(item: String) = searchRepository.removeRecentSearchByQuery(item)
}