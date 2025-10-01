package com.example.vocabgo.ui.viewmodel.game.matching

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabgo.ui.viewmodel.flashcard.FlashCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class MatchingItem(
    val word: String,
    val meaning: String,
)

class MatchingViewModel : ViewModel() {

    private val _items = mutableStateListOf(
        MatchingItem("Hello", "Xin chào"),
        MatchingItem("Goodbye", "Tạm biệt"),
        MatchingItem("Thanks", "Cảm ơn"),
        MatchingItem("Please", "Làm ơn"),
        MatchingItem("Sorry", "Xin lỗi"),
    )

    private val _wordItems = _items.shuffled()
    private val _meaningItems = _items.shuffled()
    private val _matchedItems = mutableStateListOf<MatchingItem>()
    private val _isWrong = derivedStateOf {
        wrongSelectedWord.value != null && wrongSelectedMeaning.value != null
    }
    private val _selectedMeaning = mutableStateOf<MatchingItem?>(null)
    private val _selectedWord = mutableStateOf<MatchingItem?>(null)

    private val _wrongSelectedMeaning = mutableStateOf<MatchingItem?>(null)
    private val _wrongSelectedWord = mutableStateOf<MatchingItem?>(null)
    val items: List<MatchingItem> get() = _items
    val wordItems : List<MatchingItem> get() = _wordItems
    val meaningItems : List<MatchingItem> get() = _meaningItems
    val matchedItems: List<MatchingItem> get() = _matchedItems
    val isWrong: State<Boolean> = _isWrong
    val selectedMeaning: State<MatchingItem?> get() = _selectedMeaning
    val selectedWord: State<MatchingItem?> get() = _selectedWord
    val wrongSelectedMeaning: State<MatchingItem?> get() = _wrongSelectedMeaning
    val wrongSelectedWord: State<MatchingItem?> get() = _wrongSelectedWord

    private val isMatched: Boolean
        get() = selectedMeaning.value == selectedWord.value
    private val isBothSelected: Boolean
        get() = selectedMeaning.value != null && selectedWord.value != null

    fun selectMeaning(item: MatchingItem) {
        viewModelScope.launch {
            toggleSelection(item, isMeaning = true)
        }
    }

    fun selectWord(item: MatchingItem) {
        viewModelScope.launch {
            toggleSelection(item, isMeaning = false)
        }
    }

    private suspend fun toggleSelection(item: MatchingItem, isMeaning: Boolean) {
        if (isMeaning) {
            _selectedMeaning.value = if (_selectedMeaning.value == item) null else item
        } else {
            _selectedWord.value = if (_selectedWord.value == item) null else item
        }

        if (isBothSelected) {
            if (isMatched) {
                _matchedItems.add(item)
            } else {
                _wrongSelectedMeaning.value = _selectedMeaning.value
                _wrongSelectedWord.value = _selectedWord.value
                delay(1000)
                _wrongSelectedMeaning.value = null
                _wrongSelectedWord.value = null
            }
            _selectedMeaning.value = null
            _selectedWord.value = null
        }
    }
}
