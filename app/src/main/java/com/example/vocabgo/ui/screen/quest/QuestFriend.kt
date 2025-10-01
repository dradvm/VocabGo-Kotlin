package com.example.vocabgo.ui.screen.quest

import Nunito
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun QuestFriend() {
    Text(
        "Nhiệm vụ bạn bè",
        style = TextStyle(fontFamily = Nunito, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Eel)
    )
    Box(
        Modifier
            .fillMaxWidth()
            .border(2.dp, color = MyColors.Swan, RoundedCornerShape(16.dp))
            .background(Color.White, )
            .padding(32.dp, 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Hoàn thành một bài học để có cơ hội nhận nhiệm vụ bạn bè tuần này!",
            style = TextStyle(fontFamily = Nunito, fontSize = 16.sp, color = MyColors.Wolf),
            textAlign = TextAlign.Center
        )
    }
}