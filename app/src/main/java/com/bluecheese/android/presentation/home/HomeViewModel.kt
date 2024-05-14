package com.bluecheese.android.presentation.home

import com.bluecheese.android.navigation.RouterImpl
import com.bluecheese.android.presentation.common.RoutingViewModel
import com.bluecheese.mvi.foundation.Model
import com.bluecheese.mvi.foundation.Store
import com.bluecheese.mvi.viewmodel.stateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    router: RouterImpl,
    private val store: Store<HomeState>
) : Model<HomeState, HomeIntent>, RoutingViewModel(router) {

    override fun subscribeTo(intents: Flow<HomeIntent>) = stateMachine(
        store = store,
        intents = intents
    ) {

    }
}