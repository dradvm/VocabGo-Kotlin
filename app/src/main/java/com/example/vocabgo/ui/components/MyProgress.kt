package com.example.vocabgo.ui.components

import Nunito
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeunstyled.ProgressIndicator
import com.composeunstyled.Text
import kotlinx.coroutines.delay

@Composable
fun MyProgress (
    modifier: Modifier = Modifier.fillMaxWidth(),
    progress : Float = 0.5f,
    height: Dp = 24.dp,
    backgroundColor: Color = MyColors.Swan,
    contentColor: Color = Color.White,
    progressShape: RoundedCornerShape = RoundedCornerShape(100),
    trackShape: RoundedCornerShape = RoundedCornerShape(100),
    isBright: Boolean = true,
    streakLevel: Int = 0,
    progressColor: Color = MaterialTheme.colorScheme.onPrimary,
    progressBrightColor: Color = MaterialTheme.colorScheme.primary
) {
    val targetProgressColor = when (streakLevel) {
        in 0..3 -> progressColor
        in 4..6 -> MyColors.Owl
        in 7..9 -> MyColors.Lion
        else -> MyColors.Cardinal
    }

    val targetProgressBrightColor = when (streakLevel) {
        in 0..3 -> progressBrightColor
        in 4..6 -> MyColors.Turtle
        in 7..9 -> MyColors.Duck
        else -> MyColors.Crab
    }

    val progressColor by animateColorAsState(
        targetValue = targetProgressColor,
        animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing)
    )

    val progressBrightColor by animateColorAsState(
        targetValue = targetProgressBrightColor,
        animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing)
    )


    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween (
            durationMillis = 600,
            easing = FastOutSlowInEasing
        ),
        label = "progressAnimation"
    )
    ProgressIndicator(
        progress = animatedProgress,
        modifier = modifier
            .height(height),
        shape = trackShape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
    ) {

        Box(
            Modifier
                .fillMaxWidth(animatedProgress)
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
                            progressBrightColor,
                            RoundedCornerShape(100)
                        )
                        .align(Alignment.TopCenter)
                )
            }

        }
    }


}

@Preview(showBackground = true)
@Composable
fun MyProgressTest() {
    var progress by remember { mutableStateOf(0.5f) }

    Column {
        MyProgress(progress = progress, streakLevel = 6)
    }
}
