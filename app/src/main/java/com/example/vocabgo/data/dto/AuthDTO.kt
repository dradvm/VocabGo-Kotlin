package com.example.vocabgo.data.dto

data class HelloResponse(
    val message: String
)

data class GoogleLoginRequest(val idToken: String)
data class RefreshTokenRequest(val refreshToken: String)
data class AccessToken(val accessToken: String)
data class Token (
    val accessToken: String,
    val refreshToken: String
)
class AuthResponse {
}