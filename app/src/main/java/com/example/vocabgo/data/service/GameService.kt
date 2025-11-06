package com.example.vocabgo.data.service

import com.example.vocabgo.data.dto.AccessToken
import com.example.vocabgo.data.dto.GameLevel
import com.example.vocabgo.data.dto.GoogleLoginRequest
import com.example.vocabgo.data.dto.HelloResponse
import com.example.vocabgo.data.dto.RefreshTokenRequest
import com.example.vocabgo.data.dto.Stage
import com.example.vocabgo.data.dto.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton


interface GameService {
    companion object {
        const val ROOT = "/game"
    }

    @GET("$ROOT/gameLevels")
    suspend fun getGameLevels() : Response<List<GameLevel>>

    @GET("$ROOT/gameLevels/{gameLevelId}/stages")
    suspend fun getGameLevelStages(@Path("gameLevelId") gameLevelId: String) : Response<List<Stage>>
}