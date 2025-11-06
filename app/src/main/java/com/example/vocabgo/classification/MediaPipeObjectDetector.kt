package com.example.vocabgo.classification

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Point
import android.media.Image
import com.example.vocabgo.classification.utils.ImageUtils
import com.example.vocabgo.classification.utils.VertexUtils.rotateCoordinates
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetector
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetector.ObjectDetectorOptions
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.core.ImageProcessingOptions
import kotlinx.coroutines.tasks.await



class MediaPipeObjectDetector(context: Activity) : com.example.vocabgo.classification.ObjectDetector(context) {

    private val options = ObjectDetectorOptions.builder()
        .setBaseOptions(
            BaseOptions.builder()
                .setModelAssetPath("models/efficientdet_lite2.tflite") // model mặc định
                .build()
        )
        .setRunningMode(RunningMode.IMAGE)
        .setScoreThreshold(0.6f)
        .setMaxResults(5)
        .build()

    private val detector = ObjectDetector.createFromOptions(context, options)

    override suspend fun analyze(image: Image, imageRotation: Int): List<DetectedObjectResult> {
        // Chuyển YUV -> Bitmap (giống MLKit)
        val bitmap = convertYuv(image)

        // Xoay ảnh về hướng đúng
        val rotated = ImageUtils.rotateBitmap(bitmap, imageRotation)

        val mpImage = BitmapImageBuilder(rotated).build()
        val result = detector.detect(mpImage)

        return result.detections().mapNotNull { detection ->
            val category = detection.categories().maxByOrNull { it.score() } ?: return@mapNotNull null
            val bbox = detection.boundingBox()

            val coords =
                Point(bbox.centerX().toInt(), bbox.centerX().toInt())
            val rotatedCoordinates =
                coords.rotateCoordinates(rotated.width, rotated.height, imageRotation)
            DetectedObjectResult(
                confidence = category.score(),
                label = category.categoryName(),
                centerCoordinate = rotatedCoordinates
            )
        }
    }
}
