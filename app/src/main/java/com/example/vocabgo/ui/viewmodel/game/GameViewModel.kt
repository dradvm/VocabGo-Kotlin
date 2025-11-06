package com.example.vocabgo.ui.viewmodel.game

import android.util.Log
import androidx.compose.runtime.asFloatState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import com.example.vocabgo.data.dto.LessonQuestion
import com.example.vocabgo.data.dto.Question
import com.example.vocabgo.data.dto.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.fold
import kotlin.math.roundToInt

data class GameQuestion(
    val question: Question,
    val words: List<Word>,
    var isPass: Boolean = false,
    var repeat: Int = 0
)

class GameViewModel: ViewModel() {
    private val _questions = MutableStateFlow<List<GameQuestion>?>(null)
    private val _typesNeedSentence = listOf<String>("Speech", "Fill in the blank")
    private val _totalQuestion = MutableStateFlow(0)
    private val _correctProgressIncrement = MutableStateFlow(0f)
    private val _wrongProgressIncrement = MutableStateFlow(0f)
    val questions = _questions.asStateFlow()
    val totalQuestion = _totalQuestion.asStateFlow()
    val correctProgressIncrement = _correctProgressIncrement.asStateFlow()
    val wrongProgressIncrement =  _wrongProgressIncrement.asStateFlow()
    val accuracyRate: Float get()
    = _questions.value!!.fold(  0f) {total, question -> total + if (question.repeat == 0) 1 else 0} / _totalQuestion.value * 100
    fun initGameQuestion(lessonQuestions: List<LessonQuestion>?, words: List<Word>) {
        if (lessonQuestions != null) {
            _questions.value = lessonQuestions.flatMap { lessonQuestion ->
                List(lessonQuestion.questionCount) {
                    val n = getNumberWord(lessonQuestion.question.questionName)
                    val candidateWords = if (lessonQuestion.question.questionName in _typesNeedSentence) {
                        words.filter { it.wordPos.any { wordPos -> wordPos.wordExample.isNotEmpty() } }
                    } else {
                        words
                    }
                    val randomWords = if (n <= candidateWords.size) {
                        candidateWords.shuffled().take(n)
                    } else {
                        candidateWords.shuffled()
                    }

                    GameQuestion(
                        lessonQuestion.question,
                        randomWords
                    )
                }
            }.shuffled()
            _totalQuestion.value = _questions.value!!.size
            _correctProgressIncrement.value = 1f / _totalQuestion.value
            _wrongProgressIncrement.value = _correctProgressIncrement.value / 10
        }
    }
    private fun getNumberWord(questionName: String) : Int {
        return when(questionName) {
            "Matching" -> 5
            "Multiple Choice" -> 3
            else -> 1
        }
    }
    fun handleErrorQuestion(question: GameQuestion) {
        _questions.value = _questions.value?.plus(question.copy(repeat = question.repeat + 1))
    }
}