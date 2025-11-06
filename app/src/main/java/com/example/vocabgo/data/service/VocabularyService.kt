package com.example.vocabgo.data.service
import com.example.vocabgo.data.dto.Word
import com.example.vocabgo.data.dto.WordsRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface VocabularyService {
    companion object {
        const val ROOT = "/vocabulary"
    }

    @POST("$ROOT/wordsPosIds")
    suspend fun getWords(@Body request : WordsRequest) : Response<List<Word>>

}