package com.example.vocabgo.ui.screen.reward

import Nunito
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vocabgo.R
import com.example.vocabgo.common.helpers.Time
import com.example.vocabgo.ui.components.MyLottie
import com.example.vocabgo.ui.components.PrimaryButton
import com.example.vocabgo.ui.viewmodel.reward.RewardEvent
import com.example.vocabgo.ui.viewmodel.reward.RewardViewModel
import kotlin.math.roundToInt

@Composable
fun RewardScreen(
    userLessonProgressId: String,
    kp: Int,
    timeSpent: Int,
    accuracyRate: Float,
    navController: NavController,
    rewardViewModel: RewardViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        rewardViewModel.eventFlow.collect { event ->

            when (event) {
                RewardEvent.NavigateToStreakClaim -> {
                    navController.navigate("streakClaim") {
                        launchSingleTop = true
                    }
                }

                RewardEvent.NavigateToMain -> {
                    navController.navigate("main") {
                        launchSingleTop = true
                    }
                }
            }
        }
    }


    Surface (
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column (
                modifier = Modifier.padding(top = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(36.dp)
            ) {
                MyLottie("trophy.lottie", size = 300.dp, loopForever = false)
                Text(
                    "Hoàn thành bài học!",
                    color = MyColors.Bee,
                    style = TextStyle(
                        fontFamily = Nunito,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Row (
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(top = 4.dp, start = 2.dp, end = 2.dp, bottom = 2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "TỔNG ĐIỂM KP",
                            color = Color.White,
                            style = TextStyle(
                                fontFamily = Nunito,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold
                            ),
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .background(Color.White, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.brain_solid_full),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    kp.toString(),
                                    color = MaterialTheme.colorScheme.primary,
                                    style = TextStyle(
                                        fontFamily = Nunito,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .background(MyColors.Fox, RoundedCornerShape(12.dp))
                            .padding(top = 4.dp, start = 2.dp, end = 2.dp, bottom = 2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "TỐC ĐỘ",
                            color = Color.White,
                            style = TextStyle(
                                fontFamily = Nunito,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold
                            ),
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .background(Color.White, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.stopwatch_solid_full),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MyColors.Fox
                                )
                                Text(
                                    Time.format(timeSpent),
                                    color = MyColors.Fox,
                                    style = TextStyle(
                                        fontFamily = Nunito,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .background(MyColors.Owl, RoundedCornerShape(12.dp))
                            .padding(top = 4.dp, start = 2.dp, end = 2.dp, bottom = 2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "CHÍNH XÁC",
                            color = Color.White,
                            style = TextStyle(
                                fontFamily = Nunito,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold
                            ),
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .background(Color.White, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.bullseye_solid_full),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MyColors.Owl
                                )
                                Text(
                                    "${accuracyRate.roundToInt()}%",
                                    color = MyColors.Owl,
                                    style = TextStyle(
                                        fontFamily = Nunito,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                )
                            }
                        }
                    }
                }
            }
            PrimaryButton(content = "NHẬN KN", onClick = {
                rewardViewModel.doneLesson(userLessonProgressId,kp, timeSpent, accuracyRate)
            }) {

            }
        }
    }
}