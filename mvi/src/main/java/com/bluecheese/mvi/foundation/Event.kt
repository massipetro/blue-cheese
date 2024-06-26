package com.bluecheese.mvi.foundation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface Event

interface EventReceiver<E : Event> {
    val eventFlow: Flow<E>
}

fun interface EventSender<E: Event> {
    fun emit(event: E)
}

class FlowEventEmitter<E: Event>(
    private val scope: CoroutineScope
) : EventReceiver<E>, EventSender<E> {
    private val sharedFlow = MutableSharedFlow<E>()
    override val eventFlow: Flow<E> = sharedFlow.asSharedFlow()

    override fun emit(event: E) { scope.launch { sharedFlow.emit(event) } }
}