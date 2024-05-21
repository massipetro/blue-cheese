package com.bluecheese.android.presentation.camera

import android.graphics.Bitmap
import com.bluecheese.mvi.foundation.State

data class CameraState(
    val lastCapturedPhoto: Bitmap? = null,
): State
