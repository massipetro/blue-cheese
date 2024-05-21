package com.bluecheese.android.presentation.camera

import com.bluecheese.android.navigation.RouterImpl
import com.bluecheese.android.navigation.navigateBack
import com.bluecheese.android.presentation.common.RoutingViewModel
import com.bluecheese.mvi.foundation.Model
import com.bluecheese.mvi.foundation.Store
import com.bluecheese.mvi.viewmodel.stateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    router: RouterImpl,
    private val store: Store<CameraState>,
    private val reducers: CameraReducers,
) : Model<CameraState, CameraIntent>, RoutingViewModel(router) {
    override fun subscribeTo(intents: Flow<CameraIntent>) = stateMachine(
        store = store,
        intents = intents
    ) {
        on<CameraIntent.TakePhoto>() updateState { i ->
            reducers.updateLastCapturedPhoto(i.photo)
        }

        on<CameraIntent.ResetPhotoCaptured>() updateState {
            reducers.updateLastCapturedPhoto(null)
        }

        on<CameraIntent.AcceptPhotoCapture>() sideEffect {
            navigateBack()
        }
    }
}