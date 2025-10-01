package com.example.vocabgo.ui.screen.game

import Nunito
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabgo.R
import com.example.vocabgo.ui.components.MyProgress
import com.example.vocabgo.ui.components.PrimaryButton
import com.example.vocabgo.ui.screen.game.FillInBlankScreen.FillInBlankScreen
import com.example.vocabgo.ui.screen.game.matching.MatchingGameScreen
import com.example.vocabgo.ui.screen.game.multiplechoice.MultipleChoiceScreen

@Preview()
@Composable
fun GameScreen() {
    val isFocusTextField by rememberKeyboardOpenState()
    Surface (
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
            .imePadding(),
        color = Color.White
    ) {
        Column (
            Modifier.padding(16.dp).fillMaxSize()
        ) {
            Crossfade (targetState = isFocusTextField) { isFocus ->
                if (!isFocus) {
                    ProgressGameBar()
                } else {

                }
            }


            Box(
                Modifier.weight(1f).background(Color.White)
            ) {
//                MatchingGameScreen()
//                MultipleChoiceScreen()
                FillInBlankScreen()
            }
            Box(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                PrimaryButton ("KIỂM TRA") {

                }
            }
        }

    }
}

@Composable
fun rememberKeyboardOpenState(): State<Boolean> {
    val ime = WindowInsets.ime
    val density = LocalDensity.current
    val isKeyboardOpen = remember { mutableStateOf(false) }

    LaunchedEffect(ime) {
        snapshotFlow { ime.getBottom(density) }
            .collect { bottom ->
                isKeyboardOpen.value = bottom > 0
            }
    }
    return isKeyboardOpen
}

@Composable
fun ProgressGameBar() {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier,
            onClick = {
//                        navController.popBackStack()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "close",
                tint = Color.LightGray,
                modifier = Modifier.size(30.dp)
            )
        }
        MyProgress(
            height = 16.dp,
            modifier = Modifier.weight(1f)
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            itemVerticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.heart_solid_full),
                contentDescription = "heart",
                tint = MyColors.Cardinal,
                modifier = Modifier.size(26.dp)
            )
            Text(
                "0",
                color = MyColors.Cardinal,
                style = TextStyle(fontFamily = Nunito, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
            )
        }
    }
}