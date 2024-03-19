package com.bluecheese.android.navigation

import androidx.navigation.NavHostController
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class RouterImpl @Inject constructor() : Router {

    private lateinit var navController: NavHostController

    override fun setNavController(navHostController: NavHostController): Router {
        navController = navHostController
        return this
    }

    override fun getNavController(): NavHostController = navController

    override fun navigateTo(navigationParameter: NavigationParameter) {
        navController.navigate(navigationParameter.id)
    }

    override fun navigateBackTo(
        navigationParameter: NavigationParameter,
        parentRoute: NavigationParameter
    ) {
        navController.navigate(navigationParameter.id) {
            popUpTo(parentRoute.id)
        }
    }

    override fun navigateBack() {
        if (navController.previousBackStackEntry == null) Unit
        else navController.popBackStack()
    }
}