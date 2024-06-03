package com.bluecheese.android.presentation.home

import com.bluecheese.android.navigation.RouterImpl
import com.bluecheese.android.presentation.common.RoutingViewModel
import com.bluecheese.domain.interactor.RetrievePhotosFromGalleryUseCase
import com.bluecheese.mvi.dsl.SideEffectScope
import com.bluecheese.mvi.foundation.Model
import com.bluecheese.mvi.foundation.Store
import com.bluecheese.mvi.viewmodel.stateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    router: RouterImpl,
    private val store: Store<HomeState>,
    private val reducers: HomeReducers,
    private val retrievePhotosFromGalleryUseCase: RetrievePhotosFromGalleryUseCase,
) : Model<HomeState, HomeIntent>, RoutingViewModel(router) {

    override fun subscribeTo(intents: Flow<HomeIntent>) = stateMachine(
        store = store,
        intents = intents
    ) {
        on<HomeIntent.SelectDate>() sideEffect {
            loadPhotos(it.date.div(1000))
        }
    }

    private suspend fun SideEffectScope<HomeState>.loadPhotos(
        date: Long
    ) = retrievePhotosFromGalleryUseCase
        .perform(date)
        .map { updateState(reducers.updatePhotos(it)) }
}