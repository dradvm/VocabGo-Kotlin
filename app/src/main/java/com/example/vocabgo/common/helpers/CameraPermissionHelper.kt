package com.example.vocabgo.common.helpers
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class CameraPermissionHelper() {
    companion object {
        const val CAMERA_PERMISSION = Manifest.permission.CAMERA

        fun hasCameraPermission(activity: ComponentActivity): Boolean =
            ContextCompat.checkSelfPermission(activity, CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED

        fun shouldShowRequestPermissionRationale(activity: ComponentActivity): Boolean =
            activity.shouldShowRequestPermissionRationale(CAMERA_PERMISSION)

        fun openAppSettings(activity: ComponentActivity) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", activity.packageName, null)
            }
            activity.startActivity(intent)
        }

        fun createPermissionLauncher(
            activity: ComponentActivity,
            onGranted: () -> Unit,
            onDenied: () -> Unit
        ): ActivityResultLauncher<String> {
            return activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (granted) onGranted() else onDenied()
            }
        }
    }


}