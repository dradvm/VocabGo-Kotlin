package com.example.vocabgo.data.interceptor

import android.content.Context
import com.example.vocabgo.data.datastore.DataStoreManager
import com.example.vocabgo.data.dto.AccessToken
import com.example.vocabgo.data.dto.RefreshTokenRequest
import com.example.vocabgo.data.dto.Token
import com.example.vocabgo.data.service.AuthService
import com.example.vocabgo.repository.AuthRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    @ApplicationContext private val context: Context
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.header("Authorization")?.contains("Bearer") == true &&
            responseCount(response) <= 1) {

            val refreshToken = dataStoreManager.getRefreshToken()
            var newAccessToken = ""
            runBlocking {
                val responseToken = RefreshTokenAuthServiceInstance.authService.refreshToken(RefreshTokenRequest(refreshToken.first()))
                if (responseToken.isSuccessful) {
                    val token: AccessToken = responseToken.body() ?: throw Exception("Response body is null")
                    newAccessToken = token.accessToken
                    dataStoreManager.setAccessToken(newAccessToken)
                } else {
                    return@runBlocking response.request.newBuilder().build()
                }
            }

            return response.request.newBuilder()
                .header("Authorization", "Bearer ${newAccessToken}")
                .build()
        }
        return null
    }

    private fun responseCount(response: Response): Int {
        var res = response
        var count = 1
        while (res.priorResponse != null) {
            count++
            res = res.priorResponse!!
        }
        return count
    }

}

private object RefreshTokenAuthServiceInstance {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build() // Không gắn TokenAuthenticator

    val retrofit = Retrofit.Builder()
        .baseUrl(AppConfig.BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    val authService = retrofit.create(AuthService::class.java)
}