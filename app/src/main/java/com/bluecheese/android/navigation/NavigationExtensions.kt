package com.bluecheese.android.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

internal inline fun <reified VM : ViewModel> NavGraphBuilder.composable(
    route: NavigationParameter,
    navController: NavHostController,
    parentRoute: NavigationParameter? = null,
    crossinline content: @Composable (VM) -> Unit
) {
    this.composable(
        route = route.id,
        content = { backStackEntry ->
            val viewModel = provideHiltViewModel<VM>(
                navController = navController,
                parentRoute = parentRoute,
            )
            // TODO pass backStackEntry to custom viewmodel
            content.invoke(
                viewModel
            )
        }
    )
}

@Composable
private inline fun <reified VM : ViewModel> provideHiltViewModel(
    navController: NavHostController,
    parentRoute: NavigationParameter? = null,
): VM = parentRoute
    ?.let {
        navController
            .getBackStackEntry(it.id)
            .let { navBackStackEntry ->
                hiltViewModel<VM>(navBackStackEntry)
            }
    }
    ?: hiltViewModel<VM>()