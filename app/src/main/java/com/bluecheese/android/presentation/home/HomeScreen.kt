package com.bluecheese.android.presentation.home

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bluecheese.android.R
import com.bluecheese.android.presentation.common.BlueCheesePreview
import com.bluecheese.android.ui.components.atoms.Spacer8
import com.bluecheese.android.ui.components.atoms.SpacerFull
import com.bluecheese.android.ui.components.molecules.Carousel
import com.bluecheese.android.ui.components.molecules.CarouselItemPreviewProvider
import com.bluecheese.android.ui.theme.Dimen
import com.bluecheese.android.ui.theme.PreviewScreen
import com.bluecheese.mvi.compose.rememberMviComponent
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

object HomeScreen {
    val ScreenHorizontalMargin = Dimen.Margin16
    val ScreenVerticalMargin = Dimen.Margin16

    data class Actions(
        val onSelectDay: (Long) -> Unit,
        val onOpenPhoto: (Carousel.Item) -> Unit,
        val onCloseOverlay: () -> Unit,
        val onShareSelectedPhoto: (Uri) -> Unit,
    ) {
        companion object {
            val NoActions = Actions({}, {}, {}, {})
        }
    }
}

@Composable
fun HomeScreen(
    model: HomeViewModel,
    onHideNavigationBar: () -> Unit,
    onShowNavigationBar: () -> Unit,
) {
    val (state, onIntent) = rememberMviComponent(model = model)

    LaunchedEffect(model.eventFlow) {
        model.eventFlow.collect { effect ->
            when (effect) {
                HomeEffect.HideNavigationBar -> onHideNavigationBar()
                HomeEffect.ShowNavigationBar -> onShowNavigationBar()
            }
        }
    }

    HomeScreen(
        state = state,
        actions = HomeScreen.Actions(
            onSelectDay = { HomeIntent.SelectDate(it).let(onIntent) },
            onOpenPhoto = { HomeIntent.OpenPhoto(it.uri).let(onIntent) },
            onCloseOverlay = { HomeIntent.HidePhoto.let(onIntent) },
            onShareSelectedPhoto = { HomeIntent.SharePhoto(it).let(onIntent) },
        )
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    actions: HomeScreen.Actions
) = Box {
    val hazeState = remember { HazeState() }
    InternalContent(
        state = state,
        actions = actions,
        modifier = Modifier.haze(state = hazeState)
    )
    if (state.selectedPhoto != null) SelectedPhotoOverlay(
        hazeState = hazeState,
        selectedPhoto = state.selectedPhoto,
        actions = actions,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun InternalContent(
    state: HomeState,
    actions: HomeScreen.Actions,
    modifier: Modifier,
) {
    val datePickerState = rememberDatePickerState(
        yearRange = IntRange(2024, state.currentYear),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        }
    )
    val photos = state.photos

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { actions.onSelectDay(it) }
    }

    Column(
        modifier = modifier
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
        if (photos != null) {
            Carousel(
                items = photos,
                onItemClick = actions.onOpenPhoto,
                modifier = Modifier.padding(horizontal = HomeScreen.ScreenHorizontalMargin),
            )
        }
    }
}

@Composable
fun SelectedPhotoOverlay(
    hazeState: HazeState,
    selectedPhoto: Uri,
    actions: HomeScreen.Actions
) = Column(
    modifier = Modifier
        .background(Color.Transparent)
        .hazeChild(state = hazeState)
        .padding(
            vertical = HomeScreen.ScreenVerticalMargin,
            horizontal = HomeScreen.ScreenHorizontalMargin
        )
) {
    Row {
        IconButton(onClick = actions.onCloseOverlay) {
            Icon(painter = painterResource(R.drawable.ic_arrow_down), "close-overlay")
        }
        SpacerFull()
        IconButton(onClick = { actions.onShareSelectedPhoto(selectedPhoto) }) {
            Icon(painter = painterResource(R.drawable.ic_share), "share-photo")
        }
    }
    AsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(selectedPhoto)
            .build(),
        contentDescription = "selected photo"
    )
}

@PreviewScreen
@Composable
fun HomeScreenPreview() = BlueCheesePreview {
    val photos = CarouselItemPreviewProvider().values.first()
    HomeScreen(
        state = HomeState(photos = photos, selectedPhoto = Uri.EMPTY),
        actions = HomeScreen.Actions.NoActions
    )
}