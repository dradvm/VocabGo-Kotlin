package com.example.vocabgo.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ProgressGameLevel (
    val gameLevelId: String,
    val stageProgress: Int,
    val totalStage: Int,
    val isStarted: Boolean
)


@JsonClass(generateAdapter = true)
data class UserStageProgress(
    @Json(name = "user_stage_progress_id")
    val userStageProgressId: String,

    @Json(name = "user_id")
    val userId: String,

    @Json(name = "stage_id")
    val stageId: String,

    @Json(name = "is_done")
    val isDone: Boolean,

    @Json(name = "user_lesson_progress")
    val userLessonProgress: List<UserLessonProgress>
)

@JsonClass(generateAdapter = true)
data class UserLessonProgress(
    @Json(name = "user_lesson_progress_id")
    val userLessonProgressId: String,

    @Json(name = "lesson_id")
    val lessonId: String,

    @Json(name = "completed_at")
    val completedAt: String?,

    @Json(name = "created_at")
    val createdAt: String,

    @Json(name = "updated_at")
    val updatedAt: String,

    @Json(name = "user_stage_progress_id")
    val userStageProgressId: String,

    @Json(name = "time_spent")
    val timeSpent: Int,

    @Json(name = "accuracy_rate")
    val accuracyRate: String
)


data class UserStageProgressRequest(
    val stageIds: List<String>
)
data class UserCurrentGameLevelProgress(
    val gameLevelId: String
)
data class StartLessonResponse(
    val hasEnergy: Boolean
)
data class DoneLessonRequest(
    val userLessonProgressId: String,
    val kp: Int?,
    val timeSpent: Int?,
    val accuracyRate: Float?
)

data class DoneLessonResponse(
    val isStreakCreated: Boolean
)

@JsonClass(generateAdapter = true)
data class StreakResponse(
    val currentStreak: Int,
    val days: List<StreakDay>
)

@JsonClass(generateAdapter = true)
data class StreakDay(
    val day: String,
    val isFrozen: Boolean
)

@JsonClass(generateAdapter = true)
data class StreakDate(
    val date: String,
    val isFrozen: Boolean
)

@JsonClass(generateAdapter = true)
data class StreakPreviewResponse(
    val currentStreak: Int,
    val usedFreezeYesterday: Boolean
)