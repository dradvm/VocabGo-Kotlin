package com.example.vocabgo.ui.screen.gamestage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vocabgo.ui.components.MyButton


@Composable
fun GameNode(
) {

    MyButton (
        buttonHeight = 56f,
        modifier = Modifier.width(70.dp),
        clickable = false,
        shape = RoundedCornerShape(12),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "A",
                tint = Color.White,
                modifier = Modifier.size(100.dp)
            )
        }

    }
}
