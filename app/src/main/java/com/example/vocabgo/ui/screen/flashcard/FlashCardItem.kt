package com.example.vocabgo.ui.screen.flashcard

import Nunito
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.vocabgo.R
import com.example.vocabgo.common.helpers.AudioHelper
import com.example.vocabgo.data.dto.Word
import com.example.vocabgo.data.dto.WordExample
import com.example.vocabgo.data.dto.WordPos
import com.example.vocabgo.ui.viewmodel.flashcard.FlashCard

@Preview(showBackground = true)
@Composable
fun FlashCardItem (
    card: FlashCard = FlashCard(
        word = Word (
            wordId = "w001",
            word = "apple",
            phonetic = "/ˈæp.əl/",
            meaningVi = "quả táo",
            audio = "https://example.com/audio/apple.mp3",
            wordPos = listOf(
                WordPos(
                    wordPosId = "wp001",
                    wordId = "w001",
                    posTagId = "n", // noun
                    definition = "A round fruit with red or green skin and a whitish inside.",
                    levelId = "A1",
                    wordExample = listOf(
                        WordExample(
                            wordExampleId = "we001",
                            example = "I eat an apple every morning.",
                            exampleVi = "Tôi ăn một quả táo mỗi sáng.",
                            createdAt = "2024-05-01T10:00:00Z",
                            wordPosId = "wp001"
                        ),
                        WordExample(
                            wordExampleId = "we002",
                            example = "The apple fell from the tree.",
                            exampleVi = "Quả táo rơi từ trên cây xuống.",
                            createdAt = "2024-05-01T10:05:00Z",
                            wordPosId = "wp001"
                        )
                    )
                )
            )
        )
    ),
    primaryColor: Color = MyColors.Fox,
    onPrimaryColor: Color = MyColors.Lion,
) {
    val context = LocalContext.current
    Card (
        Modifier
            .fillMaxSize(),
        colors = CardColors(
            Color.White,
            Color.Black,
            Color.Black,
            Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp ),

        ) {
        Box (
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = "https://static.wikia.nocookie.net/the-snack-encyclopedia/images/7/7d/Apple.png/revision/latest?cb=20200706145821",
                    contentDescription = card.word.word,
                    modifier = Modifier
                        .size(160.dp)
                        .padding(16.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = {
                            AudioHelper.playAudioFromUrl(context, card.word.audio)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.volume_high_solid_full),
                            contentDescription = "volumn",
                            tint = primaryColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Text(
                        card.word.word,
                        style = TextStyle(fontFamily = Nunito, fontSize = 24.sp, fontWeight = FontWeight.Bold )
                    )
                }
                card.word.phonetic?.let {
                    Text(
                        it,
                        style = TextStyle(fontFamily = Nunito, fontSize = 20.sp, color = MyColors.Hare )
                    )
                }
                Spacer(Modifier.height(20.dp))
                card.word.meaningVi?.let {
                    Text(
                        it,
                        style = TextStyle(fontFamily = Nunito, fontSize = 16.sp )
                    )
                }
            }
        }
    }
}