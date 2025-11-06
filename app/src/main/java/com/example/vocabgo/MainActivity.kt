package com.example.vocabgo

import MyAppTheme
import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vocabgo.ui.navigation.AuthNavigation
import com.example.vocabgo.ui.navigation.HomeNavigation
import com.example.vocabgo.ui.screen.WelcomeScreen
import com.example.vocabgo.ui.viewmodel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


import com.example.vocabgo.ui.screen.loading.FirstLoadingScreen
import kotlinx.coroutines.delay


@HiltAndroidApp
class MyApplication: Application() {

}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                MyApp()
            }

        }
    }

}
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MyApp(authViewModel : AuthViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        delay(1000)

        when (isLoggedIn) {
            true -> {
                navController.navigate("main") {
                    popUpTo(0) { inclusive = true }
                }
            }
            false -> {
                navController.navigate("welcome") {
                    popUpTo(0) { inclusive = true }
                }
            }
            else -> {
                navController.navigate("loading") {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = "loading"
    ) {
        composable(
            "welcome",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700)
                )
                slideOutHorizontally (
                    targetOffsetX = { -it / 2 },
                    animationSpec = tween(700)
                )
            }
        ) {
            WelcomeScreen(navController)
        }

        composable(
            "loading"
        ) {
            FirstLoadingScreen()
        }
        HomeNavigation(navController)
        AuthNavigation(navController)
    }
}
