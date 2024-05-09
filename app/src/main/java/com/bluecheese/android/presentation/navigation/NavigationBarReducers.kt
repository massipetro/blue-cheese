package com.bluecheese.android.presentation.navigation

import com.bluecheese.mvi.foundation.Reducer
import javax.inject.Inject

fun interface NavigationBarReducers {
    fun updateCurrentTab(i: NavigationBarIntent.ChangeTab): Reducer<NavigationBarState>
}

class NavigationBarReducersImpl @Inject constructor() : NavigationBarReducers {
    override fun updateCurrentTab(i: NavigationBarIntent.ChangeTab) = Reducer<NavigationBarState> { s ->
        s.copy(currentTab = i.tab)
    }

}