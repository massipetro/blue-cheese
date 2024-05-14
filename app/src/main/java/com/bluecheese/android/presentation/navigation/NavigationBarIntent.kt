package com.bluecheese.android.presentation.navigation

import com.bluecheese.mvi.foundation.Intent

sealed interface NavigationBarIntent : Intent {
    data class ChangeTab(val tab: NavigationBarState.Tab) : NavigationBarIntent
    data object OpenCamera : NavigationBarIntent
}