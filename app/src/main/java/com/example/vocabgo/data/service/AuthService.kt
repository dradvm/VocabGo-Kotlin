package com.example.vocabgo.data.service

import com.example.vocabgo.data.dto.AccessToken
import com.example.vocabgo.data.dto.GoogleLoginRequest
import com.example.vocabgo.data.dto.HelloResponse
import com.example.vocabgo.data.dto.RefreshTokenRequest
import com.example.vocabgo.data.dto.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Inject
import javax.inject.Singleton


interface AuthService {
    companion object {
        const val ROOT = "/auth"
    }

    @POST("$ROOT/google-login")
    suspend fun googleLogin(@Body request: GoogleLoginRequest) : Response<Token>
    @POST("$ROOT/refresh-token")
    suspend fun refreshToken(@Body request: RefreshTokenRequest) : Response<AccessToken>
    @GET("/hello")
    suspend fun test() : Response<HelloResponse>
}