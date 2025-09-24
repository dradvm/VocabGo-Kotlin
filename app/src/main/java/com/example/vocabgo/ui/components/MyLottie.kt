package com.example.vocabgo.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*

@Composable
fun MyLottie(lottie: String? = "") {
    // Đọc file trong assets
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("animation/lottie/$lottie")
    )

    // Tự động play & lặp lại
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(200.dp)
    )
}
