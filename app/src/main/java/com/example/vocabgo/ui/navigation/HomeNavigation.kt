package com.example.vocabgo.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.vocabgo.ui.screen.flashcard.FlashCardScreen
import com.example.vocabgo.ui.screen.game.GameScreen
import com.example.vocabgo.ui.screen.gamestage.LevelStageScreen
import com.example.vocabgo.ui.screen.home.HomeScreen
import com.example.vocabgo.ui.screen.reward.RewardScreen
import com.example.vocabgo.ui.screen.settings.SettingScreen
import com.example.vocabgo.ui.screen.streak.StreakClaimScreen
import com.example.vocabgo.ui.screen.streak.StreakScreen
import com.example.vocabgo.ui.viewmodel.flashcard.FlashCardViewModel
import com.example.vocabgo.ui.viewmodel.gamestage.GameStageViewModel
import com.example.vocabgo.ui.viewmodel.gamestage.LevelStageViewModel
import com.example.vocabgo.ui.viewmodel.streak.StreakViewModel
import com.example.vocabgo.ui.viewmodel.user.UserViewModel


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
        ) { backStackEntry ->
            val navGraphEntry = remember(backStackEntry) {
                navController.getBackStackEntry("home")
            }
            val gameStageViewModel: GameStageViewModel = hiltViewModel(navGraphEntry)
            val levelStageViewModel: LevelStageViewModel = hiltViewModel(navGraphEntry)
            val userViewModel: UserViewModel = hiltViewModel(navGraphEntry)
            HomeScreen(navController, gameStageViewModel, levelStageViewModel, userViewModel)
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
        ) { backStackEntry ->
            val navGraphEntry = remember(backStackEntry) {
                navController.getBackStackEntry("home")
            }
            val sharedGameStageViewModel: GameStageViewModel = hiltViewModel(navGraphEntry)
            val levelStageViewModel: LevelStageViewModel = hiltViewModel(navGraphEntry)
            LevelStageScreen(navController, sharedGameStageViewModel, levelStageViewModel)
        }

        composable (
            "settings",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ) {
            SettingScreen(navController)
        }

        composable (
            "game",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ) { backStackEntry ->
            val navGraphEntry = remember(backStackEntry) {
                navController.getBackStackEntry("home")
            }
            val sharedGameStageViewModel: GameStageViewModel = hiltViewModel(navGraphEntry)
            GameScreen(sharedGameStageViewModel, navController)
        }

        composable (
            route = "flashcard/{buttonColor}/{shadowColor}",
            arguments = listOf(
                navArgument("buttonColor") { type = NavType.IntType },
                navArgument("shadowColor") { type = NavType.IntType }
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ) { backStackEntry ->
            val navGraphEntry = remember(backStackEntry) {
                navController.getBackStackEntry("home")
            }
            val sharedGameStageViewModel: GameStageViewModel = hiltViewModel(navGraphEntry)
            val flashCardViewModel : FlashCardViewModel = hiltViewModel()
            val buttonColor = Color(backStackEntry.arguments?.getInt("buttonColor") ?: Color.White.toArgb())
            val shadowColor = Color(backStackEntry.arguments?.getInt("shadowColor") ?: Color.Black.toArgb())
            FlashCardScreen(navController, sharedGameStageViewModel, flashCardViewModel, buttonColor, shadowColor)
        }


        composable (
            "reward?userLessonProgressId={id}&kp={kp}&timeSpent={timeSpent}&accuracyRate={accuracyRate}",
            arguments = listOf(
                navArgument("id") {type = NavType.StringType; defaultValue = ""},
                navArgument("kp") {type = NavType.IntType; defaultValue = 0},
                        navArgument("timeSpent") {type = NavType.IntType; defaultValue = 0},
                        navArgument("accuracyRate") {type = NavType.FloatType; defaultValue = 0f}
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val kp = backStackEntry.arguments?.getInt("kp") ?: 0
            val timeSpent = backStackEntry.arguments?.getInt("timeSpent") ?: 0
            val accuracyRate = backStackEntry.arguments?.getFloat("accuracyRate") ?: 0f
            RewardScreen(id, kp, timeSpent, accuracyRate, navController)
        }
        composable(
            route = "streak",
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
        ) { backStackEntry ->
            val navGraphEntry = remember(backStackEntry) {
                navController.getBackStackEntry("home")
            }
            val streakViewModel: StreakViewModel = hiltViewModel(navGraphEntry)
            StreakScreen(navController, streakViewModel)
        }

        composable(
            route = "streakClaim",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ) { backStackEntry ->
            val navGraphEntry = remember(backStackEntry) {
                navController.getBackStackEntry("home")
            }
            val streakViewModel: StreakViewModel = hiltViewModel(navGraphEntry)
            StreakClaimScreen(navController, streakViewModel)
        }
    }
}