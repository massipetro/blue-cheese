package com.bluecheese.android.presentation.common

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.bluecheese.android.navigation.NavigationParameter

abstract class RoutingViewModel(
    private val navController: NavController,
) : ViewModel() {

    fun navigateTo(route: NavigationParameter) = navController.navigate(route.id)

    fun onBack() =
        if (navController.previousBackStackEntry == null) false
        else navController.popBackStack()
}