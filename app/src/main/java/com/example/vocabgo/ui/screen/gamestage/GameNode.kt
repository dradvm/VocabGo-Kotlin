package com.example.vocabgo.ui.screen.gamestage

import Nunito
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.example.vocabgo.R
import com.example.vocabgo.data.dto.Lesson
import com.example.vocabgo.data.dto.Stage
import com.example.vocabgo.ui.components.MyButton
import com.example.vocabgo.ui.components.MyButtonWithOutClick
import com.example.vocabgo.ui.components.SecondaryButton
import com.example.vocabgo.ui.viewmodel.gamestage.GameStageViewModel
import kotlinx.coroutines.launch

//@Preview(showBackground = true)
@Composable
fun GameNode(
    navController: NavController,
    gameStageViewModel: GameStageViewModel,
    stage: Stage,
    lesson: Lesson,
    offset: Float,
) {
    var showPopup by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val currentStagesProgress by gameStageViewModel.currentStagesProgress.collectAsState()
    val colorsPair = MyColorsPair.asList()
    val buttonColor = colorsPair[(stage.stageOrder - 1) % colorsPair.size].first
    val shadowColor = colorsPair[(stage.stageOrder - 1) % colorsPair.size].second
    val userLessonProgress = currentStagesProgress.get(stage.stageId)?.userLessonProgress?.find { it.lessonId == lesson.lessonId }
    val isDisabled: Boolean = userLessonProgress == null
    Box(
        contentAlignment = Alignment.Center
    ) {
        MyButtonWithOutClick (
            buttonHeight = 56f,
            modifier = Modifier.width(70.dp),
            shape = RoundedCornerShape(12),
            contentPadding = PaddingValues(0.dp),
            buttonColor = if (isDisabled) MyColors.Swan else buttonColor,
            shadowColor = if (isDisabled) MyColors.Hare else shadowColor,
            onClick = {
                showPopup = true


            }
        ) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (lesson.lessonType.lessonTypeName) {
                    "Flashcard" -> {
                        Icon(
                            painter = painterResource(R.drawable.flash_cards),
                            contentDescription = "A",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    "Game" -> {
                        Icon(
                            painter = painterResource(R.drawable.gamepad_solid_full),
                            contentDescription = "A",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    "Reward" -> {
                        Icon(
                            painter = painterResource(R.drawable.cube_solid_full),
                            contentDescription = "A",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

            }

        }
        Popup(
            alignment = Alignment.TopCenter,
            offset = IntOffset(0, 150),
            onDismissRequest = { showPopup = false }
        ) {
            AnimatedVisibility(
                visible = showPopup,
                enter = scaleIn(
                    transformOrigin = TransformOrigin(0.5f + offset, 0f),
                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
                ) + fadeIn(),
                exit = scaleOut(
                    transformOrigin = TransformOrigin(0.5f + offset, 0f),
                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
                ) + fadeOut()
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 16.dp)
                            .background(if (isDisabled) MyColors.Polar else buttonColor, shape = RoundedCornerShape(12.dp))
                            .border(if (isDisabled) 2.dp else 0.dp,  MyColors.Swan, shape = RoundedCornerShape(12.dp))
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                lesson.lessonName,
                                color = if(isDisabled) MyColors.Hare else Color.White,
                                style = TextStyle(
                                    fontFamily = Nunito,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                            if (isDisabled) {
                                MyButtonWithOutClick (
                                    buttonColor = MyColors.Swan,
                                    shadowBottomOffset = 0f,
                                    buttonHeight = 50f,
                                ) {
                                    Text(
                                        "ĐANG KHOÁ",
                                        color = MyColors.Hare,
                                        style = TextStyle(
                                            fontFamily = Nunito,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    )
                                }
                            }
                            else {
                                SecondaryButton(content = "${if (!userLessonProgress.completedAt.isNullOrEmpty()) "ÔN TẬP" else "BẮT ĐẦU"} +${lesson.lessonReward} KP", onClick = {
                                    showPopup = false
                                    scope.launch {
                                        val shouldNavigate: Boolean = gameStageViewModel.startLesson(userLessonProgress, lesson, stage)
                                        when(lesson.lessonType.lessonTypeName) {
                                            "Flashcard" -> {
                                                if(shouldNavigate) {
                                                    navController.navigate("flashcard/${buttonColor.toArgb()}/${shadowColor.toArgb()}") {
                                                        launchSingleTop = true
                                                    }
                                                }

                                            }
                                            "Game" -> {
                                                if(shouldNavigate) {
                                                    navController.navigate("game") {
                                                        launchSingleTop = true
                                                    }
                                                }

                                            }
                                            else -> {

                                            }
                                        }
                                    }


                                }, contentColor = buttonColor) { }
                            }

                        }
                    }
                }
            }
        }
    }
}

