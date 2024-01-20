package com.bluecheese.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.bluecheese.android.presentation.login.LoginScreen
import com.bluecheese.android.presentation.login.LoginViewModel

@Composable
fun MainNavigation(navigationController: Router, startDestination: NavigationParameter) {
    val navController = navigationController.getNavController()
    NavHost(navController = navController, startDestination = startDestination.id) {
        composable<LoginViewModel>(
            route = NavigationParameter.Login,
            navController = navController
        ) { viewModel ->
            LoginScreen(viewModel)
        }
    }
}