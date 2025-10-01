package com.example.vocabgo.ui.components

import Nunito
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import kotlin.invoke

@Composable
fun MyButton (
    modifier: Modifier = Modifier.fillMaxWidth(),
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    shadowColor: Color = MaterialTheme.colorScheme.onPrimary,
    shadowBottomOffset: Float = 4f,
    buttonHeight: Float = 40f,
    shape: RoundedCornerShape = RoundedCornerShape(14.dp),
    border: BorderStroke? = null,
    onClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    clickable: Boolean = true,
    content: (@Composable () -> Unit)? = null
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val isPressed by if (clickable) {
        interactionSource.collectIsPressedAsState()
    } else {
        remember { mutableStateOf(false) }
    }
    Box (
        modifier = modifier
            .height(buttonHeight.dp + shadowBottomOffset.dp)
    ) {
        Surface(
            modifier = modifier
                .align(Alignment.BottomCenter)
                .height(buttonHeight.dp + if (!isPressed) {
                    shadowBottomOffset.dp
                }
                else  {
                    (shadowBottomOffset * 0.5).dp
                }),
            color = shadowColor,
            shape = shape
        ) {
        }
        Button(
            onClick = {
                if (clickable) {
                    onClick?.let {
                        it()
                    }
                }

            },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            shape = shape,
            modifier = modifier
                .align(Alignment.TopCenter)
                .height(buttonHeight.dp + if (!isPressed) {
                    0.dp
                } else {
                    shadowBottomOffset.dp
                })
                .padding(
                    top = if (!isPressed) {
                        0.dp
                    } else {
                        (shadowBottomOffset * 0.5).dp
                    }
                ),
            interactionSource = interactionSource,
            border = border,
            contentPadding = contentPadding,
            ) {
            content?.invoke()
        }
    }
}

@Composable
fun PrimaryButton (content: String? = "", onClick: (() -> Unit)? = {}, icon: (@Composable () -> Unit)? = null) {
    MyButton(
        buttonColor = MaterialTheme.colorScheme.primary,
        shadowColor = MaterialTheme.colorScheme.onPrimary,
        shadowBottomOffset = 4f,
        buttonHeight = 46f,
        onClick = onClick,
    ) {
        FlowRow (
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            itemVerticalAlignment = Alignment.CenterVertically
        ) {
            icon?.invoke()
            if (content != null) {
                Text(
                    content,
                    style = TextStyle(fontFamily = Nunito, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun SecondaryButton (content: String? = "", onClick: (() -> Unit)? = {}, icon: (@Composable () -> Unit)? = null) {
    MyButton(
        buttonColor = Color.White,
        shadowColor = MyColors.Swan,
        shadowBottomOffset = 2f,
        buttonHeight = 48f,
        border = BorderStroke(1.dp, MyColors.Swan),
        onClick = onClick,
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