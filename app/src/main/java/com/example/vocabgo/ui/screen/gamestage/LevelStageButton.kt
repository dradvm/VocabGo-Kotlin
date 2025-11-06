package com.example.vocabgo.ui.screen.gamestage

import Nunito
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vocabgo.data.dto.Stage
import com.example.vocabgo.ui.components.MyButton
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ClipboardList


@Composable
fun LevelStageButton(navController: NavController, stage: Stage?) {
    val colorsPair = MyColorsPair.asList()
    val buttonColor = colorsPair[(stage?.stageOrder?.minus(1))?.rem(colorsPair.size) ?: 0].first
    val shadowButton =  colorsPair[(stage?.stageOrder?.minus(1))?.rem(colorsPair.size) ?: 0].second
    MyButton(
        buttonColor = buttonColor,
        shadowColor = shadowButton,
        buttonHeight = 68f,
        contentPadding = PaddingValues(0.dp),
        onClick = {
            navController.navigate("level") {
                launchSingleTop = true
            }
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(16.dp, 12.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    "PHẦN ${stage?.gameLevel?.levelOrder} CỬA ${stage?.stageOrder}",
                    color = MyColors.Swan,
                    style = TextStyle(fontFamily = Nunito, fontSize = 13 .sp, fontWeight = FontWeight.ExtraBold )
                )
                stage?.stageName?.let {
                    Text(
                        it,
                        color = Color.White,
                        style = TextStyle(fontFamily = Nunito, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold )
                    )
                }

            }
            Box(
                Modifier
                    .width(2.dp)
                    .fillMaxHeight()
                    .background(shadowButton))
            Box(
                Modifier
                    .fillMaxHeight()
                    .width(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.ClipboardList,
                    contentDescription = "String",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}