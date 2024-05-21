package com.bluecheese.android.presentation.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.bluecheese.android.R
import com.bluecheese.android.presentation.common.BlueCheesePreview
import com.bluecheese.android.ui.components.atoms.Spacer16
import com.bluecheese.android.ui.theme.Dimen
import com.bluecheese.android.ui.theme.PreviewScreen
import com.bluecheese.mvi.compose.rememberMviComponent
import com.bluecheese.mvi.foundation.Model
import java.util.concurrent.Executor

@Composable
fun CameraScreen(model: Model<CameraState, CameraIntent>) {
    val (state, onIntent) = rememberMviComponent(model = model)

    CameraScreen(
        state = state,
        onPhotoCaptured = { CameraIntent.TakePhoto(it).let(onIntent) },
        onResetPhotoCaptured = { CameraIntent.ResetPhotoCaptured.let(onIntent) },
        onAcceptPhotoCapture = { CameraIntent.AcceptPhotoCapture.let(onIntent) },
    )
}

@Composable
private fun CameraScreen(
    state: CameraState,
    onPhotoCaptured: (Bitmap) -> Unit,
    onResetPhotoCaptured: () -> Unit,
    onAcceptPhotoCapture: () -> Unit,
) {
    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController =
        remember { LifecycleCameraController(context) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    setBackgroundColor(Color.BLACK)
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    scaleType = PreviewView.ScaleType.FILL_START
                }.also { previewView ->
                    previewView.controller = cameraController
                    cameraController.bindToLifecycle(lifecycleOwner)
                }
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = Dimen.Margin8)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (state.lastCapturedPhoto != null) {
                FilledTonalButton(
                    onClick = onResetPhotoCaptured
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = null,
                    )
                }
                Spacer16()
                FilledTonalButton(
                    onClick = onAcceptPhotoCapture
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_done),
                        contentDescription = null,
                    )
                }
            } else FilledTonalButton(
                onClick = { capturePhoto(context, cameraController, onPhotoCaptured) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                )
            }
        }
        if (state.lastCapturedPhoto != null) {
            LastPhotoPreview(lastCapturedPhoto = state.lastCapturedPhoto)
        }
    }
}

@Composable
private fun LastPhotoPreview(
    modifier: Modifier = Modifier,
    lastCapturedPhoto: Bitmap
) {
    val capturedPhoto: ImageBitmap = remember(lastCapturedPhoto.hashCode()) {
        lastCapturedPhoto.asImageBitmap()
    }
    val shape = MaterialTheme.shapes.large
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = shape
    ) {
        Box(modifier = Modifier.padding(Dimen.Margin8)) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(shape),
                bitmap = capturedPhoto,
                contentDescription = "Last captured photo",
                contentScale = ContentScale.None
            )
        }
    }
}

private fun capturePhoto(
    context: Context,
    cameraController: LifecycleCameraController,
    onPhotoCaptured: (Bitmap) -> Unit
) {
    val mainExecutor: Executor = ContextCompat.getMainExecutor(context)

    cameraController.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: ImageProxy) {
            val correctedBitmap: Bitmap = image
                .toBitmap()
                .rotateBitmap(image.imageInfo.rotationDegrees)

            onPhotoCaptured(correctedBitmap)
            image.close()
        }

        override fun onError(exception: ImageCaptureException) {
            Log.e("CameraContent", "Error capturing image", exception)
        }
    })
}

private fun Bitmap.rotateBitmap(rotationDegrees: Int): Bitmap {
    val matrix = Matrix().apply {
        postRotate(-rotationDegrees.toFloat())
        postScale(-1f, -1f)
    }

    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

@PreviewScreen
@Composable
fun CameraScreenPreview() = BlueCheesePreview {
    CameraScreen(
        state = CameraState(
            lastCapturedPhoto = Bitmap.createBitmap(
                448,
                923,
                Bitmap.Config.ARGB_8888
            )
        ),
        {}, {}, {}
    )
}