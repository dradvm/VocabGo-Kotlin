package com.example.vocabgo.ui.screen.gamestage

import Nunito
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.vocabgo.data.dto.Stage
import com.example.vocabgo.ui.components.MyButton
import com.example.vocabgo.ui.components.MyProgress
import com.example.vocabgo.ui.components.PrimaryButton
import com.example.vocabgo.ui.components.SecondaryButton
import com.example.vocabgo.ui.viewmodel.gamestage.GameStageViewModel

@Composable
fun GameStageScreen(
    navController: NavController,
    gameStageViewModel: GameStageViewModel
) {
    val stages = gameStageViewModel.stages.collectAsState()
    val userWallet = gameStageViewModel.userWallet.collectAsState()
    val userStreak = gameStageViewModel.userStreak.collectAsState()

    LaunchedEffect(Unit) {
        gameStageViewModel.fetchData()
    }

    Surface (
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            var isMenuExpanded by remember { mutableStateOf(false) }

            GameStatusBar(
                navController,
                handleClickEnergy = {
                    isMenuExpanded = !isMenuExpanded
                },
                userWallet = userWallet.value,
                userStreak = userStreak.value
            )
            Box(
                modifier = Modifier.fillMaxSize().clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    isMenuExpanded = false
                },

                contentAlignment = Alignment.TopCenter
            ) {
                val listState = rememberLazyListState()
                var currentStage by remember { mutableStateOf<Stage?>(null) }

                Box(
                    modifier = Modifier.fillMaxSize()
                        .padding(16.dp, 0.dp),
                ) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(28.dp)
                    ) {
                        val variance = 40
                        item { Spacer(modifier = Modifier.height(60.dp)) }

                        stages.value.forEach { stage ->
                            // 👇 Tiêu đề stage
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(2.dp)
                                            .weight(1f)
                                            .background(MyColors.Swan)
                                    )

                                    Text(
                                        text = stage.stageName,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MyColors.Hare,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .height(2.dp)
                                            .weight(1f)
                                            .background(MyColors.Swan)
                                    )
                                }
                            }

                            val lessons = stage.lessons

                            itemsIndexed(lessons) { index, lesson ->
                                val isLastLesson = index == lessons.lastIndex
                                val isLeft = ((index / 4) % 2 == 0)

                                val offsetX = if (isLastLesson) {
                                    0 // 👈 luôn ở giữa cho bài cuối
                                } else {
                                    when (index % 4) {
                                        0 -> 0
                                        1 -> variance * if (isLeft) -1 else 1
                                        2 -> 2 * variance * if (isLeft) -1 else 1
                                        3 -> variance * if (isLeft) -1 else 1
                                        else -> 0
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(x = offsetX.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    GameNode(
                                        navController,
                                        gameStageViewModel,
                                        stage,
                                        lesson,
                                        offset = offsetX / variance / 10f
                                    )
                                }
                            }
                        }
                    }
                    LevelStageButton(navController, currentStage)
                }



                androidx.compose.animation.AnimatedVisibility(
                    visible = isMenuExpanded,
                    enter = expandVertically(),
                    exit = shrinkVertically(),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .zIndex(2f)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth().clickable(enabled = false) {}
                    ) {

                        Column(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .background(Color.White)
                                .fillMaxWidth()
                                ,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth().background(Color.LightGray).height(1.dp)
                            )
                            Column(
                                modifier = Modifier.padding(16.dp, 20.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Column (
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            "Năng lượng",
                                            style = TextStyle(fontFamily = Nunito, fontSize = 22.sp, fontWeight =FontWeight.ExtraBold, color = MyColors.Eel)
                                        )
                                        Text(
                                            "${userWallet.value?.energy?.current} / ${userWallet.value?.energy?.max}",
                                            style = TextStyle(fontFamily = Nunito, fontSize = 20.sp, fontWeight =FontWeight.Bold, color = MyColors.Bee)
                                        )
                                    }

                                    MyProgress(
                                        progress = (userWallet.value?.energy?.current?.toFloat() ?: 0f) /
                                                (userWallet.value?.energy?.max?.toFloat() ?: 1f),
                                        height = 20.dp,
                                        progressColor = MyColors.Bee,
                                        progressBrightColor = MyColors.Duck
                                    )
                                }
                                
                                SecondaryButton (content = "Sạc đầy năng lượng") {

                                }
                            }


                            Box(
                                modifier = Modifier.fillMaxWidth().background(Color.LightGray).height(1.dp)
                            )
                        }
                    }
                }
                LaunchedEffect(listState.firstVisibleItemIndex, stages.value) {
                    val lessons = stages.value.flatMap { stage ->
                        stage.lessons.map { lesson -> stage to lesson }
                    }

                    if (lessons.isNotEmpty()) {
                        val firstIndex = listState.firstVisibleItemIndex.coerceIn(0, lessons.lastIndex)
                        val (stage, lesson) = lessons[firstIndex]

                        currentStage = stage
                    }
                }






            }
        }
    }
}
