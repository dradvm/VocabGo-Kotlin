package com.example.vocabgo.ui.screen.game.speech

import Nunito
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vocabgo.R
import com.example.vocabgo.data.dto.Word
import com.example.vocabgo.ui.components.MyButton
import com.example.vocabgo.ui.components.MyButtonWithOutClick
import com.example.vocabgo.ui.components.MyLottie
import com.example.vocabgo.ui.screen.game.GameButton
import com.example.vocabgo.ui.viewmodel.game.GameQuestion
import info.debatty.java.stringsimilarity.JaroWinkler
import info.debatty.java.stringsimilarity.Levenshtein
@Composable
fun SpeechScreen(
    question: GameQuestion,
    onReadyToCheck: () -> Unit,
    onChecked: (Boolean, Boolean) -> Unit,
    onCheckRegister: (() -> Boolean) -> Unit
) {
    val context = LocalContext.current

    var word by remember(question) { mutableStateOf<Word?>(null) }
    val sentenceWords = remember(question) { mutableStateListOf<String>() }
    var spokenText by remember { mutableStateOf("") }
    var isListening by remember { mutableStateOf(false) }
    // 🎙️ Speech recognizer setup
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    val recognizerIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
        }
    }

    val textMeasurer = rememberTextMeasurer()
    val baseStyle = TextStyle(
        fontFamily = Nunito,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = MyColors.Eel
    )

    val spaceWidth = textMeasurer.measure(AnnotatedString(" "), style = baseStyle).size.width

    // 🔤 Similarity tools
    val levenshtein = remember { Levenshtein() }
    val jaroWinkler = remember { JaroWinkler() }

    val similarityThreshold = 0.85
    val maxDistance = 2
    val successThreshold = 0.6
    val wordWeight = 1
    val keyWordWeight = 3



    fun isRightWord(spoken: String, expected: String): Boolean {
        val target = expected.lowercase().replace(Regex("[^a-z]"), "") // loại bỏ punctuation
        val spokenWords = spoken.lowercase().split(Regex("\\s+")).map { it.replace(Regex("[^a-z]"), "") }

        // Tìm từ giống target nhất trong spokenWords
        val bestMatch = spokenWords.maxByOrNull { jaroWinkler.similarity(target, it) } ?: ""
        val similarity = jaroWinkler.similarity(target, bestMatch)
        val distance = levenshtein.distance(target, bestMatch).toInt()

        return similarity >= similarityThreshold && distance <= maxDistance
    }
// ⚖️ Tính trọng số tổng động (auto-update khi sentenceWords đổi)
    val totalWeightSpoken by remember {
        derivedStateOf {
            sentenceWords.fold(0) { total, w ->
                total + if (isRightWord(spokenText, w)) {
                    if (w == word?.word) keyWordWeight else wordWeight
                } else 0
            }
        }
    }
    val totalWeight by remember {
        derivedStateOf {
            sentenceWords.fold(0) { total, w ->
                total + if (w == word?.word) keyWordWeight else wordWeight
            }
        }
    }
    // 📖 Lấy câu ví dụ
    LaunchedEffect(question) {
        word = question.words.firstOrNull()
        word?.wordPos
            ?.filter { it.wordExample.isNotEmpty() }
            ?.randomOrNull()
            ?.wordExample
            ?.randomOrNull()
            ?.let { example ->
                sentenceWords.addAll(example.example.split(" ").map({it.replace(Regex("[^a-zA-Z']"), "")}))
            }
    }

    LaunchedEffect(spokenText) {
        if (spokenText.isNotEmpty()) {
            Log.d("Speech", spokenText)
            Log.d("Speech", totalWeightSpoken.toString())
            Log.d("Speech", sentenceWords.toString())
            Log.d("Speech", totalWeight.toString())
            Log.d("Speech", (totalWeightSpoken * 1f / totalWeight >= successThreshold).toString())
            onChecked(true, totalWeightSpoken * 1f / totalWeight >= successThreshold)
        }
    }

    // 🧹 Cleanup listener + handle result
    DisposableEffect(Unit) {
        val listener = object : RecognitionListener {
            override fun onResults(results: Bundle?) {
                spokenText = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.firstOrNull() ?: ""
                isListening = false
            }

            override fun onError(error: Int) {
                isListening = false
            }
            override fun onEndOfSpeech() {
                isListening = false
            }

            override fun onBeginningOfSpeech() {}
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onRmsChanged(rmsdB: Float) {}
        }

        speechRecognizer.setRecognitionListener(listener)
        onDispose { speechRecognizer.destroy() }
    }

    // 🎧 Permission request
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { }
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    // 🧱 UI layout
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Đọc câu này",
            style = baseStyle.copy(fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        )

        // 👁️ Hiển thị câu ví dụ
        Box(
            modifier = Modifier.weight(0.8f),
            contentAlignment = Alignment.Center
        ) {
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
                    sentenceWords.forEach { item ->
                        val color = if (isRightWord(spokenText, item))
                            MaterialTheme.colorScheme.primary else MyColors.Eel
                        Text(item, style = baseStyle.copy(color = color))
                    }
                }
            }
        }

        // 🎙️ Nút nói
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.TopCenter
        ) {
            MyButton(
                buttonColor = Color.White,
                shadowColor = MyColors.Swan,
                buttonHeight = 80f,
                border = BorderStroke(2.dp, MyColors.Swan),
                shadowBottomOffset = 2f,
                onClick = {
                    if (!isListening) {
                        speechRecognizer.startListening(recognizerIntent)
                        isListening = true
                    } else {
                        speechRecognizer.stopListening()
                        isListening = false
                    }
                }
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    itemVerticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.microphone_solid_full),
                        contentDescription = "micro",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        text = if (isListening) "Listening..." else "Nhấn để nói",
                        style = baseStyle.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }

        }
    }
}
