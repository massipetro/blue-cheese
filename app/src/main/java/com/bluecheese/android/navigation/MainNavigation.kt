package com.bluecheese.android.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.bluecheese.android.presentation.login.LoginScreen
import com.bluecheese.android.presentation.login.LoginViewModel

@Composable
fun MainNavigation(navigationController: Router, startDestination: NavigationParameter) {
    val navController = navigationController.getNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination.id,
        modifier = Modifier.fillMaxSize(),
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(500)
            )
        },
    ) {
        composable<LoginViewModel>(
            route = NavigationParameter.Login,
            navController = navController
        ) { viewModel ->
            LoginScreen(viewModel)
        }
    }
}