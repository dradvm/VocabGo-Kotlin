package com.example.vocabgo.ui.screen.flashcard

import Nunito
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabgo.R
import com.example.vocabgo.ui.viewmodel.flashcard.FlashCard


@Composable
fun FlashCardItem (
    card: FlashCard
) {
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.volume_high_solid_full),
                        contentDescription = "volumn",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        card.word,
                        style = TextStyle(fontFamily = Nunito, fontSize = 24.sp, fontWeight = FontWeight.Bold )
                    )
                }
                Text(
                    card.phonetic,
                    style = TextStyle(fontFamily = Nunito, fontSize = 20.sp, color = MyColors.Hare )
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    card.meaning,
                    style = TextStyle(fontFamily = Nunito, fontSize = 16.sp )
                )
            }
        }
    }
}