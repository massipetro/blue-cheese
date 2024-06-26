package com.bluecheese.android.presentation.navigation

import com.bluecheese.mvi.foundation.Reducer
import javax.inject.Inject

interface NavigationBarReducers {
    val showNavigationBar: Reducer<NavigationBarState>
    val hideNavigationBar: Reducer<NavigationBarState>

    fun updateCurrentTab(i: NavigationBarIntent.ChangeTab): Reducer<NavigationBarState>
}

class NavigationBarReducersImpl @Inject constructor() : NavigationBarReducers {
    override val showNavigationBar = Reducer<NavigationBarState> { s ->
        s.copy(isNavigationBarVisible = true)
    }

    override val hideNavigationBar = Reducer<NavigationBarState> { s ->
        s.copy(isNavigationBarVisible = false)
    }

    override fun updateCurrentTab(
        i: NavigationBarIntent.ChangeTab
    ) = Reducer<NavigationBarState> { s ->
        s.copy(currentTab = i.tab)
    }
}