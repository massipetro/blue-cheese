package com.bluecheese.android.navigation

import androidx.navigation.NavHostController

class RouterImpl(
    private val navController: NavHostController
) : Router {

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