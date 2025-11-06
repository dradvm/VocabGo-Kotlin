package com.example.vocabgo.ui.screen.game

import Nunito
import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vocabgo.R
import com.example.vocabgo.ui.components.MyButton
import com.example.vocabgo.ui.components.MyButtonWithOutClick
import com.example.vocabgo.ui.components.MyProgress
import com.example.vocabgo.ui.components.PrimaryButton
import com.example.vocabgo.ui.screen.game.fill_in_blank.FillInBlankScreen
import com.example.vocabgo.ui.screen.game.matching.MatchingGameScreen
import com.example.vocabgo.ui.screen.game.multiple_choice.MultipleChoiceScreen
import com.example.vocabgo.ui.screen.game.speech.SpeechScreen
import com.example.vocabgo.ui.viewmodel.TimerViewModel
import com.example.vocabgo.ui.viewmodel.game.GameViewModel
import com.example.vocabgo.ui.viewmodel.gamestage.GameStageViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@Composable
fun GameScreen(
    gameStageViewModel: GameStageViewModel,
    navController: NavController,
    gameViewModel: GameViewModel = viewModel(),
    timerViewModel: TimerViewModel = viewModel()
) {
    val isFocusTextField by rememberKeyboardOpenState()
    val stageWords by gameStageViewModel.stageWords.collectAsState()
    val currentLesson by gameStageViewModel.currentLesson.collectAsState()
    val questions by gameViewModel.questions.collectAsState()
    val lesson by gameStageViewModel.currentLesson.collectAsState()
    val seconds by timerViewModel.seconds.collectAsState()
    val lessonProgress by gameStageViewModel.currentLessonProgress.collectAsState()
    val totalQuestion by gameViewModel.totalQuestion.collectAsState()
    val correctProgressIncrement by gameViewModel.correctProgressIncrement.collectAsState()
    val wrongProgressIncrement by gameViewModel.wrongProgressIncrement.collectAsState()
    var progress = remember {
        mutableFloatStateOf(0f)
    }
    val streak = remember { mutableIntStateOf(0) }
    fun getProgress(isRight: Boolean) {
        progress.floatValue = progress.floatValue + if (isRight) correctProgressIncrement else wrongProgressIncrement

    }
    fun mapProgress(percent: Float): Float {
        val inMin = 0f
        val inMax = 100f
        val outMin = 0.2f
        val outMax = 1f
        return outMin + (percent * 100f - inMin) * (outMax - outMin) / (inMax - inMin)
    }
    LaunchedEffect(stageWords, currentLesson) {
        if (stageWords.isNotEmpty() && currentLesson != null) {
            gameViewModel.initGameQuestion(currentLesson?.lessonQuestion, stageWords)
        }

    }

    Surface (
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
            .imePadding(),
        color = Color.White
    ) {
        Column (
            Modifier.padding(16.dp).fillMaxSize()
        ) {
            Crossfade (targetState = isFocusTextField) { isFocus ->
                if (!isFocus) {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            modifier = Modifier,
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "close",
                                tint = Color.LightGray,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        MyProgress(
                            height = 16.dp,
                            modifier = Modifier.weight(1f),
                            progress = mapProgress(progress.floatValue),
                            streakLevel = streak.intValue
                        )
                    }
                } else {

                }
            }

            if (!questions.isNullOrEmpty()) {

                val pagerState = rememberPagerState(pageCount = { questions?.size ?: 0 })
                val scope = rememberCoroutineScope()

                val onReadyToCheck = remember { mutableStateListOf<Boolean>() }
                val onCheckActionList = remember { mutableStateListOf<(() -> Boolean)?>() }

                LaunchedEffect(questions) {
                    val newSize = questions?.size ?: 0

                    while (onReadyToCheck.size < newSize) onReadyToCheck.add(false)
                    while (onReadyToCheck.size > newSize) onReadyToCheck.removeAt(onReadyToCheck.lastIndex)

                    while (onCheckActionList.size < newSize) onCheckActionList.add(null)
                    while (onCheckActionList.size > newSize) onCheckActionList.removeAt(onReadyToCheck.lastIndex)

//                    if (questions!!.isNotEmpty()) {
//                        val lastIndex = questions!!.lastIndex
//                        val currentPage = pagerState.currentPage
//
//                        if (currentPage == lastIndex - 1) {
//                            scope.launch {
//                                pagerState.animateScrollToPage(lastIndex)
//                            }
//                        }
//                    }
                }


                val currentPage = pagerState.currentPage
                var isAllowNextPage by remember { mutableStateOf(false) }
                var isRight by remember { mutableStateOf(false) }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f).background(Color.White),
                    key = { index ->
                        val question = questions!![index]
                        "${index}-${question.question.questionId}-${question.repeat}"
                    },
                    userScrollEnabled = false
                ) { page ->
                    val question = questions!![page]

                    val onChecked: (checked: Boolean, right: Boolean) -> Unit = { checked, right ->
                        onReadyToCheck[page] = checked
                        isRight = right
                        isAllowNextPage = checked
                    }
                    Column(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (question.repeat > 0) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.repeat_solid_full),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.background(MyColors.Fox, CircleShape).padding(2.dp).size(16.dp)
                                )
                                Text(
                                    "LỖI SAI CŨ",
                                    style = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.ExtraBold, color = MyColors.Fox, fontSize = 15.sp)
                                )
                            }
                        }
                        when (question.question.questionName) {
                            "Matching" -> MatchingGameScreen(
                                question,
                                onReadyToCheck = { onReadyToCheck[page] = true },
                                onChecked = onChecked,
                                onCheckRegister = { onCheckActionList[page] = it }
                            )
                            "Multiple Choice" -> MultipleChoiceScreen(
                                question,
                                onReadyToCheck = { onReadyToCheck[page] = true },
                                onChecked = onChecked,
                                onCheckRegister = { onCheckActionList[page] = it }
                            )
                            "Speech" -> SpeechScreen(
                                question,
                                onReadyToCheck = { onReadyToCheck[page] = true },
                                onChecked = onChecked,
                                onCheckRegister = { onCheckActionList[page] = it }
                            )
                            "Fill in the blank" -> FillInBlankScreen(
                                question,
                                onReadyToCheck = { onReadyToCheck[page] = true },
                                onChecked = onChecked,
                                onCheckRegister = { onCheckActionList[page] = it }
                            )
                        }
                    }

                }

                Box(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    var isCheckEnabled = onReadyToCheck.getOrNull(currentPage) == true

                    if (!isAllowNextPage) {
                        if (!isCheckEnabled) {
                            MyButtonWithOutClick(
                                shadowBottomOffset = 4f,
                                buttonHeight = 46f,
                                buttonColor = MyColors.Swan,
                                shadowColor = MyColors.Hare,
                            ) {
                                Text(
                                    "KIỂM TRA",
                                    style = TextStyle(
                                        fontFamily = Nunito,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    ),
                                    color = MyColors.Hare
                                )
                            }

                        } else {
                            PrimaryButton("KIỂM TRA", onClick = {
                                val checkAction = onCheckActionList[currentPage]
                                val result = checkAction?.invoke() ?: false
                                isAllowNextPage = true
                                isRight = result
                            }) {}
                        }
                    } else {
                        MyButton(
                            shadowBottomOffset = 4f,
                            buttonHeight = 46f,
                            buttonColor = if (isRight) MyColors.Owl else MyColors.Cardinal,
                            shadowColor = if (isRight) MyColors.TreeFrog else MyColors.FireAnt,
                            onClick = {
                                scope.launch {
                                    if (pagerState.currentPage <= questions!!.lastIndex) {
                                        isAllowNextPage = false
                                        isCheckEnabled = false
                                        val question = questions!!.get(currentPage)
                                        question.isPass = true
                                        if (isRight) {
                                            streak.intValue++
                                        }
                                        else {
                                            streak.intValue = 0
                                        }
                                        getProgress(isRight)
                                        if (pagerState.currentPage < questions!!.lastIndex ) {
                                            if (!isRight) {
                                                gameViewModel.handleErrorQuestion(question)
                                                delay(50)
                                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                                delay(500)
                                                question.repeat++
                                            }
                                            else {
                                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                            }
                                        }
                                        else if (pagerState.currentPage == questions!!.lastIndex) {
                                            if (isRight) {
                                                navController.navigate(
                                                    "reward?userLessonProgressId=${lessonProgress?.userLessonProgressId}&kp=${lesson?.lessonReward?.toInt() ?: 0}&timeSpent=${seconds.toInt()}&accuracyRate=${gameViewModel.accuracyRate.toFloat()}"
                                                ) {
                                                    launchSingleTop = true
                                                    popUpTo("main") { inclusive = false }
                                                }
                                            }
                                            else {
                                                gameViewModel.handleErrorQuestion(question)
                                                delay(50)
                                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                                delay(500)
                                                question.repeat++

                                            }

                                        }
                                    }
                                }
                            }
                        ) {
                            Text(
                                "TIẾP THEO",
                                style = TextStyle(
                                    fontFamily = Nunito,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.ExtraBold
                                ),
                                color = Color.White
                            )
                        }
                    }
                }
            }


        }

    }
}




@Composable
fun rememberKeyboardOpenState(): State<Boolean> {
    val ime = WindowInsets.ime
    val density = LocalDensity.current
    val isKeyboardOpen = remember { mutableStateOf(false) }

    LaunchedEffect(ime) {
        snapshotFlow { ime.getBottom(density) }
            .collect { bottom ->
                isKeyboardOpen.value = bottom > 0
            }
    }
    return isKeyboardOpen
}
