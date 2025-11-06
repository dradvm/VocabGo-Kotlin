package com.example.vocabgo.ui.viewmodel.reward

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabgo.repository.ProgressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class RewardEvent {
    object NavigateToStreakClaim : RewardEvent()
    object NavigateToMain: RewardEvent()
}
@HiltViewModel
class RewardViewModel @Inject constructor(
    val progressRepository: ProgressRepository
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<RewardEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun doneLesson(userLessonProgressId: String, kp: Int?, timeSpent: Int?, accuracyRate: Float?) {
        viewModelScope.launch {
            val res = progressRepository.doneLesson(userLessonProgressId, kp, timeSpent, accuracyRate)


            if (res != null && res.isStreakCreated) {
                _eventFlow.emit(RewardEvent.NavigateToStreakClaim)
            } else {
                _eventFlow.emit(RewardEvent.NavigateToMain)
            }
        }
    }
}