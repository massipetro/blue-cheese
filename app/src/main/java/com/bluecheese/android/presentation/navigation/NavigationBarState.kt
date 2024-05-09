package com.bluecheese.android.presentation.navigation

import androidx.annotation.DrawableRes
import com.bluecheese.android.R
import com.bluecheese.mvi.foundation.State

data class NavigationBarState(
    val currentTab: Tab = Tab.Home
) : State {
    enum class Tab(@DrawableRes val icon: Int) {
        Home(R.drawable.ic_home),
        Profile(R.drawable.ic_profile);
    }
}
