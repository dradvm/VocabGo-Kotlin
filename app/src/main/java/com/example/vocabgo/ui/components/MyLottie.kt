package com.example.vocabgo.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*

@Composable
fun MyLottie(
    lottie: String? = "",
    size: Dp = 200.dp,
    loopForever: Boolean = true
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("animation/lottie/$lottie")
    )

    val iterations = if (loopForever) LottieConstants.IterateForever else 1

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = iterations
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(size)
    )
}

