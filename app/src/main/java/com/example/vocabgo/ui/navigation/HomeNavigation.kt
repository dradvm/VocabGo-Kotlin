package com.example.vocabgo.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.vocabgo.ui.screen.auth.LoginScreen
import com.example.vocabgo.ui.screen.flashcard.FlashCardScreen
import com.example.vocabgo.ui.screen.game.GameScreen
import com.example.vocabgo.ui.screen.gamestage.LevelStageScreen
import com.example.vocabgo.ui.screen.home.HomeScreen


fun NavGraphBuilder.HomeNavigation (navController: NavController) {
    navigation(
        startDestination = "main",
        route = "home"
    ) {
        composable(
            "main",
            exitTransition = {
                ExitTransition.None
            },
            enterTransition = {
                EnterTransition.None
            }
        ) {
//            HomeScreen(navController)
//            FlashCardScreen()
            GameScreen()
        }

        composable(
            "level",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    tween(700)
                )
            },
            popExitTransition = { slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                tween(1000)

            )}
        ) {
            LevelStageScreen(navController)
        }
    }
}