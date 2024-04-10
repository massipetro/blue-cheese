package com.bluecheese.android.presentation.common

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.bluecheese.mvi.foundation.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class ApplicationState(
    val snackbarHostState: SnackbarHostState,
    val snackbarScope: CoroutineScope,
) : State {
    fun showSnackbar(message: String, duration: SnackbarDuration = SnackbarDuration.Short) {
        snackbarScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = duration
            )
        }
    }
}

@Composable
fun rememberApplicationState(
    snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    },
    snackbarScope: CoroutineScope = rememberCoroutineScope()
) = remember(snackbarHostState, snackbarScope) {
    ApplicationState(
        snackbarHostState = snackbarHostState,
        snackbarScope = snackbarScope
    )
}
