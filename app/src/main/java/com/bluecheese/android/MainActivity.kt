package com.bluecheese.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bluecheese.android.navigation.MainNavigation
import com.bluecheese.android.navigation.NavigationParameter
import com.bluecheese.android.navigation.RouterImpl
import com.bluecheese.android.ui.theme.BlueCheeseTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint

private lateinit var auth: FirebaseAuth

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val startDestination = if (auth.currentUser == null) NavigationParameter.Login else NavigationParameter.Home

        setContent {
            val navController = rememberNavController()
            navController.setLifecycleOwner(this)
            val navigationController = RouterImpl(navController)
            BlueCheeseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation(
                        navigationController = navigationController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}