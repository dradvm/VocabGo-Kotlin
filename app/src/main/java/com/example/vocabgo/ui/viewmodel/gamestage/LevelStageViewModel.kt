package com.example.vocabgo.ui.viewmodel.gamestage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabgo.data.dto.GameLevel
import com.example.vocabgo.data.dto.ProgressGameLevel
import com.example.vocabgo.data.service.ProgressService
import com.example.vocabgo.repository.GameRepository
import com.example.vocabgo.repository.ProgressRepository
import com.example.vocabgo.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LevelStageViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    private val progressRepository: ProgressRepository
) : ViewModel() {

    // StateFlow để Compose quan sát
    private val _gameLevels = MutableStateFlow<List<GameLevel>>(emptyList())
    private val _gameLevelsProgress = MutableStateFlow<Map<String, ProgressGameLevel>>(emptyMap())
    val gameLevels= _gameLevels.asStateFlow()
    val gameLevelsProgress= _gameLevelsProgress.asStateFlow()
    init {
        viewModelScope.launch {
            fetchGameLevels()
        }
    }

    private suspend fun fetchGameLevels() {
        val result = gameRepository.getGameLevels() // giả sử suspend
        if (result != null) {
            _gameLevels.value = result
        }
    }
    suspend fun fetchGameLevelsProgress() {
        val result = progressRepository.getGameLevelsProgress()
        if (result != null) {
            _gameLevelsProgress.value = result.associateBy { it.gameLevelId }
        }
    }
}
