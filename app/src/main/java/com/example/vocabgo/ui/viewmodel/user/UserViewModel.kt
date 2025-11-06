package com.example.vocabgo.ui.viewmodel.user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabgo.data.dto.UserProfile
import com.example.vocabgo.data.dto.UserWallet
import com.example.vocabgo.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    private val _userWallet = MutableStateFlow<UserWallet?>(null)
    val userProfile = _userProfile.asStateFlow()
    val userWallet = _userWallet.asStateFlow()
    suspend fun fetchUserProfile() {
        viewModelScope.launch {
            val res = userRepository.getUserProfile()

            _userProfile.value = res?.userProfile
            _userWallet.value = res?.userWallet
        }
    }
}