package com.example.vocabgo.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vocabgo.ui.components.MyLottie
import com.example.vocabgo.ui.components.PrimaryButton
import com.example.vocabgo.ui.components.SecondaryButton


@Composable
fun WelcomeScreen(navController: NavController) {
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column (
            modifier = Modifier.fillMaxSize().systemBarsPadding().padding(16.dp, 2.dp)
        ) {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MyLottie("main.lottie")
                    Text(
                        "vocabgo",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Học miễn phí. Suốt đời.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
            Column() {
                PrimaryButton(content =  "BẮT ĐẦU NGAY")
                Spacer(modifier = Modifier.height(16.dp))
                SecondaryButton(
                    content = "TÔI ĐÃ CÓ TÀI KHOẢN", onClick =  {
                        navController.navigate("login")
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

}


