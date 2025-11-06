package com.example.vocabgo.repository

import com.example.vocabgo.data.dto.Word
import com.example.vocabgo.data.dto.WordsRequest
import com.example.vocabgo.data.service.VocabularyService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VocabularyRepository @Inject constructor(private val vocabularyService: VocabularyService) {
    suspend fun getWords(wordIds: List<String>) : List<Word>? {
        val response = vocabularyService.getWords(WordsRequest(wordPosIds = wordIds))
        if (response.isSuccessful) {
            return response.body()
        }
        else {
            throw Exception("Error code: ${response.code()}")
        }
    }


}