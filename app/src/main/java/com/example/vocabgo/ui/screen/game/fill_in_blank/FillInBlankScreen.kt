package com.example.vocabgo.ui.screen.game.fill_in_blank

import Nunito
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabgo.R
import com.example.vocabgo.data.dto.Word
import com.example.vocabgo.ui.components.MyLottie
import com.example.vocabgo.ui.screen.game.rememberKeyboardOpenState
import com.example.vocabgo.ui.viewmodel.game.GameQuestion
import java.util.UUID
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FillInBlankScreen(
    question: GameQuestion,
    onReadyToCheck: () -> Unit,
    onChecked: (Boolean, Boolean) -> Unit,
    onCheckRegister: (() -> Boolean) -> Unit
) {
    val isFocusTextField by rememberKeyboardOpenState()
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var word by remember(question) { mutableStateOf<Word?>(null) }
    val sentenceWords = remember(question) { mutableStateListOf<String>() }
    val sentenceViWords = remember(question) { mutableStateListOf<String>() }
    var text by remember { mutableStateOf("") }

    val textMeasurer = rememberTextMeasurer()
    val baseStyle = TextStyle(
        fontFamily = Nunito,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = MyColors.Eel
    )
    var isChecked by remember { mutableStateOf(false) }
    val isFocus by remember {
        derivedStateOf {
            text.isNotEmpty()
        }
    }
    val isWrong by remember {
        derivedStateOf {
            isChecked && word?.word?.lowercase() != text.lowercase()
        }
    }
    val isRight by remember {
        derivedStateOf {
            isChecked && word?.word?.lowercase() == text.lowercase()
        }
    }

    val wordWidth = textMeasurer.measure(AnnotatedString(word?.word ?: ""), style = baseStyle).size.width
    val spaceWidth = textMeasurer.measure(AnnotatedString(" "), style = baseStyle).size.width

    // ✅ chỉ chạy 1 lần khi question thay đổi
    LaunchedEffect(question) {
        word = question.words.first()
        word?.wordPos
            ?.filter { it.wordExample.isNotEmpty() }
            ?.randomOrNull()
            ?.wordExample
            ?.randomOrNull()
            ?.let { example ->
                sentenceWords.addAll(example.example.split(" ").map({it.replace(Regex("[^a-zA-Z']"), "")}))
                sentenceViWords.addAll(example.exampleVi?.split(" ")
                    ?.map { it.lowercase() } ?: emptyList())
            }
    }
    LaunchedEffect(text) {
        if (text.length > 0) {
            onReadyToCheck()
        }
    }
    LaunchedEffect(Unit) {
        onCheckRegister {
            focusManager.clearFocus(force = true)
            isChecked = true
            word?.word?.lowercase() == text.lowercase()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        // ⬆ Tiêu đề
        Text(
            text = "Hoàn tất bản dịch",
            style = baseStyle.copy(fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        )

        // ⬆ Câu tiếng Việt
        Box(contentAlignment = Alignment.Center) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyLottie("happy_dog.lottie", 150.dp)
                FlowRow(
                    modifier = Modifier
                        .offset(y = (-20).dp)
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .border(BorderStroke(2.dp, MyColors.Swan), RoundedCornerShape(16.dp))
                        .padding(20.dp, 12.dp),
                    horizontalArrangement = Arrangement.spacedBy((spaceWidth / 2).dp)
                ) {
                    sentenceViWords.forEach { item ->
                        Text(item, style = baseStyle)
                    }
                }
            }
        }

        // ⬇ Câu tiếng Anh (với ô trống)

        Column (
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(MyColors.Polar, RoundedCornerShape(12.dp))
                    .border(2.dp, MyColors.Swan, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.TopStart
            ) {
                FlowRow(horizontalArrangement = Arrangement.spacedBy((spaceWidth / 2).dp)) {
                    sentenceWords.forEach { item ->
                        if (item== word?.word) {
                            val focusColor = when {
                                isRight -> MyColors.Owl
                                isWrong -> MyColors.Cardinal
                                isFocus -> MaterialTheme.colorScheme.primary
                                else -> MyColors.Eel
                            }
                            Column(modifier = Modifier.width((wordWidth / 2).dp)) {
                                BasicTextField(
                                    value = text,
                                    onValueChange = { if (!isChecked) text = it },
                                    singleLine = true,
                                    textStyle = baseStyle.copy(color = focusColor),
                                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                                    enabled = !isChecked,
                                    modifier = Modifier.fillMaxWidth().padding(bottom = 2.dp).focusRequester(focusRequester)
                                )
                                HorizontalDivider(thickness = 2.dp, color = focusColor)
                            }
                        } else {
                            Text(item, style = baseStyle)
                        }
                    }
                }
            }
            if (isChecked && isWrong) {
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MyColors.WalkingFish, RoundedCornerShape(12.dp))
                        .border(2.dp, MyColors.Cardinal, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                ) {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Icon(
                                painter = painterResource(R.drawable.circle_xmark_solid_full),
                                contentDescription = null,
                                tint = MyColors.FireAnt,
                                modifier = Modifier.size(30.dp)
                            )
                            Text(
                                text = "Không chính xác",
                                style = baseStyle.copy(fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.FireAnt)
                            )
                        }
                        FlowRow(horizontalArrangement = Arrangement.spacedBy((spaceWidth / 2).dp)) {
                            Text(
                                text = "Đáp án:",
                                style = baseStyle.copy(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.FireAnt)
                            )
                            sentenceWords.forEach { item ->
                                if (item== word?.word) {
                                    Text(item, style = baseStyle.copy(color = MyColors.FireAnt, fontWeight = FontWeight.ExtraBold))
                                } else {
                                    Text(item, style = baseStyle.copy(color = MyColors.FireAnt))
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
