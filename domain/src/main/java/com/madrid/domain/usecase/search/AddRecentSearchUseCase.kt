package com.madrid.domain.usecase.search

import com.madrid.domain.repository.SearchRepository
import javax.inject.Inject

class AddRecentSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(item: String) = searchRepository.addRecentSearchByQuery(item)
}