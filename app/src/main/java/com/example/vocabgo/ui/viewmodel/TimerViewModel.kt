package com.example.vocabgo.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimerViewModel() : ViewModel() {
    private val _seconds = MutableStateFlow(0)
    val seconds = _seconds.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _seconds.value++
            }
        }
    }
}
