package com.example.vocabgo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeunstyled.ProgressIndicator

@Composable
fun MyProgress (
    modifier: Modifier = Modifier.fillMaxWidth(),
    progress : Float = 0.5f,
    height: Dp = 24.dp,
    backgroundColor: Color = MyColors.Swan,
    contentColor: Color = Color.White,
    progressColor: Color = MaterialTheme.colorScheme.onPrimary,
    progressShape: RoundedCornerShape = RoundedCornerShape(100),
    trackShape: RoundedCornerShape = RoundedCornerShape(100),
    isBright: Boolean = true
) {
    ProgressIndicator(
        progress = progress,
        modifier = modifier
            .height(height),
        shape = trackShape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
    ) {
        Box(
            Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .background(
                    progressColor,
                    progressShape
                )
        ) {
            if (isBright) {
                Box(
                    Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.5f)
                        .padding(top = 5.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(100)
                        )
                        .align(Alignment.TopCenter)
                )
            }
        }
    }
}