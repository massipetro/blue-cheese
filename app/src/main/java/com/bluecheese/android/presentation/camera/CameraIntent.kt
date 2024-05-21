package com.bluecheese.android.presentation.camera

import android.graphics.Bitmap
import com.bluecheese.mvi.foundation.Intent

sealed interface CameraIntent : Intent {
    data class TakePhoto(val photo: Bitmap) : CameraIntent
    object ResetPhotoCaptured : CameraIntent
    object AcceptPhotoCapture : CameraIntent
}