package com.example.vocabgo.ui.viewmodel.gamestage

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabgo.data.dto.Lesson
import com.example.vocabgo.data.dto.Stage
import com.example.vocabgo.data.dto.UserLessonProgress
import com.example.vocabgo.data.dto.UserStageProgress
import com.example.vocabgo.data.dto.UserWallet
import com.example.vocabgo.data.dto.UserWalletState
import com.example.vocabgo.data.dto.Word
import com.example.vocabgo.repository.GameRepository
import com.example.vocabgo.repository.ProgressRepository
import com.example.vocabgo.repository.UserRepository
import com.example.vocabgo.repository.VocabularyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameStageViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    private val vocabularyRepository: VocabularyRepository,
    private val progressRepository: ProgressRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _stagesCache = mutableMapOf<String, List<Stage>>()
    private val _stages = MutableStateFlow<List<Stage>>(emptyList())
    private val _stageWordsCache = mutableMapOf<String, List<Word>>()
    private val _stageWords = MutableStateFlow<List<Word>>(emptyList())
    private val _currentStage = MutableStateFlow<Stage?>(null)
    private val _currentLesson = MutableStateFlow<Lesson?>(null)
    private val _currentLessonProgress = MutableStateFlow<UserLessonProgress?>(null)
    private val _currentStagesProgress = MutableStateFlow<Map<String, UserStageProgress>>(emptyMap())
    private val _userWallet = MutableStateFlow<UserWalletState?>(null)
    private val _userStreak = MutableStateFlow<Int>(0)
    private val _isLoading = MutableStateFlow(true)
    private val _isFirstLoading = MutableStateFlow(true)

    private val _errorMessage = MutableStateFlow<String?>(null)
    val isLoading = _isLoading.asStateFlow()
    val isFirstLoading = _isFirstLoading.asStateFlow()
    val errorMessage = _errorMessage.asStateFlow()
    val stages = _stages.asStateFlow()
    val stageWords = _stageWords.asStateFlow()
    val currentStage = _currentStage.asStateFlow()
    val currentStagesProgress = _currentStagesProgress.asStateFlow()
    val currentLesson = _currentLesson.asStateFlow()
    val currentLessonProgress = _currentLessonProgress.asStateFlow()
    val userWallet: StateFlow<UserWalletState?> = _userWallet.asStateFlow()
    val userStreak: StateFlow<Int> = _userStreak.asStateFlow()



    init {
        viewModelScope.launch {
            getStagesForLevel(progressRepository.getUserCurrentGameLevelProgress())
        }
    }

    fun getStagesForLevel(gameLevelId: String) {
        viewModelScope.launch {
            val cachedStage = _stagesCache[gameLevelId]
            if (cachedStage != null) {
                _stages.value = cachedStage
            }
            else {
                val fetchStages = gameRepository.getGameLevelStages(gameLevelId)
                _stagesCache[gameLevelId] = fetchStages as List<Stage>
                _stages.value = fetchStages
            }
        }
    }
    fun getStageWords(stage: Stage) {
        viewModelScope.launch {
            if (_currentStage.value != stage) {
                _currentStage.value = stage
                val cachedStageWords = _stageWordsCache[stage.stageId]
                if (cachedStageWords != null) {
                    _stageWords.value = cachedStageWords
                }
                else {
                    val wordIds: List<String> = stage.stageWords.map { it.wordPosId }
                    val fetchStageWords = vocabularyRepository.getWords(wordIds)
                    _stageWordsCache[stage.stageId] = fetchStageWords as List<Word>
                    _stageWords.value = fetchStageWords
                }
            }

        }
    }
    fun fetchGameStagesProgress() {
        val stageIds = _stages.value.map { it.stageId }
        viewModelScope.launch {
            val result = progressRepository.getGameStagesProgress(stageIds)
            if (result != null) {
                val userStageProgresses = result.associateBy { it.stageId }
                _currentStagesProgress.value = userStageProgresses
            }
        }
    }
    fun fetchUserWallet() {
        viewModelScope.launch {
            val result = userRepository.getStatus()
            _userWallet.value = result
        }
    }
    fun fetchUserStreak() {
        viewModelScope.launch {
            val result = progressRepository.getUserStreakPreview()
            _userStreak.value = result.currentStreak
        }
    }
    suspend fun startLesson(
        userLessonProgress: UserLessonProgress,
        lesson: Lesson,
        stage: Stage
    ): Boolean {
        val started = progressRepository.startLesson()

        if (started) {
            _currentLesson.value = lesson
            _currentLessonProgress.value = userLessonProgress
            getStageWords(stage)
        }

        return started
    }


    fun fetchData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null
                coroutineScope {
                    val streakDeferred = async { fetchUserStreak() }
                    val walletDeferred = async { fetchUserWallet() }
                    val progressDeferred = async { fetchGameStagesProgress() }

                    streakDeferred.await()
                    walletDeferred.await()
                    progressDeferred.await()
                }
                delay(1000)
            }
            catch (e: Exception) {
                _errorMessage.value = e.message ?: "Đã có lỗi xảy ra"
            }
            finally {
                _isLoading.value = false
                _isFirstLoading.value = false
            }

        }

    }
}