package com.example.vocabgo.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameLevel(
    @Json(name = "game_level_id") val gameLevelId: String,
    @Json(name = "game_level_name") val gameLevelName: String,
    @Json(name = "level_order") val levelOrder: Int,
    @Json(name = "game_level_description") val gameLevelDescription: String
)
@JsonClass(generateAdapter = true)
data class Stage(
    @Json(name = "stage_id") val stageId: String,
    @Json(name = "stage_name") val stageName: String,
    @Json(name = "stage_order") val stageOrder: Int,
    @Json(name = "is_active") val isActive: Boolean,
    @Json(name = "game_level_id") val gameLevelId: String,
    @Json(name = "lesson") val lessons: List<Lesson> = emptyList(),
    @Json(name = "stage_word") val stageWords: List<StageWord> = emptyList(),
    @Json(name = "game_level") val gameLevel: GameLevel
)


@JsonClass(generateAdapter = true)
data class Lesson(
    @Json(name = "lesson_id") val lessonId: String,
    @Json(name = "lesson_name") val lessonName: String,
    @Json(name = "lesson_order") val lessonOrder: Int,
    @Json(name = "lesson_reward") val lessonReward: Int,
    @Json(name = "stage_id") val stageId: String,
    @Json(name = "lesson_type_id") val lessonTypeId: String,
    @Json(name = "lesson_type") val lessonType: LessonType,
    @Json(name = "lesson_question") val lessonQuestion: List<LessonQuestion>
)

@JsonClass(generateAdapter = true)
data class LessonQuestion(
    @Json(name = "lesson_id") val lessonId: String,
    @Json(name = "question_id") val questionId: String,
    @Json(name = "question_count") val questionCount: Int,
    @Json(name = "question") val question: Question
)

@JsonClass(generateAdapter = true)
data class Question(
    @Json(name = "question_id") val questionId: String,
    @Json(name = "question_name") val questionName: String,
    @Json(name = "difficulty_id") val difficultyId: String,
    @Json(name = "difficulty") val difficulty: Difficulty
)

@JsonClass(generateAdapter = true)
data class Difficulty(
    @Json(name = "difficulty_id") val difficultyId: String,
    @Json(name = "difficulty_name") val difficultyName: String,
    @Json(name = "difficulty_score") val difficultyScore: Int
)

@JsonClass(generateAdapter = true)
data class LessonType(
    @Json(name = "lesson_type_id") val lessonTypeId: String,
    @Json(name = "lesson_type_name") val lessonTypeName: String
)

@JsonClass(generateAdapter = true)
data class StageWord(
    @Json(name = "game_stage_word_id") val gameStageWordId: String,
    @Json(name = "stage_id") val stageId: String,
    @Json(name = "word_pos_id") val wordPosId: String
)
