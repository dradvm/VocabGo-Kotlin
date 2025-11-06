package com.example.vocabgo

import ArRenderer
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.vocabgo.common.helpers.ARCoreSessionLifecycleHelper
import com.example.vocabgo.common.helpers.CameraPermissionHelper
import com.example.vocabgo.ui.screen.ar.ArActivityView
import com.google.ar.core.CameraConfig
import com.google.ar.core.CameraConfigFilter
import com.google.ar.core.Config
import com.google.ar.core.exceptions.CameraNotAvailableException
import com.google.ar.core.exceptions.UnavailableApkTooOldException
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException
import com.google.ar.core.exceptions.UnavailableSdkTooOldException
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException

class ArActivity : ComponentActivity() {

    companion object {
        const val TAG = "ArActivity"
    }
    lateinit var arCoreSessionLifecycleHelper: ARCoreSessionLifecycleHelper
    lateinit var renderer: ArRenderer
    lateinit var view: ArActivityView
    val cameraPermissionLauncher = CameraPermissionHelper.createPermissionLauncher(
        this,
        {

        },
        {
            Toast.makeText(
                this,
                "Camera permission is needed to run this application",
                Toast.LENGTH_LONG
            )
                .show()
            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(this)) {
                // Permission denied with checking "Do not ask again".
                CameraPermissionHelper.openAppSettings(this)
            }
            this.finish()
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arCoreSessionLifecycleHelper = ARCoreSessionLifecycleHelper(this)

        arCoreSessionLifecycleHelper.exceptionCallback =
            { exception ->
                val message =
                    when (exception) {
                        is UnavailableArcoreNotInstalledException,
                        is UnavailableUserDeclinedInstallationException -> "Please install ARCore"
                        is UnavailableApkTooOldException -> "Please update ARCore"
                        is UnavailableSdkTooOldException -> "Please update this app"
                        is UnavailableDeviceNotCompatibleException -> "This device does not support AR"
                        is CameraNotAvailableException -> "Camera not available. Try restarting the app."
                        else -> "Failed to create AR session: $exception"
                    }
                Log.e(TAG, message, exception)
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        arCoreSessionLifecycleHelper.beforeSessionResume =
            { session ->
                session.configure(
                    session.config.apply {
                        // To get the best image of the object in question, enable autofocus.
                        focusMode = Config.FocusMode.AUTO
                        if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                            depthMode = Config.DepthMode.AUTOMATIC
                        }
                    }
                )

                val filter =
                    CameraConfigFilter(session).setFacingDirection(CameraConfig.FacingDirection.BACK)
                val configs = session.getSupportedCameraConfigs(filter)
                val sort =
                    compareByDescending<CameraConfig> { it.imageSize.width }.thenByDescending {
                        it.imageSize.height
                    }
                session.cameraConfig = configs.sortedWith(sort)[0]
            }
        lifecycle.addObserver(arCoreSessionLifecycleHelper)

        renderer = ArRenderer(this)
        lifecycle.addObserver(renderer)
        view = ArActivityView(this,  renderer)
        setContentView(view.root)
        renderer.bindView(view)
        lifecycle.addObserver(view)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
    }
}