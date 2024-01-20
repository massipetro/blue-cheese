package com.bluecheese.android.navigation

import java.util.Stack

data class NavigationState(
    val currentId: ScreenId,
    val stack: Stack<NavigationParameter>
) {
    val lastParams: NavigationParameter = stack.last()
}
