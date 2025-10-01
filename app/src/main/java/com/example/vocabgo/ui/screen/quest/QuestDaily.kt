package com.example.vocabgo.ui.screen.quest

import Nunito
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuestDaily () {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Nhiệm vụ hằng ngày",
            style = TextStyle(fontFamily = Nunito, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Eel)
        )
        Row (
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Time",
                tint = MyColors.Lion
            )
            Text(
                "7 TIẾNG",
                style = TextStyle(fontFamily = Nunito, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Lion)
            )
        }
    }
    Box(
        Modifier
            .fillMaxWidth()
            .border(2.dp, color = MyColors.Swan, RoundedCornerShape(16.dp))
            .background(Color.White, )
    ) {
        Column(

        ) {
            QuestDailyItem()
            Box(Modifier.fillMaxWidth().height(2.dp).background(MyColors.Swan))
            QuestDailyItem()
            QuestDailyItem()
        }
    }
}