package com.example.vocabgo.data.dto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class WordsRequest (
    val wordPosIds: List<String> = emptyList<String>()
)


@JsonClass(generateAdapter = true)
data class Word(
    @Json(name = "word_id") val wordId: String,
    @Json(name = "word") val word: String,
    @Json(name = "phonetic") val phonetic: String?,
    @Json(name = "meaning_vi") val meaningVi: String?,
    @Json(name = "audio") val audio: String?,
    @Json(name = "word_pos") val wordPos: List<WordPos>
)

@JsonClass(generateAdapter = true)
data class WordPos(
    @Json(name = "word_pos_id") val wordPosId: String,
    @Json(name = "word_id") val wordId: String,
    @Json(name = "pos_tag_id") val posTagId: String,
    @Json(name = "definition") val definition: String?,
    @Json(name = "level_id") val levelId: String,
    @Json(name = "word_example") val wordExample: List<WordExample>
)

@JsonClass(generateAdapter = true)
data class WordExample(
    @Json(name = "word_example_id") val wordExampleId: String,
    @Json(name = "example") val example: String,
    @Json(name = "example_vi") val exampleVi: String?,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "word_pos_id") val wordPosId: String
)
