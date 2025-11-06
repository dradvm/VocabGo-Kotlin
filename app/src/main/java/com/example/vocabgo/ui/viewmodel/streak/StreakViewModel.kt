package com.example.vocabgo.ui.viewmodel.streak

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabgo.data.dto.StreakDate
import com.example.vocabgo.data.dto.StreakDay
import com.example.vocabgo.repository.ProgressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StreakViewModel @Inject constructor(
    private val progressRepository: ProgressRepository
) : ViewModel() {

    private val _streakCount = mutableIntStateOf(0)
    private val _weekStreak = mutableStateListOf<StreakDay>()
    private val _monthStreak = mutableStateListOf<StreakDate>()
    val streakCount: Int get() = _streakCount.intValue
    val weekStreak: List<StreakDay> get() = _weekStreak
    val monthStreak: List<StreakDate> get() = _monthStreak
    fun fetchStreakCount() {
        viewModelScope.launch {
            val res = progressRepository.getUserStreakInfo()
            _streakCount.intValue = res.currentStreak
            _weekStreak.clear()
            _weekStreak.addAll(res.days)
        }
    }

    fun clearMonthStreak() {
        _monthStreak.clear()
    }

    fun fetchStreakMonth(month: Int, year: Int) {
        viewModelScope.launch {
            val res = progressRepository.getUserMonthlyStreakInfo(month, year)
            _monthStreak.clear()
            _monthStreak.addAll(res)
        }
    }
}