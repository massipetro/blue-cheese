package com.bluecheese.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainNavigation(navigationController: NavHostController) {
    NavHost(navController = navigationController, startDestination = Route.Splash) {
        composable(Route.Splash) { /* SplashScreen()*/ }
    }
}