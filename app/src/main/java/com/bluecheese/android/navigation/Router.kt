package com.bluecheese.android.navigation

import androidx.navigation.NavHostController

interface Router {
    fun setNavController(navHostController: NavHostController): Router
    fun getNavController(): NavHostController
    fun navigateTo(navigationParameter: NavigationParameter)
    fun navigateBackTo(navigationParameter: NavigationParameter, parentRoute: NavigationParameter)
    fun navigateBack()
}