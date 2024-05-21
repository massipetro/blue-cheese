package com.bluecheese.android.presentation.camera

import android.graphics.Bitmap
import com.bluecheese.mvi.foundation.Reducer
import javax.inject.Inject

interface CameraReducers {
    fun updateLastCapturedPhoto(v: Bitmap?): Reducer<CameraState>
}

class CameraReducersImpl @Inject constructor() : CameraReducers {
    override fun updateLastCapturedPhoto(
        v: Bitmap?
    ) = Reducer<CameraState> { s ->
        s.copy(lastCapturedPhoto = v)
    }
}