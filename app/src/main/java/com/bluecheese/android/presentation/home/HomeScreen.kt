package com.bluecheese.android.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.bluecheese.android.presentation.common.BlueCheesePreview
import com.bluecheese.android.ui.components.atoms.Spacer8
import com.bluecheese.android.ui.components.molecules.Carousel
import com.bluecheese.android.ui.components.molecules.CarouselItemPreviewProvider
import com.bluecheese.android.ui.theme.Dimen
import com.bluecheese.android.ui.theme.PreviewScreen
import com.bluecheese.mvi.compose.rememberMviComponent
import com.bluecheese.mvi.foundation.Model

object HomeScreen {
    val ScreenHorizontalMargin = Dimen.Margin16

    data class Actions(
        val onSelectDay: (Long) -> Unit
    ) {
        companion object {
            val NoActions = Actions({})
        }
    }
}

@Composable
fun HomeScreen(model: Model<HomeState, HomeIntent>) {
    val (state, onIntent) = rememberMviComponent(model = model)

    HomeScreen(
        state = state,
        actions = HomeScreen.Actions(
            onSelectDay = { HomeIntent.SelectDate(it).let(onIntent) }
        )
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    actions: HomeScreen.Actions
) = Column {
    InternalContent(state, actions)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun InternalContent(
    state: HomeState,
    actions: HomeScreen.Actions,
) {
    val datePickerState = rememberDatePickerState()
    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { actions.onSelectDay(it) }
    }

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
        Spacer8()
        state.photos?.let {
            Carousel(
                items = it,
                modifier = Modifier.padding(horizontal = HomeScreen.ScreenHorizontalMargin)
            )
        }
    }
}

@PreviewScreen
@Composable
fun HomeScreenPreview() = BlueCheesePreview {
    val photos = CarouselItemPreviewProvider().values.first()
    HomeScreen(
        state = HomeState(photos = photos),
        actions = HomeScreen.Actions.NoActions
    )
}