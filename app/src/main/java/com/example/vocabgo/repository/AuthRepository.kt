package com.example.vocabgo.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialOption
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialCustomException
import androidx.credentials.exceptions.NoCredentialException
import com.example.vocabgo.data.datastore.DataStoreManager
import com.example.vocabgo.data.dto.AccessToken
import com.example.vocabgo.data.dto.GoogleLoginRequest
import com.example.vocabgo.data.dto.RefreshTokenRequest
import com.example.vocabgo.data.dto.Token
import com.example.vocabgo.data.service.AuthService
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.security.SecureRandom
import java.util.Base64
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val authService: AuthService) {

    private val signInWithGoogleOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption
        .Builder(serverClientId = AppConfig.WEB_CLIENT_ID)
        .setNonce(generateSecureRandomNonce())
        .build()


    suspend fun getRequest(credentialOption: CredentialOption): GetCredentialRequest {
        return GetCredentialRequest.Builder()
            .addCredentialOption(credentialOption)
            .build()
    }

    fun generateSecureRandomNonce(byteLength: Int = 32): String {
        val randomBytes = ByteArray(byteLength)
        SecureRandom.getInstanceStrong().nextBytes(randomBytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes)
    }
    suspend fun signIn(request: GetCredentialRequest ,context: Context): Exception? {
        val credentialManager = CredentialManager.create(context)
        val failureMessage = "Sign in failed!"
        var e: Exception? = null
        delay(250)
        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context,
            )
            val credential = result.credential;
            val responseJson: String;
            when (credential) {
                is PublicKeyCredential -> {
                    responseJson = credential.authenticationResponseJson
                }
                is PasswordCredential -> {
                    val username = credential.id
                    val password = credential.password
                }

                is CustomCredential -> {
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        try {
                            val googleIdTokenCredential = GoogleIdTokenCredential
                                .createFrom(credential.data)
                            val response = authService.googleLogin(GoogleLoginRequest(googleIdTokenCredential.idToken))
                            if (response.isSuccessful) {
                                val token : Token = response.body() ?: throw Exception("Response body is null")
                                val dataStoreManager = DataStoreManager(context)
                                dataStoreManager.setAccessToken(token.accessToken)
                                dataStoreManager.setRefreshToken(token.refreshToken)
                            } else {
                                throw Exception("Error code: ${response.code()}")
                            }
                        } catch (e: GoogleIdTokenParsingException) {
                            Log.e(TAG, "Received an invalid google id token response", e)
                        }
                    } else {
                        Log.e(TAG, "Unexpected type of credential")
                    }
                }

                else -> {
                    Log.e(TAG, "Unexpected type of credential")
                }
            }


        } catch (e: GoogleIdTokenParsingException) {
            Log.e(TAG, failureMessage + ": Issue with parsing received GoogleIdToken", e)

        } catch (e: NoCredentialException) {
            Log.e(TAG, failureMessage + ": No credentials found", e)
            return e

        } catch (e: GetCredentialCustomException) {
            Log.e(TAG, failureMessage + ": Issue with custom credential request", e)

        } catch (e: GetCredentialCancellationException) {
            Log.e(TAG, failureMessage + ": Sign-in was cancelled", e)
        }
        return e
    }

    suspend fun googleSignIn(context: Context): Exception? {
        val request = getRequest(signInWithGoogleOption)
        return signIn(request, context)
    }
    suspend fun test(): String {
        var response = authService.test()
        if (response.isSuccessful) {
            return response.body()?.message ?: "Response body is null"
        } else {
            return "Error code: ${response.code()}"
        }
    }
    suspend fun refreshAccessToken(refreshTokenFlow: Flow<String>, context: Context): String {
        val dataStoreManager = DataStoreManager(context)
        val refreshToken = refreshTokenFlow.first()

        if (refreshToken.isEmpty()) {
            throw Exception("Refresh token is empty")
        }

        val response = authService.refreshToken(RefreshTokenRequest(refreshToken))

        if (response.isSuccessful) {
            val token: AccessToken = response.body() ?: throw Exception("Response body is null")
            // Lưu access token mới vào DataStore
            dataStoreManager.setAccessToken(token.accessToken)
            return token.accessToken
        } else {
            throw Exception("Refresh token failed, code: ${response.code()}")
        }
    }

}