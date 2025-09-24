package com.example.vocabgo.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


class DataStoreKeys {
    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }
}


@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "vocabgo_preferences")
    }

    suspend fun setAccessToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[DataStoreKeys.ACCESS_TOKEN] = token
        }
    }
    suspend fun setRefreshToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[DataStoreKeys.REFRESH_TOKEN] = token
        }
    }

    fun getAcessToken(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[DataStoreKeys.ACCESS_TOKEN] ?: ""
        }
    }

    fun getRefreshToken(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[DataStoreKeys.REFRESH_TOKEN] ?: ""
        }
    }
}