package com.example.vocabgo.ui.screen.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vocabgo.ui.components.MyLoading
import com.example.vocabgo.ui.components.MyLottie
import com.example.vocabgo.ui.screen.gamestage.GameStageScreen
import com.example.vocabgo.ui.screen.quest.QuestScreen
import com.example.vocabgo.ui.screen.streak.StreakScreen
import com.example.vocabgo.ui.screen.user.UserScreen
import com.example.vocabgo.ui.viewmodel.auth.AuthViewModel
import com.example.vocabgo.ui.viewmodel.gamestage.GameStageViewModel
import com.example.vocabgo.ui.viewmodel.gamestage.LevelStageViewModel
import com.example.vocabgo.ui.viewmodel.user.UserViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    gameStageViewModel: GameStageViewModel,
    levelStageViewModel: LevelStageViewModel,
    userViewModel: UserViewModel
) {
    val tabs = listOf("Home", "Quests", "Profile", )
    var selectedTab by remember { mutableStateOf("Home") }
    val isLoadingGameStageScreen by gameStageViewModel.isLoading.collectAsState()
    val isFirstLoadingGameStageScreen by gameStageViewModel.isFirstLoading.collectAsState()
    LaunchedEffect(Unit) {
        launch { gameStageViewModel.fetchData() }
        launch { levelStageViewModel.fetchGameLevelsProgress() }
        launch { userViewModel.fetchUserProfile() }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (isLoadingGameStageScreen && isFirstLoadingGameStageScreen) {
            MyLoading()
        }
        else {
            Column(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    when (selectedTab) {
                        "Home" -> GameStageScreen(navController, gameStageViewModel)
                        "Quests" -> QuestScreen()
                        "Profile" -> UserScreen(navController, userViewModel)
                    }
                }
                Column(
                    Modifier.navigationBarsPadding()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        tabs.forEach { tab ->
                            val isSelected = selectedTab == tab

                            Box(
                                modifier = Modifier
                                    .size(48.dp) // vùng click rộng hơn
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        Color.Transparent
                                    )
                                    .border(
                                        width = if (isSelected) 2.dp else 0.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { selectedTab = tab },
                                contentAlignment = Alignment.Center
                            ) {
                                when (tab) {
                                    "Home" -> Icon(
                                        imageVector = FontAwesomeIcons.Solid.Book,
                                        contentDescription = tab,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    "Quests" -> Icon(
                                        imageVector = FontAwesomeIcons.Solid.Book,
                                        contentDescription = tab,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    "Profile" -> Icon(
                                        imageVector = FontAwesomeIcons.Solid.Book,
                                        contentDescription = tab,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )

                                }

                            }
                        }
                    }
                }


            }
        }

    }
}
