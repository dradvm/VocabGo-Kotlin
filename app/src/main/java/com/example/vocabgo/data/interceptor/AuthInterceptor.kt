package com.example.vocabgo.data.interceptor

import com.example.vocabgo.data.datastore.DataStoreManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val accessToken = runBlocking {
            dataStoreManager.getAcessToken().firstOrNull()
        }

        val modifiedRequest = if (!accessToken.isNullOrEmpty()) {
            originalRequest.newBuilder()
                .addHeader("Authorization",  "Bearer $accessToken")
                .build()
        } else originalRequest
        return chain.proceed(modifiedRequest)
    }

}