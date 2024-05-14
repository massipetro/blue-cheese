package com.bluecheese.android.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.bluecheese.android.presentation.camera.CameraScreen
import com.bluecheese.android.presentation.camera.CameraViewModel
import com.bluecheese.android.presentation.navigation.NavigationBarScreen
import com.bluecheese.android.presentation.navigation.NavigationBarViewModel
import com.bluecheese.android.presentation.signin.SignInScreen
import com.bluecheese.android.presentation.signin.SignInViewModel
import com.bluecheese.android.presentation.signin.signup.SignUpScreen

@Composable
fun MainNavigation(
    navigationController: Router,
    startDestination: NavigationParameter,
    showSnackbar: (String, SnackbarDuration) -> Unit,
) {
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
        composable<SignInViewModel>(
            route = NavigationParameter.Login,
            navController = navController
        ) { viewModel ->
            SignInScreen(
                model = viewModel,
                showSnackbar
            )
        }

        composable<SignInViewModel>(
            route = NavigationParameter.SignUp,
            navController = navController
        ) { viewModel ->
            SignUpScreen(
                model = viewModel,
                onBack = viewModel::onBack
            )
        }

        composable<NavigationBarViewModel>(
            route = NavigationParameter.NavigationBar,
            navController = navController
        ) { viewModel ->
            NavigationBarScreen(model = viewModel)
        }

        composable<CameraViewModel>(
            route = NavigationParameter.Camera,
            navController = navController
        ) { viewModel ->
            CameraScreen(viewModel)
        }
    }
}