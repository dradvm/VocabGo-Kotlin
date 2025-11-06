package com.example.vocabgo.ui.screen.game.multiple_choice

import Nunito
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabgo.data.dto.Word
import com.example.vocabgo.ui.components.MyLottie
import com.example.vocabgo.ui.screen.game.GameButton
import com.example.vocabgo.ui.viewmodel.game.GameQuestion

@Composable
fun MultipleChoiceScreen(
    question: GameQuestion,
    onReadyToCheck: () -> Unit,
    onChecked: (Boolean, Boolean) -> Unit,
    onCheckRegister: (() -> Boolean) -> Unit
) {

    // --- State ---
    var questionWord by remember (question) { mutableStateOf<Word?>(null) }
    val answerSelectWords = remember(question) { mutableStateListOf<Word>() }
    var selectedWord by remember { mutableStateOf<Word?>(null) }
    var isChecked by remember { mutableStateOf(false) }
    // --- Logic ---
    fun select(item: Word) {
        selectedWord = item
    }

    // --- Init when question changes ---
    LaunchedEffect(question) {
        questionWord = question.words.first()
        answerSelectWords.clear()
        selectedWord = null
        answerSelectWords.addAll(question.words.shuffled())
    }
    LaunchedEffect(selectedWord) {
        if (selectedWord != null) {
            onReadyToCheck()
        }
    }
    LaunchedEffect(Unit) {
        onCheckRegister {
            isChecked = true
            selectedWord == questionWord
        }
    }

    // --- UI ---
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            "Chọn bản dịch đúng",
            style = TextStyle(
                fontFamily = Nunito,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MyColors.Eel
            )
        )

        Box(
            modifier = Modifier.weight(.45f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyLottie("happy_dog.lottie", 150.dp)
                Box(Modifier.offset(y = -20.dp)) {
                    questionWord?.meaningVi?.let {
                        Text(
                            it,
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(16.dp))
                                .border(
                                    BorderStroke(2.dp, MyColors.Swan),
                                    RoundedCornerShape(16.dp)
                                )
                                .padding(20.dp, 12.dp),
                            style = TextStyle(
                                fontFamily = Nunito,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MyColors.Eel
                            )
                        )
                    }
                }
            }
        }

        Box(
            Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                answerSelectWords.forEach { item ->
                    GameButton(
                        content = item.word,
                        isSelected = (selectedWord == item),
                        isRight = (item == questionWord && isChecked),
                        isWrong = (selectedWord == item && selectedWord != questionWord && isChecked),
                        isDisabled = selectedWord != null && isChecked,
                        buttonHeight = 50f,
                        onClick = { select(item) }
                    )
                }
            }
        }
    }
}
