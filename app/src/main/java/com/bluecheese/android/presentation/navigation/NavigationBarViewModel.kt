package com.bluecheese.android.presentation.navigation

import com.bluecheese.android.navigation.RouterImpl
import com.bluecheese.android.presentation.common.RoutingViewModel
import com.bluecheese.mvi.foundation.Model
import com.bluecheese.mvi.foundation.Store
import com.bluecheese.mvi.viewmodel.stateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NavigationBarViewModel @Inject constructor(
    router: RouterImpl,
    private val store: Store<NavigationBarState>,
    private val reducers: NavigationBarReducers
) : Model<NavigationBarState, NavigationBarIntent>, RoutingViewModel(router) {
    override fun subscribeTo(intents: Flow<NavigationBarIntent>) = stateMachine(
        store = store,
        intents = intents
    ) {
        on<NavigationBarIntent.ChangeTab>() updateState reducers::updateCurrentTab
    }
}