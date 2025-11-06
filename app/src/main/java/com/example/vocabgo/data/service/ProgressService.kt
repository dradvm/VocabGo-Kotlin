package com.example.vocabgo.data.service

import com.example.vocabgo.data.dto.DoneLessonRequest
import com.example.vocabgo.data.dto.DoneLessonResponse
import com.example.vocabgo.data.dto.ProgressGameLevel
import com.example.vocabgo.data.dto.StartLessonResponse
import com.example.vocabgo.data.dto.StreakDate
import com.example.vocabgo.data.dto.StreakPreviewResponse
import com.example.vocabgo.data.dto.StreakResponse
import com.example.vocabgo.data.dto.UserCurrentGameLevelProgress
import com.example.vocabgo.data.dto.UserStageProgress
import com.example.vocabgo.data.dto.UserStageProgressRequest
import com.example.vocabgo.data.dto.Word
import com.example.vocabgo.data.dto.WordsRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProgressService {
    companion object {
        const val ROOT = "/progress"
    }

    @GET("$ROOT/user")
    suspend fun getUserCurrentGameLevelProgress(): Response<UserCurrentGameLevelProgress>

    @GET("$ROOT/gameLevels")
    suspend fun getGameLevelsProgress() : Response<List<ProgressGameLevel>>

    @POST("$ROOT/gameStages")
    suspend fun getGameStagesProgress(@Body request: UserStageProgressRequest): Response<List<UserStageProgress>>

    @POST("$ROOT/lesson/start")
    suspend fun startLesson(): Response<StartLessonResponse>

    @POST("$ROOT/lesson/done")
    suspend fun doneLesson(@Body request: DoneLessonRequest): Response<DoneLessonResponse>

    @GET("$ROOT/streak")
    suspend fun getUserStreakInfo(): Response<StreakResponse>


    @GET("$ROOT/streak/preview")
    suspend fun getUserStreakPreview(): Response<StreakPreviewResponse>
    @GET("$ROOT/streak/month")
    suspend fun getUserMonthlyStreakInfo(
        @Query("month") month: Int,
        @Query("year") year: Int
    ): Response<List<StreakDate>>

}