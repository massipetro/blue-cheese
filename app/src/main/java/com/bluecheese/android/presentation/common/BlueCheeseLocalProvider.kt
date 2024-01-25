package com.bluecheese.android.presentation.common

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.bluecheese.android.ui.theme.BlueCheeseTheme

@Composable
internal fun BlueCheeseLocalProvider(
    snackbarHostState: SnackbarHostState,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        values = arrayOf(LocalSnackbarHostState provides snackbarHostState),
        content = content
    )
}

val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
    error("No Snackbar Host State")
}

@Composable
fun BlueCheesePreview(
    content: @Composable () -> Unit
) {
    BlueCheeseLocalProvider(snackbarHostState = SnackbarHostState()) {
        BlueCheeseTheme {
            content()
        }
    }
}