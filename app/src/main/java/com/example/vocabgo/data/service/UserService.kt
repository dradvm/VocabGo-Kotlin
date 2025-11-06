package com.example.vocabgo.data.service

import com.example.vocabgo.data.dto.UserProfile
import com.example.vocabgo.data.dto.UserProfileResponse
import com.example.vocabgo.data.dto.UserWallet
import com.example.vocabgo.data.dto.UserWalletState
import com.example.vocabgo.data.dto.Word
import com.example.vocabgo.data.dto.WordsRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    companion object {
        const val ROOT = "/user"
    }

    @GET("$ROOT/wallet")
    suspend fun getUserWallet() : Response<UserWalletState>

    @POST("$ROOT/profile")
    suspend fun  getUserProfile() : Response<UserProfileResponse>
}