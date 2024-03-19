package com.bluecheese.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bluecheese.android.navigation.MainNavigation
import com.bluecheese.android.navigation.NavigationParameter
import com.bluecheese.android.navigation.RouterImpl
import com.bluecheese.android.presentation.common.BlueCheeseLocalProvider
import com.bluecheese.android.ui.theme.BlueCheeseTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private lateinit var auth: FirebaseAuth

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var router: RouterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val startDestination =
            if (auth.currentUser == null) NavigationParameter.Login else NavigationParameter.Home

        setContent {
            val navController = rememberNavController()
            navController.setLifecycleOwner(this)
            val navigationController = router.setNavController(navController)
            BlueCheeseTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                BlueCheeseLocalProvider(snackbarHostState = snackbarHostState) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    ) { _ ->
                        MainNavigation(
                            navigationController = navigationController,
                            startDestination = startDestination
                        )
                    }
                }
            }
        }
    }
}