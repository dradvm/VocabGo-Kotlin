package com.example.vocabgo.ui.screen.game.matching

import Nunito
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabgo.data.dto.Word
import com.example.vocabgo.ui.components.MyButton
import com.example.vocabgo.ui.components.SecondaryButton
import com.example.vocabgo.ui.screen.game.GameButton
import com.example.vocabgo.ui.viewmodel.game.GameQuestion
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchingGameScreen(
    question: GameQuestion,
    onReadyToCheck: () -> Unit,
    onChecked: (Boolean, Boolean) -> Unit,
    onCheckRegister: (() -> Boolean) -> Unit
) {

    // region --- State ---
    val wordItems = remember(question) { mutableStateListOf<Word>() }
    val meaningItems = remember(question) { mutableStateListOf<Word>() }
    val matchedItems = remember { mutableStateListOf<Word>() }

    var selectedWord by remember { mutableStateOf<Word?>(null) }
    var selectedMeaning by remember { mutableStateOf<Word?>(null) }

    var wrongSelectedWord by remember { mutableStateOf<Word?>(null) }
    var wrongSelectedMeaning by remember { mutableStateOf<Word?>(null) }

    val isWrong by remember {
        derivedStateOf {
            wrongSelectedWord != null && wrongSelectedMeaning != null
        }
    }
    val isChecked by remember {
        derivedStateOf {
            matchedItems.size == wordItems.size && matchedItems.size > 0
        }
    }
    val coroutineScope = rememberCoroutineScope()
    // endregion

    // region --- Functions ---
    fun isMatched(): Boolean {
        return selectedWord == selectedMeaning
    }

    fun isBothSelected(): Boolean {
        return selectedWord != null && selectedMeaning != null
    }

    fun resetSelection() {
        selectedWord = null
        selectedMeaning = null
    }

    suspend fun toggleSelection(item: Word, isMeaning: Boolean) {
        if (isMeaning) {
            selectedMeaning = if (selectedMeaning == item) null else item
        } else {
            selectedWord = if (selectedWord == item) null else item
        }

        if (isBothSelected()) {
            if (isMatched()) {
                matchedItems.add(item)
            } else {
                wrongSelectedMeaning = selectedMeaning
                wrongSelectedWord = selectedWord
                delay(1000)
                wrongSelectedMeaning = null
                wrongSelectedWord = null
            }
            resetSelection()
        }
    }
    fun selectMeaning(item: Word) {
        coroutineScope.launch {
            toggleSelection(item, isMeaning = true)
        }
    }

    fun selectWord(item: Word) {
        coroutineScope.launch {
            toggleSelection(item, isMeaning = false)
        }
    }



    // endregion

    // region --- Init ---
    LaunchedEffect(question) {
        wordItems.clear()
        meaningItems.clear()
        matchedItems.clear()
        selectedWord = null
        selectedMeaning = null
        wrongSelectedMeaning = null
        wrongSelectedWord = null

        wordItems.addAll(question.words.shuffled())
        meaningItems.addAll(question.words.shuffled())
    }

    LaunchedEffect(isChecked) {
        if (isChecked) {
            onChecked(true, true)
        }
    }

    // endregion

    // region --- UI ---
    Column(
        Modifier.fillMaxSize()
    ) {
        Text(
            "Nhấn vào các cặp tương ứng",
            style = TextStyle(
                fontFamily = Nunito,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MyColors.Eel
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    meaningItems.forEach { item ->
                        GameButton(
                            item.meaningVi ?: "",
                            selectedMeaning == item,
                            matchedItems.contains(item),
                            wrongSelectedMeaning == item,
                            matchedItems.contains(item) || isWrong,
                            onClick = {
                                selectMeaning(item)
                            }
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    wordItems.forEach { item ->
                        GameButton(
                            item.word,
                            selectedWord == item,
                            matchedItems.contains(item),
                            wrongSelectedWord == item,
                            matchedItems.contains(item) || isWrong,
                            onClick = {
                                selectWord(item)
                            }
                        )
                    }
                }
            }
        }
    }
    // endregion
}
