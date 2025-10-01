package com.example.vocabgo.ui.viewmodel.flashcard

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

data class FlashCard(
    val word: String,
    val phonetic: String,
    val meaning: String,
    val uuid: String = UUID.randomUUID().toString(),
    var visible: MutableState<Boolean> = mutableStateOf(true),
    var slideToLeft: MutableState<Boolean> = mutableStateOf(true)
)

class FlashCardViewModel : ViewModel() {
    // State giữ list, để Compose recompose khi thay đổi
    private val _cards = mutableStateListOf(
        FlashCard("Hello", "/həˈləʊ/", "Xin chào"),
        FlashCard("Goodbye", "/ˌɡʊdˈbaɪ/", "Tạm biệt"),
        FlashCard("Thanks", "/θæŋks/", "Cảm ơn"),
        FlashCard("Please", "/pliːz/", "Làm ơn"),
        FlashCard("Sorry", "/ˈsɒri/", "Xin lỗi"),
        FlashCard("Yes", "/jes/", "Vâng"),
        FlashCard("No", "/nəʊ/", "Không")
    )
    val cards: List<FlashCard> get() = _cards

    private val _visible = mutableStateOf(true)
    val visible: State<Boolean> = _visible

    val currentCard: FlashCard?
        get() = _cards.lastOrNull()

    suspend fun onKnow() {
//        hideCard()
        currentCard?.visible?.value = false
        delay(300)
        _cards.removeLastOrNull()
//        if (_cards.isNotEmpty()) {
//            _cards.removeFirstOrNull()
//        }
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
        }
    }

    fun onDontKnowClick() {
        viewModelScope.launch {
            onDontKnow()
        }
    }

    fun hideCard() {
        _visible.value = false
    }

    fun onCardHidden() {
        _visible.value = true
    }
}
