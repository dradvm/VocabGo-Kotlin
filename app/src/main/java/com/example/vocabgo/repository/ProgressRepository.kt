package com.example.vocabgo.repository

import com.example.vocabgo.data.dto.DoneLessonRequest
import com.example.vocabgo.data.dto.DoneLessonResponse
import com.example.vocabgo.data.dto.ProgressGameLevel
import com.example.vocabgo.data.dto.StreakDate
import com.example.vocabgo.data.dto.StreakPreviewResponse
import com.example.vocabgo.data.dto.StreakResponse
import com.example.vocabgo.data.dto.UserStageProgress
import com.example.vocabgo.data.dto.UserStageProgressRequest
import com.example.vocabgo.data.dto.Word
import com.example.vocabgo.data.dto.WordsRequest
import com.example.vocabgo.data.service.ProgressService
import com.example.vocabgo.data.service.VocabularyService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgressRepository @Inject constructor(private val progressService: ProgressService) {
    suspend fun getGameLevelsProgress() : List<ProgressGameLevel>? {
        val response = progressService.getGameLevelsProgress()
        if (response.isSuccessful) {
            return response.body()
        }
        else {
            throw Exception("Error code: ${response.code()}")
        }
    }

    suspend fun getGameStagesProgress(stageIds: List<String>) : List<UserStageProgress>? {
        val response = progressService.getGameStagesProgress(UserStageProgressRequest(stageIds))
        if (response.isSuccessful) {
            return response.body()
        }
        else {
            throw Exception("Error code: ${response.code()}")
        }
    }
    suspend fun startLesson() : Boolean {
        val response = progressService.startLesson()
        if (response.isSuccessful) {
            return response.body()!!.hasEnergy
        }
        else {
            throw Exception("Error code: ${response.code()}")
        }
    }

    suspend fun doneLesson(userProgressLessonId: String, kp: Int?, timeSpent: Int?, accuracyRate: Float?) : DoneLessonResponse? {
        val response = progressService.doneLesson(DoneLessonRequest(userProgressLessonId, kp, timeSpent, accuracyRate))
        if (response.isSuccessful) {
            return response.body()
        }
        else {
            throw Exception("Error code: ${response.code()}")
        }
    }

    suspend fun getUserCurrentGameLevelProgress(): String {
        val response = progressService.getUserCurrentGameLevelProgress()
        if (response.isSuccessful) {
            return response.body()!!.gameLevelId
        }
        else {
            throw Exception("Error code: ${response.code()}")
        }
    }

    suspend fun getUserStreakInfo(): StreakResponse {
        val response = progressService.getUserStreakInfo()
        if (response.isSuccessful) {
            return response.body()!!
        }
        else {
            throw Exception("Error code: ${response.code()}")
        }
    }
    suspend fun getUserStreakPreview(): StreakPreviewResponse {
        val response = progressService.getUserStreakPreview()
        if (response.isSuccessful) {
            return response.body()!!
        }
        else {
            throw Exception("Error code: ${response.code()}")
        }
    }
    suspend fun getUserMonthlyStreakInfo(month: Int, year: Int): List<StreakDate> {
        val response = progressService.getUserMonthlyStreakInfo(month, year)
        if (response.isSuccessful) {
            return response.body()!!
        }
        else {
            throw Exception("Error code: ${response.code()}")
        }
    }
}