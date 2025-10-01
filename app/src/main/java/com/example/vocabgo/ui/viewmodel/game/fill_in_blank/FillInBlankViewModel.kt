package com.example.vocabgo.ui.viewmodel.game.fill_in_blank

import androidx.compose.ui.text.rememberTextMeasurer
import androidx.lifecycle.ViewModel
import com.example.vocabgo.ui.viewmodel.game.matching.MatchingItem

data class Word(
    val word: String,
    val sentence: String,
    val sentenceVi: String
)

class FillInBlankViewModel: ViewModel() {
    private val _word: Word = Word("beginners","This class is only for beginners","Lớp học này chỉ dành cho người mới bắt đầu")
    private val _sentenceWord = _word.sentence.split(" ")
    private val _sentenceViWord = _word.sentenceVi.split(" ")


    val word: Word get() = _word
    val sentenceWord: List<String> get() = _sentenceWord
    val sentenceViWord: List<String> get() = _sentenceViWord




}