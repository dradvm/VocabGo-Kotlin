package com.example.vocabgo.ui.screen.quest

import Nunito
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabgo.R
import com.example.vocabgo.ui.components.MyProgress


@Composable
fun QuestDailyItem () {
    Column (
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            "Streak mở màn",
            style = TextStyle(fontFamily = Nunito, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = MyColors.Eel)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyProgress(
                modifier = Modifier.weight(1f),
                progress = 1f   ,
                progressShape = RoundedCornerShape(
                    topStartPercent = 100,
                    topEndPercent = 0,
                    bottomStartPercent = 100,
                    bottomEndPercent = 0
                ),
                trackShape = RoundedCornerShape(
                    topStartPercent = 100,
                    topEndPercent = 0,
                    bottomStartPercent = 100,
                    bottomEndPercent = 0
                ),
            )
            Icon(
                painter = painterResource(R.drawable.cube_solid_full),
                contentDescription = "cube",
                modifier = Modifier.size(40.dp),
                tint = MyColors.Macaw
            )
        }
    }
}