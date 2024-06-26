package com.bluecheese.android.presentation.home

import com.bluecheese.mvi.foundation.Event

sealed interface HomeEffect : Event {
    data object HideNavigationBar : HomeEffect
    data object ShowNavigationBar : HomeEffect
}