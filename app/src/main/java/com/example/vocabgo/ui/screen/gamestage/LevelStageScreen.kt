package com.example.vocabgo.ui.screen.gamestage

import Nunito
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.vocabgo.R
import com.example.vocabgo.data.dto.GameLevel
import com.example.vocabgo.data.dto.ProgressGameLevel
import com.example.vocabgo.ui.components.MyButton
import com.example.vocabgo.ui.components.MyLottie
import com.example.vocabgo.ui.components.MyProgress
import com.example.vocabgo.ui.viewmodel.gamestage.GameStageViewModel
import com.example.vocabgo.ui.viewmodel.gamestage.LevelStageViewModel


@Composable
fun LevelStageScreen (
    navController: NavController,
    gameStageViewModel: GameStageViewModel,
    levelStageViewModel : LevelStageViewModel
) {
    val gameLevels by levelStageViewModel.gameLevels.collectAsState()
    val gameLevelsProgress by levelStageViewModel.gameLevelsProgress.collectAsState()
    LaunchedEffect(Unit) {
        levelStageViewModel.fetchGameLevelsProgress()
    }

    Surface (
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .systemBarsPadding(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterStart).offset(x = 6.dp),
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
                    Text(
                        "Mức độ từ vựng",
                        modifier = Modifier.align(Alignment.Center),
                        color = MyColors.Hare,
                        style = TextStyle(fontFamily = Nunito, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold )
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color.LightGray)
                )

                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(gameLevels) { gameLevel ->
                        LevelItem(gameLevel, gameLevelsProgress.get(gameLevel.gameLevelId) ,
                            onClick = {
                            gameStageViewModel.getStagesForLevel(gameLevel.gameLevelId)
                            navController.popBackStack()
                        })

                    }

                }
            }
        }

    }
}
@Composable
fun LevelItem(level: GameLevel, progress: ProgressGameLevel?, onClick: (() -> Unit)? = null,) {

    val isDone : Boolean = if (progress != null) progress.stageProgress == progress.totalStage else false;
    val percent = if (progress != null && progress.totalStage > 0) {
        (progress.stageProgress.toFloat() / progress.totalStage.toFloat())
    } else 0f
    val isStarted = progress?.isStarted == true
    MyButton(
        buttonColor = Color.White,
        shadowColor = MyColors.Swan,
        buttonHeight = if (isDone) 96f else 324f,
        shadowBottomOffset = 2f,
        border = BorderStroke(2.dp, MyColors.Swan),
        contentPadding = if (isDone) {
            ButtonDefaults.ContentPadding
        }
        else {
            PaddingValues(0.dp)
        },
        onClick = {
            if (isStarted) {
                onClick?.let {
                    it()
                }
            }
        },
    ) {
        Column () {
            Surface (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = MyColors.Iguana

            ) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    Text(
                        level.gameLevelDescription,
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(12.dp))
                            .padding(16.dp),
                        style = TextStyle(fontFamily = Nunito, fontSize = 14.sp, fontWeight = FontWeight.SemiBold ),
                        color = MyColors.Eel
                    )
                    Box(Modifier.align(Alignment.BottomEnd)) {
                        MyLottie("happy_dog.lottie", 100.dp)
                    }
                }
            }
            Column(

                modifier = Modifier
                    .height(if (isStarted) 100.dp else 70.dp)
                    .padding(if (isDone) {
                        PaddingValues(0.dp)
                    }
                    else {
                        ButtonDefaults.ContentPadding
                    }),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        level.gameLevelName,
                        style = TextStyle(fontFamily = Nunito, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                    )
                }
                if (isStarted) {
                    Box(
                        modifier = Modifier.width(400.dp).height(48.dp), // khung tổng
                        contentAlignment = Alignment.Center
                    ) {
                        MyProgress(
                            progress = percent
                        )



                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .align(Alignment.CenterEnd)
                                .background(Color.White, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.award_solid_full),
                                contentDescription = "award",
                                tint = if (isDone) MaterialTheme.colorScheme.primary else MyColors.Swan,
                                modifier = Modifier.size(44.dp)
                            )
                        }
                    }

                }


            }
        }



    }
}