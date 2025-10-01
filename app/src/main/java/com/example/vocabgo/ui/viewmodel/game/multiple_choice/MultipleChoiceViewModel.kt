package com.example.vocabgo.ui.viewmodel.game.multiple_choice

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class Word(
    val word: String,
    val meaning: String,
)


class MultipleChoiceViewModel : ViewModel() {
    private val _items = mutableStateListOf(
        Word("Hello", "Xin chào"),
        Word("Goodbye", "Tạm biệt"),
        Word("Thanks", "Cảm ơn"),
    )
    private val _questionWord : Word = _items.first()
    private val _answerSelectWords  = _items.shuffled()
    private val _selectedWord = mutableStateOf<Word?>(null)

    val questionWord : Word get() = _questionWord
    val answerSelectWords : List<Word> get() = _answerSelectWords
    val selectedWord : State<Word?> get() = _selectedWord
    fun select(item: Word) {
        _selectedWord.value = item
    }

}