package com.bluecheese.mvi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluecheese.mvi.dsl.IntentStateMachineScope
import com.bluecheese.mvi.foundation.Intent
import com.bluecheese.mvi.foundation.State
import com.bluecheese.mvi.foundation.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import com.bluecheese.mvi.dsl.stateMachine

fun <S : State, I : Intent> ViewModel.stateMachine(
    initialState: S,
    intents: Flow<I>,
    builder: IntentStateMachineScope<S, I>.() -> Unit
): StateFlow<S> = stateMachine(
    initialState = initialState,
    intents = intents,
    coroutineScope = viewModelScope,
    builder = builder,
)

fun <S : State, I : Intent> ViewModel.stateMachine(
    store: Store<S>,
    intents: Flow<I>,
    builder: IntentStateMachineScope<S, I>.() -> Unit
): StateFlow<S> = stateMachine(
    store = store,
    intents = intents,
    coroutineScope = viewModelScope,
    builder = builder,
)