package com.bluecheese.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.bluecheese.android.navigation.MainNavigation
import com.bluecheese.android.navigation.NavigationParameter
import com.bluecheese.android.navigation.RouterImpl
import com.bluecheese.android.presentation.common.ApplicationEffect
import com.bluecheese.android.presentation.common.ApplicationState
import com.bluecheese.android.presentation.common.BlueCheeseLocalProvider
import com.bluecheese.android.presentation.common.rememberApplicationState
import com.bluecheese.android.ui.theme.BlueCheeseTheme
import com.bluecheese.mvi.foundation.EventReceiver
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private lateinit var auth: FirebaseAuth

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var router: RouterImpl

    @Inject
    lateinit var applicationEffect: EventReceiver<ApplicationEffect>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val startDestination =
            if (auth.currentUser == null) NavigationParameter.Login else NavigationParameter.NavigationBar
        WindowCompat.setDecorFitsSystemWindows(window, false)

        applicationEffectFlow().launchIn(lifecycleScope)

        enableEdgeToEdge()

        setContent {
            val applicationState: ApplicationState = rememberApplicationState()
            val navController = rememberNavController()
            navController.setLifecycleOwner(this)
            val navigationController = router.setNavController(navController)
            BlueCheeseTheme {
                BlueCheeseLocalProvider(snackbarHostState = applicationState.snackbarHostState) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .safeDrawingPadding(),
                        snackbarHost = {
                            SnackbarHost(hostState = applicationState.snackbarHostState)
                        },
                    ) { _ ->
                        MainNavigation(
                            navigationController = navigationController,
                            startDestination = startDestination,
                            showSnackbar = { message, duration ->
                                applicationState.showSnackbar(
                                    message = message,
                                    duration = duration
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    private fun applicationEffectFlow(): Flow<*> = applicationEffect.eventFlow
        .filterIsInstance<ApplicationEffect.LaunchIntent>()
        .mapNotNull { it.intent }
        .distinctUntilChanged()
        .onEach {
            runCatching { startActivity(it) }.exceptionOrNull()?.let {
                Log.e("MainActivity", "Error in launching intent", it)
            }
        }
}