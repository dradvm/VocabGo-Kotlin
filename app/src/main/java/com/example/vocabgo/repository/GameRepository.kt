package com.example.vocabgo.repository

import com.example.vocabgo.data.dto.GameLevel
import com.example.vocabgo.data.dto.Stage
import com.example.vocabgo.data.service.GameService
import com.example.vocabgo.data.service.VocabularyService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(private val gameService: GameService) {
    suspend fun getGameLevels() : List<GameLevel>? {
        val response = gameService.getGameLevels()
        if (response.isSuccessful) {
            return response.body()
        }
        else {
            throw Exception("Error code: ${response.code()}")
        }
    }

    suspend fun getGameLevelStages(gameLevelId: String) : List<Stage>? {
        val response = gameService.getGameLevelStages(gameLevelId)
        if (response.isSuccessful) {
            return response.body()
        }
        else {
            throw Exception("Error code: ${response.code()}")
        }
    }
 }