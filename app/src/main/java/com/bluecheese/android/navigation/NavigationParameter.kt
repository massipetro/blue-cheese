package com.bluecheese.android.navigation

typealias ScreenId = String

sealed class NavigationParameter(
    val id: ScreenId,
    val isAuthenticated: Boolean,
    val clearStack: Boolean = false,
) {
    data object Login : NavigationParameter(
        id = "login",
        isAuthenticated = false
    )

    data object SignUp : NavigationParameter(
        id = "signup",
        isAuthenticated = false
    )

    data object NavigationBar : NavigationParameter(
        id = "navigation-bar",
        isAuthenticated = true
    )

    data object Home : NavigationParameter(
        id = "home",
        isAuthenticated = true
    )

    data object Profile : NavigationParameter(
        id = "profile",
        isAuthenticated = true
    )
}