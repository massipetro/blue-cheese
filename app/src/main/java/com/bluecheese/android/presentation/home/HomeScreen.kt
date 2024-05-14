package com.bluecheese.android.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bluecheese.android.presentation.common.BlueCheesePreview
import com.bluecheese.android.ui.theme.PreviewScreen
import com.bluecheese.mvi.compose.rememberMviComponent
import com.bluecheese.mvi.foundation.Model

private data class HomeScreenActions(
    val onSelectDay: () -> Unit
) {
    companion object {
        val NoActions = HomeScreenActions({})
    }
}

@Composable
fun HomeScreen(model: Model<HomeState, HomeIntent>) {
    val (state, onIntent) = rememberMviComponent(model = model)

    HomeScreen(state = state, actions = HomeScreenActions { })
}

@Composable
private fun HomeScreen(
    state: HomeState,
    actions: HomeScreenActions
) = Column {
    InternalContent()
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun InternalContent() {
    val datePickerState = rememberDatePickerState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        DatePicker(
            state = datePickerState,
            title = null,
            headline = null,
            showModeToggle = false
        )
    }
}

@PreviewScreen
@Composable
fun HomeScreenPreview() = BlueCheesePreview {
    HomeScreen(HomeState(), HomeScreenActions.NoActions)
}