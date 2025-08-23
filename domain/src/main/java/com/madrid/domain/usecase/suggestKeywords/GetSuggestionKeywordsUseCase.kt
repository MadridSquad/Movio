package com.madrid.domain.usecase.suggestKeywords

import com.madrid.domain.repository.KeywordsSuggestionWordsRepository
import javax.inject.Inject

class GetSuggestionKeywordsUseCase @Inject constructor(
    private val keywordsSuggestionWordsRepository: KeywordsSuggestionWordsRepository
) {
    suspend operator fun invoke(query:String): List<String> {
            return keywordsSuggestionWordsRepository.getSuggestionKeyWords(query)
    }
}