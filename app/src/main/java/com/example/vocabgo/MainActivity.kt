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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vocabgo.ui.navigation.AuthNavigation
import com.example.vocabgo.ui.navigation.HomeNavigation
import com.example.vocabgo.ui.screen.WelcomeScreen
import com.example.vocabgo.ui.screen.home.HomeScreen
import com.example.vocabgo.ui.viewmodel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


//class MainActivity : AppCompatActivity() {
//
//    companion object {
//        private const val TAG = "HelloArActivity"
//    }
//
//    lateinit var arCoreSessionHelper: ARCoreSessionLifecycleHelper
//    lateinit var view: HelloArView
//    lateinit var renderer: HelloArRenderer
//
//    val instantPlacementSettings = InstantPlacementSettings()
//    val depthSettings = DepthSettings()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        // Setup ARCore session lifecycle helper and configuration.
//        arCoreSessionHelper = ARCoreSessionLifecycleHelper(this)
//        // If Session creation or Session.resume() fails, display a message and log detailed
//        // information.
//        arCoreSessionHelper.exceptionCallback =
//            { exception ->
//                val message =
//                    when (exception) {
//                        is UnavailableUserDeclinedInstallationException ->
//                            "Please install Google Play Services for AR"
//                        is UnavailableApkTooOldException -> "Please update ARCore"
//                        is UnavailableSdkTooOldException -> "Please update this app"
//                        is UnavailableDeviceNotCompatibleException -> "This device does not support AR"
//                        is CameraNotAvailableException -> "Camera not available. Try restarting the app."
//                        else -> "Failed to create AR session: $exception"
//                    }
//                Log.e(TAG, "ARCore threw an exception", exception)
//                view.snackbarHelper.showError(this, message)
//            }
//
//        // Configure session features, including: Lighting Estimation, Depth mode, Instant Placement.
//        arCoreSessionHelper.beforeSessionResume = ::configureSession
//        lifecycle.addObserver(arCoreSessionHelper)
//
//        // Set up the Hello AR renderer.
//        renderer = HelloArRenderer(this)
//        lifecycle.addObserver(renderer)
//
//        // Set up Hello AR UI.
//        view = HelloArView(this)
//        lifecycle.addObserver(view)
//        setContentView(view.root)
//
//        // Sets up an example renderer using our HelloARRenderer.
//        SampleRender(view.surfaceView, renderer, assets)
//
//        depthSettings.onCreate(this)
//        instantPlacementSettings.onCreate(this)
//    }
//    fun configureSession(session: Session) {
//        session.configure(
//            session.config.apply {
//                lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
//
//                // Depth API is used if it is configured in Hello AR's settings.
//                depthMode =
//                    if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
//                        Config.DepthMode.AUTOMATIC
//                    } else {
//                        Config.DepthMode.DISABLED
//                    }
//
//                // Instant Placement is used if it is configured in Hello AR's settings.
//                instantPlacementMode =
//                    if (instantPlacementSettings.isInstantPlacementEnabled) {
//                        Config.InstantPlacementMode.LOCAL_Y_UP
//                    } else {
//                        Config.InstantPlacementMode.DISABLED
//                    }
//            }
//        )
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        results: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, results)
//        if (!CameraPermissionHelper.hasCameraPermission(this)) {
//            // Use toast instead of snackbar here since the activity will exit.
//            Toast.makeText(this, "Camera permission is needed to run this application", Toast.LENGTH_LONG)
//                .show()
//            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(this)) {
//                // Permission denied with checking "Do not ask again".
//                CameraPermissionHelper.launchPermissionSettings(this)
//            }
//            finish()
//        }
//    }
//
//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//        FullScreenHelper.setFullScreenOnWindowFocusChanged(this, hasFocus)
//    }
//}


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
        if (isLoggedIn) {
            navController.navigate("main") {
                popUpTo(0) { inclusive = true }
            }
        }
        else {
            navController.navigate("welcome") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = "welcome"
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
        HomeNavigation(navController)
        AuthNavigation(navController)
    }
}
