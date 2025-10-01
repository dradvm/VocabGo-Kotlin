package com.example.vocabgo.ui.screen.game

import Nunito
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabgo.ui.components.MyButton

@Preview()
@Composable
fun GameButton (
    content: String = "",
    isSelected: Boolean = false,
    isRight: Boolean = false,
    isWrong: Boolean = false,
    isDisabled: Boolean = false,
    onClick: (() -> Unit)? = null,
    buttonHeight: Float = 70f,
    modifier: Modifier = Modifier.fillMaxWidth(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding
) {


    val buttonColor = when {
        isRight -> MyColors.SeaSponge
        isWrong -> MyColors.WalkingFish
        !isSelected -> Color.White
        else -> MyColors.Iguana
    }

    val shadowColor = when {
        isRight -> MyColors.Owl
        isWrong -> MyColors.Cardinal
        !isSelected -> MyColors.Swan
        else -> MyColors.BlueJay
    }

    val contentColor = when {
        isRight -> MyColors.TreeFrog
        isWrong -> MyColors.FireAnt
        !isSelected -> MyColors.Eel
        else -> MyColors.Macaw
    }

    MyButton (
        onClick = {
            if (!isDisabled) {
                onClick?.let { it() }
            }
        },
        buttonColor = buttonColor,
        shadowColor = shadowColor,
        buttonHeight = buttonHeight,
        border = BorderStroke(2.dp, shadowColor),
        shadowBottomOffset = if (!isDisabled) {2f} else {0f},
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        Text(
            content,
            style = TextStyle(fontFamily = Nunito, fontSize = 18.sp, color = contentColor , fontWeight = FontWeight.SemiBold )
        )
    }
}
