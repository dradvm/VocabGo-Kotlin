package com.example.vocabgo.repository

import com.example.vocabgo.data.dto.UserProfile
import com.example.vocabgo.data.dto.UserProfileResponse
import com.example.vocabgo.data.dto.UserWallet
import com.example.vocabgo.data.dto.UserWalletState
import com.example.vocabgo.data.service.UserService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository @Inject constructor(private val userService: UserService) {

    suspend fun getStatus(): UserWalletState? {
        val response = userService.getUserWallet()
        if (response.isSuccessful) {
            return response.body()
        }
        else {
            throw Exception("Error code: ${response.code()}")
        }
    }

    suspend fun getUserProfile(): UserProfileResponse? {
        val response = userService.getUserProfile()
        if (response.isSuccessful) {
            return response.body()
        }
        else {
            throw Exception("Error code: ${response.code()}")
        }
    }
}