package com.example.vocabgo.ui.screen.streak

import Nunito
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vocabgo.R
import com.example.vocabgo.ui.components.MyLottie
import com.example.vocabgo.ui.components.PrimaryButton
import com.example.vocabgo.ui.viewmodel.streak.StreakViewModel
import java.time.LocalDate

@Composable
fun StreakClaimScreen(
    navController: NavController,
    streakViewModel: StreakViewModel
) {

    LaunchedEffect(Unit) {
        streakViewModel.fetchStreakCount()
    }

    val streakCount = streakViewModel.streakCount
    val weekStreak = streakViewModel.weekStreak
    Log.d("StreakClaimScreen", "streakCount: $streakCount, weekStreak: $weekStreak")
    val weekDays: List<String> = listOf("CN", "T2", "T3", "T4", "T5", "T6", "T7")
    val today = LocalDate.now().dayOfWeek.value % 7 // CN = 0, T2 =1,...T7=6
    val currentIndex = (today + 1) % 7 // Mapping sang list của bạn: T6 index=0

    val rotatedWeekDays = weekDays.drop(currentIndex) + weekDays.take(currentIndex)

    Surface(
        modifier = Modifier.fillMaxSize().systemBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(Modifier.height(64.dp))

                Text(
                    text = "Chuỗi streak mới! Hãy luyện tập\nmỗi ngày để nối dài streak nhé.",
                    modifier = Modifier.border(2.dp, MyColors.Swan, RoundedCornerShape(12.dp)).padding(14.dp),
                    style = TextStyle(
                        fontFamily = Nunito, fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold, color = MyColors.Eel
                    ),
                )

                MyLottie(lottie = "streak_fire.lottie", size = 250.dp)

                Text(
                    text = "$streakCount",
                    style = TextStyle(
                        fontFamily = Nunito, fontSize = 80.sp,
                        fontWeight = FontWeight.ExtraBold, color = MyColors.Fox
                    ),
                )

                Text(
                    text = "ngày streak",
                    style = TextStyle(
                        fontFamily = Nunito, fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold, color = MyColors.Fox
                    ),
                )

                Spacer(Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rotatedWeekDays.forEachIndexed { index, day ->
                        val streakDay = weekStreak.find { it.day == day }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = day,
                                style = TextStyle(
                                    fontFamily = Nunito,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MyColors.Hare
                                ),
                            )
                            Spacer(Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (streakDay != null) if (streakDay.isFrozen) MyColors.Macaw else MyColors.Fox  else MyColors.Swan
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (streakDay != null) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            PrimaryButton(content = "QUYẾT TÂM", onClick = {
                navController.navigate("main") {
                    launchSingleTop = true
                }
            }) {

            }
        }
    }
}
