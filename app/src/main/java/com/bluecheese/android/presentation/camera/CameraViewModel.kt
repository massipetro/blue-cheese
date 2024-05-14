package com.bluecheese.android.presentation.camera

import com.bluecheese.android.navigation.RouterImpl
import com.bluecheese.android.presentation.common.RoutingViewModel
import com.bluecheese.mvi.foundation.Model
import com.bluecheese.mvi.viewmodel.stateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    router: RouterImpl,
) : Model<CameraState, CameraIntent>, RoutingViewModel(router) {
    override fun subscribeTo(intents: Flow<CameraIntent>) = stateMachine(
        initialState = CameraState(),
        intents = intents
    ) {

    }
}