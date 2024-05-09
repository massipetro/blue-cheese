package com.bluecheese.android.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.bluecheese.android.R
import com.bluecheese.android.presentation.home.HomeScreen
import com.bluecheese.android.presentation.home.HomeViewModel
import com.bluecheese.mvi.compose.rememberMviComponent
import com.bluecheese.mvi.foundation.Model

@Composable
fun NavigationBarScreen(model: Model<NavigationBarState, NavigationBarIntent>) {
    val (state, onIntent) = rememberMviComponent(model = model)

    val homeContent = @Composable {
        val homeViewModel = hiltViewModel<HomeViewModel>()
        Column {
            HomeScreen(model = homeViewModel)
        }
    }

    val profileContent = @Composable {
        Column {
            Text(text = "Profile")
        }
    }

    NavigationBarScreen(
        state = state,
        homeContent = homeContent,
        profileContent = profileContent,
        onTabSelected = { NavigationBarIntent.ChangeTab(it).let(onIntent) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationBarScreen(
    state: NavigationBarState,
    homeContent: @Composable () -> Unit,
    profileContent: @Composable () -> Unit,
    onTabSelected: (NavigationBarState.Tab) -> Unit,
) = Scaffold(
    topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) }
        )
    },
    bottomBar = {
        NavigationBar {
            NavigationBarState.Tab.entries.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.name
                        )
                    },
                    label = { Text(item.name) },
                    selected = state.currentTab == item,
                    onClick = { onTabSelected(item) }
                )
            }
        }
    }
) { padding ->
    Column(Modifier.padding(padding)) {
        when (state.currentTab) {
            NavigationBarState.Tab.Home -> homeContent()
            NavigationBarState.Tab.Profile -> profileContent()
        }
    }
}