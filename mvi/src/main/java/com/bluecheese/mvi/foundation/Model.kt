package com.bluecheese.mvi.foundation

import kotlinx.coroutines.flow.*

fun interface Model<S : State, I : Intent> {
    fun subscribeTo(intents: Flow<I>): StateFlow<S>
}
