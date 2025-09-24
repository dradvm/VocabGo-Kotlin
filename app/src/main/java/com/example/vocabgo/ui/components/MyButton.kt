package com.example.vocabgo.ui.components

import Nunito
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabgo.R

@Composable
fun MyButton (
    modifier: Modifier = Modifier.fillMaxWidth(),
    buttonColor: Color,
    buttonContentColor: Color,
    shadowColor: Color,
    shadowBottomOffset: Float,
    buttonHeight: Float = 0f,
    shape: RoundedCornerShape = RoundedCornerShape(14.dp),
    border: BorderStroke? = null,
    onClick: (() -> Unit)? = null,
    content: String? = "",
    icon: (@Composable () -> Unit)? = null
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isPressed = interactionSource.collectIsPressedAsState()

    Box (
        modifier = modifier
            .height(buttonHeight.dp + shadowBottomOffset.dp)
    ) {
        Surface(
            modifier = modifier
                .align(Alignment.BottomCenter)
                .height(buttonHeight.dp + if (!isPressed.value) {
                    shadowBottomOffset.dp
                }
                else {
                    (shadowBottomOffset * 0.5).dp
                }),
            color = shadowColor,
            shape = shape
        ) {
        }
        Button(
            onClick = {
                onClick?.let {
                    it()
                }
            },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = buttonContentColor),
            shape = shape,
            modifier = modifier
                .align(Alignment.TopCenter)
                .height(buttonHeight.dp + if (!isPressed.value) {
                    0.dp
                } else {
                    shadowBottomOffset.dp
                })
                .padding(
                    top = if (!isPressed.value) {
                        0.dp
                    } else {
                        (shadowBottomOffset * 0.5).dp
                    }
                ),
            interactionSource = interactionSource,
            border = border,

            ) {
            FlowRow (
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                itemVerticalAlignment = Alignment.CenterVertically
            ) {
                icon?.invoke()
                if (content != null) {
                    Text(
                        content,
                        style = TextStyle(fontFamily = Nunito, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
                    )
                }
            }
        }
    }
}

@Composable
fun PrimaryButton (content: String? = "", onClick: (() -> Unit)? = {}, icon: (@Composable () -> Unit)? = null) {
    MyButton(
        buttonColor = MaterialTheme.colorScheme.primary,
        buttonContentColor = Color.White,
        shadowColor = MaterialTheme.colorScheme.onPrimary,
        shadowBottomOffset = 6f,
        buttonHeight = 46f,
        onClick = onClick,
        content = content
    ) {
        icon?.invoke()
    }
}

@Composable
fun SecondaryButton (content: String? = "", onClick: (() -> Unit)? = {}, icon: (@Composable () -> Unit)? = null) {
    MyButton(
        buttonColor = Color.White,
        buttonContentColor = MaterialTheme.colorScheme.primary,
        shadowColor = Color(0xFFE0E0E0),
        shadowBottomOffset = 4f,
        buttonHeight = 48f,
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        onClick = onClick,
        content = content
    ) {
        icon?.invoke()
    }
}