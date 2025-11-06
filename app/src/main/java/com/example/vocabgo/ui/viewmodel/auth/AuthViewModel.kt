package com.example.vocabgo.ui.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabgo.data.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    init {
        viewModelScope.launch {
            combine(
                dataStoreManager.getAcessToken(),
                dataStoreManager.getRefreshToken()
            ) { access, refresh ->
                access.isNotEmpty() || refresh.isNotEmpty()
            }.collect { loggedIn ->
                _isLoggedIn.value = loggedIn
            }
        }
    }


    fun login(accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            dataStoreManager.setAccessToken(accessToken)
            dataStoreManager.setRefreshToken(refreshToken)
            _isLoggedIn.value = true
        }
    }
    fun logout() {
        viewModelScope.launch {
            dataStoreManager.setAccessToken("")
            dataStoreManager.setRefreshToken("")
            _isLoggedIn.value = false
        }
    }
}