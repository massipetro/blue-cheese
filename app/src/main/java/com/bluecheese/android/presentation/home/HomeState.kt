package com.bluecheese.android.presentation.home

import com.bluecheese.android.ui.components.molecules.Carousel
import com.bluecheese.mvi.foundation.State

data class HomeState(
    val loading: Boolean = false,
    val photos: List<Carousel.Item>? = null
): State
