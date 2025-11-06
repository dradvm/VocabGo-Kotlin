package com.example.vocabgo.ui.viewmodel.flashcard

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.vocabgo.data.dto.Word
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

data class FlashCard(
    val word: Word,
    val uuid: String = UUID.randomUUID().toString(),
    var visible: MutableState<Boolean> = mutableStateOf(true),
    var slideToLeft: MutableState<Boolean> = mutableStateOf(true)
)

class FlashCardViewModel : ViewModel() {
    // State giữ list, để Compose recompose khi thay đổi
    private val _cards = mutableStateListOf<FlashCard>()
    private val _dontKnowCards = mutableStateListOf<FlashCard>()
    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    val cards: List<FlashCard> get() = _cards
    val dontKnowCards : List<FlashCard> get() = _dontKnowCards

    val currentCard: FlashCard?
        get() = _cards.lastOrNull()

    private val _isEmpty = mutableStateOf(false)
    val isEmpty : State<Boolean> = _isEmpty
    suspend fun onKnow() {
        currentCard?.visible?.value = false
        delay(300)
        _cards.removeLastOrNull()
        _isEmpty.value = _cards.none {it.visible.value}
    }
    fun onKnowClick() {
        viewModelScope.launch {
            onKnow()
        }
    }
    suspend fun onDontKnow() {
        currentCard?.slideToLeft?.value = false
        currentCard?.visible?.value = false
        delay(300)
        val card: FlashCard? = _cards.removeLastOrNull()
        if (card != null) {
            _cards.add(0, card.copy(visible = mutableStateOf(true), slideToLeft = mutableStateOf(true) ))
            if (_dontKnowCards.find { it -> it.uuid == card.uuid } == null) {
                _dontKnowCards.add(card)
            }
        }
    }
    fun onDontKnowClick() {
        viewModelScope.launch {
            onDontKnow()
        }
    }
    suspend fun setCardsFromStageWords(words: List<Word>) {
        _isLoading.value = true
        _cards.clear()
        _cards.addAll(
            words.map {
                FlashCard(word = it)
            }
        )
        delay(1000)
        _isLoading.value = false
    }
}
