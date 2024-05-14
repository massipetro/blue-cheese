package com.bluecheese.android.presentation.home

import com.bluecheese.mvi.foundation.State

data class HomeState(
    val loading: Boolean = false,
): State
